package com.cab404.syncronos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getPointerCount() == 1) {
            if (event.getHistorySize() >= 1) {
                float x = event.getHistoricalX(0) - event.getX();
                timeOffset += x;
                System.out.println("pChange " + x);
                onPositionChange();
            }
        }

        return true;

    }

    /**
     * Do not change, only for use in onDraw.
     */
    protected Canvas cv;
    protected Paint pt;

    protected TimelineObjectStorage objects;
    protected Iterable<TimelineObject> current;

    protected float zoom = 1;
    protected long timeOffset = 0;

    protected int color = Colours.NUMIX_RED;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public long getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(long timeOffset) {
        this.timeOffset = timeOffset;
    }

    protected void drawBackLine() {
        int half_height = cv.getHeight() / 2;
        cv.drawRect(0, half_height - Luna.dp(1), cv.getWidth(), half_height + Luna.dp(1), pt);
    }

    public void setStorage(TimelineObjectStorage storage) {
        this.objects = storage;
        onPositionChange();
    }

    public void onPositionChange() {
        int length = (int) (getWidth() / zoom);
        current = objects.getObjects(timeOffset, timeOffset + length, zoom);
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

        int half_height = cv.getHeight() / 2;

        int how_much = 0;
        for (TimelineObject obj : current) {
            how_much++;
            int start = (int) ((obj.getStartTime() - timeOffset) * zoom);
            obj.draw(cv, start, half_height, zoom);
        }
        System.out.println("Drawn " + how_much + " objects");

    }

}
