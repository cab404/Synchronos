package com.cab404.syncronos;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple and slow list.
 * Created at 5:13 on 23.02.15
 *
 * @author cab404
 */
public class SimpleObjectStorage implements TimelineObjectStorage {

    public List<TimelineObject> objects = new ArrayList<>();

    @Override
    public Iterable<TimelineObject> getObjects(long start, long end, double zoom) {
        ArrayList<TimelineObject> objects = new ArrayList<>();

        for (TimelineObject obj : this.objects)
            if (obj.getStartTime() - obj.getStartPadding() / zoom < end && obj.getEndTime() + obj.getEndPadding() / zoom > start)
                objects.add(obj);


        return objects;
    }
}
