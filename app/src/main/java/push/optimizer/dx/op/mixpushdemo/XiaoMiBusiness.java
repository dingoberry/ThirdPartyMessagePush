package push.optimizer.dx.op.mixpushdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by tf on 3/26/2018.
 */

class XiaoMiBusiness {

    private static final String APP_ID = "2882303761517750033";
    private static final String APP_KEY = "5181775066033";

//    private static boolean shouldInit(Context cxt) {
//        ActivityManager am = ((ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE));
//        if (null == am) {
//            return false;
//        }
//        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
//        String mainProcessName = cxt.getPackageName();
//        int myPid = Process.myPid();
//        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
//            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
//                return true;
//            }
//        }
//        return false;
//    }

    static void init(Context cxt) {
//        if (shouldInit(cxt)) {
//            L.i("registerPush xiaomi");
//            MiPushClient.registerPush(cxt, APP_ID, APP_KEY);
//        }

        L.i("registerPush xiaomi");
        MiPushClient.registerPush(cxt, APP_ID, APP_KEY);

        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                L.e(content, t);
            }

            @Override
            public void log(String content) {
                L.i(content);
            }
        };
        Logger.setLogger(cxt, newLogger);
    }
}
