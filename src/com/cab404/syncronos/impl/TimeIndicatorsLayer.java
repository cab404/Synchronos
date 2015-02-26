package com.cab404.syncronos.impl;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.cab404.syncronos.Colours;
import com.cab404.syncronos.Luna;
import com.cab404.syncronos.eyeofharmony.TimelineLayer;

import java.util.Calendar;

/**
 * Sorry for no comments!
 * Created at 4:47 on 26.02.15
 *
 * @author cab404
 */
public class TimeIndicatorsLayer implements TimelineLayer {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paint.setColor(Colours.NUMIX_RED);
    }


    protected void drawNotch(Canvas cv, int x, int thick, int length) {
        int half_height = cv.getHeight() / 2;
        thick = thick > 0 ? thick : 1;

        int rl = x - thick / 2;
        int rr = x + thick / 2 + (thick % 2 == 1 ? 0 : 1);
        int rt = half_height - length / 2;
        int rb = half_height + length / 2;

        cv.drawRect(rl, rt, rr, rb, paint);
    }

    Calendar cl = Calendar.getInstance();

    @Override
    public void draw(long extentStart, long extentEnd, double zoom, Canvas cvs) {
        cl.setTimeInMillis(extentStart);
        cl.set(cl.get(Calendar.YEAR), 0, 0);

        while (cl.getTimeInMillis() < extentEnd) {
            drawNotch(cvs, (int) ((cl.getTimeInMillis() - extentStart) * zoom), (int) Luna.dp(2), (int) Luna.dp(10));
            cl.roll(Calendar.YEAR, true);
        }
    }


}
