package com.cab404.syncronos.eyeofharmony;

import android.graphics.Canvas;

/**
 * Sorry for no comments!
 * Created at 6:09 on 25.02.15
 *
 * @author cab404
 */
public interface TimelineLayer {

    public void draw(long extentStart, long extentEnd, double zoom, Canvas cvs);

}
