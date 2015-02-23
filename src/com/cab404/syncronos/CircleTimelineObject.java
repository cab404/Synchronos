package com.cab404.syncronos;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Sorry for no comments!
 * Created at 4:42 on 23.02.15
 *
 * @author cab404
 */
public class CircleTimelineObject implements TimelineObject {

    Paint pt = new Paint(Paint.ANTI_ALIAS_FLAG);
    short diameter = (short) Luna.dp(10);

    {
        pt.setStrokeWidth(Luna.dp(2));
    }

    private int when;

    public CircleTimelineObject(int when) {
        this.when = when;
    }

    @Override
    public void draw(Canvas cvs, int x_where, int y_where, float zoom) {
        pt.setStyle(Paint.Style.FILL);
        pt.setColor(Colours.NUMIX_GRAY);
        cvs.drawCircle(x_where, y_where, diameter, pt);

        pt.setStyle(Paint.Style.STROKE);
        pt.setColor(Colours.NUMIX_RED);
        cvs.drawCircle(x_where, y_where, diameter, pt);
    }

    @Override
    public void onTouch() {

    }

    @Override
    public long getStartTime() {
        return when;
    }


    @Override
    public long getEndTime() {
        return when;
    }

    @Override
    public short getTopPadding() {
        return diameter;
    }

    @Override
    public short getBottomPadding() {
        return diameter;
    }

    @Override
    public short getStartPadding() {
        return diameter;
    }

    @Override
    public short getEndPadding() {
        return diameter;
    }
}
