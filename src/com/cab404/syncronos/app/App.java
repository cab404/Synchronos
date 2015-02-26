package com.cab404.syncronos.app;

import android.app.Application;
import com.cab404.syncronos.Luna;

/**
 * Sorry for no comments!
 * Created at 9:01 on 21.02.15
 *
 * @author cab404
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Luna.preinit(this.getApplicationContext());
    }

}
