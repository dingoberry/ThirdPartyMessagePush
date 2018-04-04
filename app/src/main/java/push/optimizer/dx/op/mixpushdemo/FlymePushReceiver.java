package push.optimizer.dx.op.mixpushdemo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * Created by tf on 3/27/2018.
 */

public class FlymePushReceiver extends MzPushMessageReceiver {
    @Override
    public void onRegister(Context context, String pushID) {
        //调用PushManager.register(context）方法后，会在此回调注册状态
        //应用在接受返回的pushid
        L.i("onRegister pushID " + pushID);
    }

    @Override
    public void onMessage(Context context, String message) {
        //接收服务器推送的透传消息
        L.i("onMessage " + message);
    }

    @Override
    public void onMessage(Context context, Intent intent) {
        String content = intent.getExtras().toString();
        L.i("flyme3 onMessage " + content);
    }

    @Override
    public void onMessage(Context context, String message, String platformExtra) {
        L.i("onMessage " + message +" platformExtra "+platformExtra);
    }

    @Override
    public void onUnRegister(Context context, boolean b) {
        //调用PushManager.unRegister(context）方法后，会在此回调反注册状态
        L.i("onUnRegister " + b);
    }

    @Override
    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        //重要,详情参考应用小图标自定设置
        pushNotificationBuilder.setmStatusbarIcon(R.mipmap.ic_launcher_round);
        L.i("onUpdateNotificationBuilder");
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        //检查通知栏和透传消息开关状态回调
        L.i("onPushStatus:" + pushSwitchStatus);
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        //调用新版订阅PushManager.register(context,appId,appKey)回调
        L.i("onRegisterStatus " + registerStatus+ " "+context.getPackageName());
        String pushId = registerStatus.getPushId();
        if (!TextUtils.isEmpty(pushId)) {
            L.i("Turn push switcher.");
            PushManager.switchPush(context, FlymeBusiness.APP_ID, FlymeBusiness.APP_KEY, pushId, 0, true);
            PushManager.switchPush(context, FlymeBusiness.APP_ID, FlymeBusiness.APP_KEY, pushId, 1, true);
            PushManager.subScribeTags(context, FlymeBusiness.APP_ID, FlymeBusiness.APP_KEY, pushId, "Smack");
//            PushManager.checkPush(context, FlymeBusiness.APP_ID, FlymeBusiness.APP_KEY, pushId);
        }
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        //新版反订阅回调
        L.i("onUnRegisterStatus "+unRegisterStatus+" "+context.getPackageName());
    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        //标签回调
        L.i("onSubTagsStatus " + subTagsStatus+" "+context.getPackageName());
    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        //别名回调
        L.i("onSubAliasStatus " + subAliasStatus+" "+context.getPackageName());
    }

    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息到达回调，flyme6基于android6.0以上不再回调
        L.i("onNotificationArrived title " + mzPushMessage.getTitle() + "content "
                + mzPushMessage.getContent() + " selfDefineContentString " + mzPushMessage.getSelfDefineContentString()+" notifyId "+mzPushMessage.getNotifyId());
    }

    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息点击回调
        L.i("onNotificationClicked title "+ mzPushMessage.getTitle() + "content "
                + mzPushMessage.getContent() + " selfDefineContentString " + mzPushMessage.getSelfDefineContentString()+" notifyId "+mzPushMessage.getNotifyId());
    }

    @Override
    public void onNotificationDeleted(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息删除回调；flyme6基于android6.0以上不再回调
        L.i("onNotificationDeleted title " + mzPushMessage.getTitle() + "content "
                + mzPushMessage.getContent() + " selfDefineContentString " + mzPushMessage.getSelfDefineContentString()+" notifyId "+mzPushMessage.getNotifyId());
    }

    @Override
    public void onNotifyMessageArrived(Context context, String message) {
        L.i("onNotifyMessageArrived messsage " + message);
    }
}
