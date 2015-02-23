package com.cab404.syncronos;

import java.util.Collection;

/**
 * Sorry for no comments!
 * Created at 4:39 on 23.02.15
 *
 * @author cab404
 */
public interface TimelineObjectStorage {

    public Iterable<TimelineObject> getObjects(long start, long end, float zoom);

}
