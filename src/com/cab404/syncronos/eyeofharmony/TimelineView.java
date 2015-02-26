package com.cab404.syncronos.eyeofharmony;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.cab404.syncronos.Colours;
import com.cab404.syncronos.Luna;

/**
 * Sorry for no comments!
 * Created at 8:32 on 21.02.15
 *
 * @author cab404
 */
public class TimelineView extends View {

    public TimelineView(Context context) {
        super(context);
    }

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimelineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * Last X.
     */
    float l_x = -1;
    /**
     * Last speed
     */
    float l_s = -1;

    /**
     * Last length between selected points.
     */
    float l_l = -1;

    /**
     * Start center point of pan
     */
    long d_s_p = -1;

    /**
     * Last point count
     */
    int l_p_c = 0;

    /**
     * Kinetic scroll - current force
     */
    float kinetic_x = 0;

    /**
     * Speed of kinetic scroll burnout.
     * 1 means scroll will never stop, 0 means it will stop immediately.
     */
    final static float KINETIC_DAMPING = 0.95f;

    /**
     * How much of additional extent data to load on extent change, in screen pixels.
     * Allows to make calls to DB less frequent, thus increasing performance.
     */
    final static long OBJECT_QUERY_PADDING = 80;
    /**
     * How much times does loaded extent has to be bigger
     * than currently visible area to be reloaded. Zoom in memory optimisation.
     */
    final static float SHRINK_EXTENT = 5f;

    long loadedExtentStart = Long.MAX_VALUE;

    long loadedExtentEnd = Long.MIN_VALUE;

    boolean update_kinetics = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        update_kinetics = false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            l_x = -1;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (l_p_c == 1 && l_x >= 0) {
                kinetic_x = l_s;
                update_kinetics = true;
            }

            l_x = -1;
            l_l = -1;
        }

        if (event.getPointerCount() == 1) {
            if (l_x >= 0) {
                l_s = l_x - event.getX();
                addToTime((long) (l_s / zoom));
                updateObjects();
            }
            l_x = event.getX();
        } else {
            l_x = -1;
        }

        if (event.getPointerCount() == 2) {
            float len = Math.abs(event.getX(0) - event.getX(1));
            float c_x = (event.getX(0) - event.getX(1)) / 2 + event.getX(1);

            if (d_s_p < 0)
                d_s_p = timeOffset + (long) (c_x / zoom);

            System.out.println("BPoint = " + c_x);
            System.out.println("DSPoint = " + d_s_p);

            if (l_l >= 0)
                setZoom(zoom / (l_l / len));

            // Moving initial center to current center
            setTimeOffset(d_s_p - (long) ((c_x / zoom)));

            updateObjects();

            l_l = len;
        } else {
            l_l = -1;
            d_s_p = -1;
        }

        l_p_c = event.getPointerCount();

        return true;
    }

    /**
     * Do not change, only for use in onDraw.
     */
    protected Canvas cv;
    protected Paint pt;

    protected TimelineObjectStorage objects;
    protected Iterable<TimelineObject> current;
    protected TimelineLayer[] layers;

    public TimelineLayer[] getLayers() {
        return layers;
    }

    public void setLayers(TimelineLayer... layers) {
        this.layers = layers;
    }


    /**
     * widthInMilliseconds * zoom = widthInPixels
     */
    protected double zoom = 1;
    protected long timeOffset = 0;

    protected long downBorder = 0;
    protected long upBorder = System.currentTimeMillis() * 2;


    protected int color = Colours.NUMIX_RED;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getZoom() {
        return zoom;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateObjects();
    }


    public void setZoom(double zoom) {
        double minimal_zoom = ((double) getWidth()) / (upBorder - downBorder);
        if (zoom > minimal_zoom) {
            this.zoom = zoom;

        } else {
            this.zoom = minimal_zoom;
        }
    }

    public long getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(long timeOffset) {
        addToTime(timeOffset - this.timeOffset);
    }

    protected void drawBackLine() {
        int half_height = cv.getHeight() / 2;
        cv.drawRect(0, half_height - Luna.dp(1), cv.getWidth(), half_height + Luna.dp(1), pt);
    }

    public void setStorage(TimelineObjectStorage storage) {
        this.objects = storage;
        updateObjects();
    }

    public void updateObjects() {
        // Length of visible timeline part
        long length = (long) (getWidth() / zoom);
        // Loading padding
        long padding = (long) (OBJECT_QUERY_PADDING / zoom);

        if (timeOffset < loadedExtentStart || timeOffset + length > loadedExtentEnd
                || length * SHRINK_EXTENT < loadedExtentEnd - loadedExtentStart) {

            loadedExtentStart = timeOffset - padding;
            loadedExtentEnd = timeOffset + length + padding;

            System.out.println("Loaded " + loadedExtentStart + ":" + loadedExtentEnd);
            System.out.println("now " + timeOffset + ":" + (timeOffset + length));
            System.out.println("Shrink " + timeOffset + ":" + (timeOffset + length));

            current = objects.getObjects(loadedExtentStart, loadedExtentEnd, zoom);
        }
        invalidate();

    }

    protected void drawNotch(int x, int thick, int length) {
        int half_height = cv.getHeight() / 2;
        thick = thick > 0 ? thick : 1;

        int rl = x - thick / 2;
        int rr = x + thick / 2 + (thick % 2 == 1 ? 0 : 1);
        int rt = half_height - length / 2;
        int rb = half_height + length / 2;

        cv.drawRect(rl, rt, rr, rb, pt);
    }

    public void addToTime(long howmuch) {
        if (howmuch < 0) {
            if (timeOffset + howmuch < 0)
                timeOffset = downBorder;
            else
                timeOffset += howmuch;
        } else {
            long size = (long) (getWidth() / zoom);
            if (timeOffset + howmuch > upBorder - size)
                timeOffset = upBorder - size;
            else
                timeOffset += howmuch;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        Luna.preinit(getContext());

        if (pt == null) {
            pt = new Paint(Paint.ANTI_ALIAS_FLAG);
            pt.setStyle(Paint.Style.FILL);
            pt.setStrokeWidth(Luna.dp(2));
            pt.setColor(color);
        }
        cv = canvas;

        drawBackLine();

        if (isInEditMode()) return;

        if (layers != null) {
            long length = (long) (cv.getWidth() / zoom);
            for (TimelineLayer layer : layers)
                layer.draw(timeOffset, timeOffset + length, zoom, cv);

        }

        if (current != null)
            for (TimelineObject obj : current) {
                int start = (int) ((obj.getStartTime() - timeOffset) * zoom);
                obj.draw(cv, start, zoom);
            }

        if (update_kinetics) {
            addToTime((long) (kinetic_x / zoom));
            kinetic_x *= KINETIC_DAMPING;
            updateObjects();
        }

        if (Math.abs(kinetic_x) < 0.1)
            update_kinetics = false;

    }

}
