package push.optimizer.dx.op.mixpushdemo;

import android.content.Context;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;

/**
 * Created by tf on 3/27/2018.
 */

class FlymeBusiness {

    static final String APP_ID = "112799";
    static final String APP_KEY = "9660bab874fe428a86a343dabdbacf90";

    static void init(Context cxt) {
        if (MzSystemUtils.isBrandMeizu(cxt)) {
            L.i("Init Meizhu");
            PushManager.register(cxt, APP_ID, APP_KEY);
            DebugLogger.initDebugLogger(cxt);
        }
    }
}
