package com.cab404.syncronos.eyeofharmony;


import android.graphics.Canvas;

/**
 * Object on a timeline.
 * Created at 11:11 on 21.02.15
 *
 * @author cab404
 */
public interface TimelineObject {

    /**
     * Draws an object on given canvas with start at x_where:y_where.
     *  @param cvs     Canvas to draw on
     * @param x_where X-coordinate of start point on canvas
     * @param zoom    Current zoom of the timeline
     */
    public void draw(Canvas cvs, int x_where, double zoom);


    /**
     * Invoked on touch event.
     * Touch events occur inside bounds specified by {@link #getStartTime() start}, {@link #getEndTime() end},
     * {@link #getStartPadding() their} {@link #getEndTime() padding},
     * as well as {@link #getTopPadding() top} and {@link #getBottomPadding() bottom} padding.
     */
    public void onTouch();

    /**
     * Time when event should be displayed.
     */
    public long getStartTime();

    /**
     * Time where event ends. In case of points it is equals to start time.
     */
    public long getEndTime();

    /**
     * Top padding. Used while determining touch events.
     */
    public short getTopPadding();

    /**
     * Bottom padding. Used while determining touch events.
     */
    public short getBottomPadding();

    /**
     * Start padding. Used while determining touch events, AND while determining if view gonna draw this object or not.
     */
    public short getStartPadding();

    /**
     * End padding. Used while determining touch events, AND while determining if view gonna draw this object or not.
     */
    public short getEndPadding();
}
