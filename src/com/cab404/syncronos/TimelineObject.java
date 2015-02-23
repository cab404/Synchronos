package com.cab404.syncronos;


import android.graphics.Canvas;

/**
 * Sorry for no comments!
 * Created at 11:11 on 21.02.15
 *
 * @author cab404
 */
public interface TimelineObject {

    public void draw(Canvas cvs, int x_where, int y_where, float zoom);

    public void onTouch();

    public long getStartTime();

    public long getEndTime();

    public short getTopPadding();

    public short getBottomPadding();

    public short getStartPadding();

    public short getEndPadding();
}
