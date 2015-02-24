package com.cab404.syncronos;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * Utility methods. Tribute for Princess Luna.
 * Created at 8:59 on 21.02.15
 *
 * @author cab404
 */
public class Luna {
    public static Context ctx;
    public static Resources res;

    /**
     * Launching every time we might encounter Luna for the first time.
     */
    public static void preinit(Context ctx) {
        if (Luna.ctx != null) return;
        Luna.ctx = ctx;
        Luna.res = ctx.getResources();
    }

    public static float dp(int how_much) {
        return res.getDisplayMetrics().density * how_much;
    }

    public static void log(Object wat) {
        Log.v("LunaLog", String.valueOf(wat));
    }

}
