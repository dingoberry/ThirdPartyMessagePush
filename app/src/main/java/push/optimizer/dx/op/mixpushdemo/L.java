package push.optimizer.dx.op.mixpushdemo;

import android.util.Log;

/**
 * Created by tf on 3/26/2018.
 */

class L {

    private static final String TAG = "yymm";

    static void i(String msg) {
        Log.i(TAG, msg);
    }

    static void e(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }

    static void e(Throwable e) {
        Log.e(TAG, "oops!", e);
    }
}
