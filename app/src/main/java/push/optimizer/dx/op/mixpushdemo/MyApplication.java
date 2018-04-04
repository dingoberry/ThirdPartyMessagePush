package push.optimizer.dx.op.mixpushdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import java.util.List;

/**
 * Created by tf on 3/26/2018.
 */

public class MyApplication extends Application {

    private static boolean shouldInit(Context cxt) {
        ActivityManager am = ((ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE));
        if (null == am) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && info.processName.endsWith("dxopt")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (shouldInit(this)) {
            L.i("INIT bkg");
//            HuaweiBusiness.init(this);
//        XiaoMiBusiness.init(this);
            FlymeBusiness.init(this);
        }
    }
}
