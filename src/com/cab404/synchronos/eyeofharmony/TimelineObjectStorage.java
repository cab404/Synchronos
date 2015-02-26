package com.cab404.synchronos.eyeofharmony;


/**
 * Specifies logic of data mining.
 * Should be possibly done with trees.
 * Created at 4:39 on 23.02.15
 *
 * @author cab404
 */
public interface TimelineObjectStorage {

    /**
     * Returns events in given time span
     *
     * @param start Start of time span
     * @param end   End of time span
     * @param zoom  Zoom of the map
     */
    public Iterable<TimelineObject> getObjects(long start, long end, double zoom);

}
