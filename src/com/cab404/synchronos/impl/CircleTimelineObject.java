package com.cab404.synchronos.impl;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.cab404.synchronos.Colours;
import com.cab404.synchronos.Luna;
import com.cab404.synchronos.eyeofharmony.TimelineObject;

/**
 * Sorry for no comments!
 * Created at 4:42 on 23.02.15
 *
 * @author cab404
 */
public class CircleTimelineObject implements TimelineObject {

    Paint pt = new Paint(Paint.ANTI_ALIAS_FLAG);
    short diameter = (short) Luna.dp(6);

    {
        pt.setStrokeWidth(Luna.dp(2));
    }

    private final long when;
    private int color = Colours.NUMIX_RED;

    public CircleTimelineObject(long when) {
        this.when = when;
    }

    public CircleTimelineObject(long when, int color) {
        this.when = when;
        this.color = color;
    }

    @Override
    public void draw(Canvas cvs, int x_where, double zoom) {
        pt.setStyle(Paint.Style.FILL);
        pt.setColor(color);
        cvs.drawCircle(x_where, cvs.getHeight() / 2, diameter, pt);

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
