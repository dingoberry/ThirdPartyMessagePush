package push.optimizer.dx.op.mixpushdemo;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;

public class HuaweiPushReceiver extends PushReceiver {

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        L.i("onToken:" + token);
        context.getSharedPreferences("huawei", Context.MODE_PRIVATE).edit().putString("token",
                token).apply();
    }

    @Override
    public boolean onPushMsg(Context context, byte[] msgBytes, Bundle extras) {
        try {
            //CP可以自己解析消息内容，然后做相应的处理
            String content = new String(msgBytes, "UTF-8");
            L.i("收到PUSH透传消息,消息内容为:" + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        L.i("onEvent");
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            L.i("收到通知栏消息点击事件,notifyId:" + notifyId);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
        }
    }

    @Override
    public void onToken(Context context, String token) {
        L.i("BonToken:" + token);
    }

    @Override
    public void onPushMsg(Context context, byte[] msgBytes, String token) {
        try {
            //CP可以自己解析消息内容，然后做相应的处理
            String content = new String(msgBytes, "UTF-8");
            L.i("收到PUSH透传消息,消息内容为:" + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        super.onPushState(context, pushState);
        L.i("onPushState:" + pushState);
    }
}
