package com.cab404.syncronos;

import android.app.Activity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

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
        storage.objects.add(new CircleTimelineObject(0));
        storage.objects.add(new CircleTimelineObject(1));
        storage.objects.add(new CircleTimelineObject(TimeUnit.SECONDS.toMillis(1)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.MINUTES.toMillis(1)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.HOURS.toMillis(1)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(1)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(2)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(3)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(4)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(40)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(80)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(365)));
        storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(365 * 10)));
        storage.objects.add(new CircleTimelineObject(System.currentTimeMillis()));
        storage.objects.add(new CircleTimelineObject(Long.MAX_VALUE));

        timeline.setStorage(storage);

        timeline.setZoom(0.000000005f);
    }
}
