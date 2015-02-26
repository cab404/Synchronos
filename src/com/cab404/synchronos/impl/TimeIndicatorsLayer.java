package com.cab404.synchronos.impl;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.cab404.synchronos.Colours;
import com.cab404.synchronos.Luna;
import com.cab404.synchronos.eyeofharmony.TimelineLayer;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
        paint.setTextSize(Luna.dp(12));
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

        int hh = cvs.getHeight() / 2;

        /* Years */
        if (zoom > (float) cvs.getWidth() / TimeUnit.DAYS.toMillis(365 * 20)) {
            cl.setTimeInMillis(extentStart);
            cl.set(cl.get(Calendar.YEAR), 0, 1, 0, 0, 0);
            cl.set(Calendar.MILLISECOND, 0);

            boolean first = true;//(zoom > (float) cvs.getWidth() / TimeUnit.DAYS.toMillis(365));

            while (cl.getTimeInMillis() < extentEnd) {
                int x = first ? 0 : (int) ((cl.getTimeInMillis() - extentStart) * zoom);
                first = false;

                drawNotch(cvs, x, (int) Luna.dp(2), (int) Luna.dp(60));
                cvs.drawText(cl.get(Calendar.YEAR) + "", x + Luna.dp(2), hh + Luna.dp(45), paint);
                cl.roll(Calendar.YEAR, true);
            }

        }


        /* Months */
        if (zoom > (float) cvs.getWidth() / TimeUnit.DAYS.toMillis(365)) {
            cl.setTimeInMillis(extentStart);
            cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), 1, 0, 0, 0);
            cl.set(Calendar.MILLISECOND, 0);

            boolean first = true;//(zoom > (float) cvs.getWidth() / TimeUnit.DAYS.toMillis(28));

            while (cl.getTimeInMillis() < extentEnd) {
                int x = first ? 0 : (int) ((cl.getTimeInMillis() - extentStart) * zoom);
                first = false;

                drawNotch(cvs, x, (int) Luna.dp(1), (int) Luna.dp(40));
                cvs.drawText(DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths()[cl.get(Calendar.MONTH)], x + Luna.dp(2), hh + Luna.dp(25), paint);
                cl.roll(Calendar.MONTH, true);
                if (cl.get(Calendar.MONTH) == 0)
                    cl.roll(Calendar.YEAR, true);
            }

        }


        /* Days */
        if (zoom > (float) cvs.getWidth() / TimeUnit.DAYS.toMillis(30)) {
            cl.setTimeInMillis(extentStart);
            cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            cl.set(Calendar.MILLISECOND, 0);

            boolean first = true;//(zoom > (float) cvs.getWidth() / TimeUnit.DAYS.toMillis(1));

            while (cl.getTimeInMillis() < extentEnd) {
                int x = first ? 0 : (int) ((cl.getTimeInMillis() - extentStart) * zoom);
                first = false;

                drawNotch(cvs, x, (int) Luna.dp(1), (int) Luna.dp(10));
                cvs.drawText(cl.get(Calendar.DAY_OF_MONTH) + "", x + Luna.dp(2), hh + Luna.dp(15), paint);
                cl.roll(Calendar.DAY_OF_YEAR, true);
                if (cl.get(Calendar.DAY_OF_YEAR) == 1)
                    cl.roll(Calendar.YEAR, true);
            }

        }

    }

}
