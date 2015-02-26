package com.cab404.syncronos.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import com.cab404.syncronos.R;
import com.cab404.syncronos.eyeofharmony.TimelineView;
import com.cab404.syncronos.impl.CircleTimelineObject;
import com.cab404.syncronos.impl.SimpleObjectStorage;
import com.cab404.syncronos.impl.TimeIndicatorsLayer;

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
        for (int i = 0; i < 10; i++)
            storage.objects.add(new CircleTimelineObject(TimeUnit.DAYS.toMillis(365 * i), 0xff377a3b));
        storage.objects.add(new CircleTimelineObject(System.currentTimeMillis(), Color.GREEN));

        timeline.setStorage(storage);

        timeline.setLayers(new TimeIndicatorsLayer());

        timeline.setZoom(0.000000005f);
    }
}
