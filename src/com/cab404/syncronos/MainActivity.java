package com.cab404.syncronos;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TimelineView timeline = (TimelineView) findViewById(R.id.timeline);

        SimpleObjectStorage storage = new SimpleObjectStorage();
        storage.objects.add(new CircleTimelineObject(42));
        storage.objects.add(new CircleTimelineObject(62));
        storage.objects.add(new CircleTimelineObject(82));
        storage.objects.add(new CircleTimelineObject(92));
        storage.objects.add(new CircleTimelineObject(122));
        storage.objects.add(new CircleTimelineObject(720));

        timeline.setStorage(storage);

        timeline.setZoom(1);
        timeline.setTimeOffset(62);
    }
}
