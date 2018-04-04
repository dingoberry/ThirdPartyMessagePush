package push.optimizer.dx.op.mixpushdemo;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.ApiClientMgr;
import com.huawei.android.hms.agent.common.HMSAgentLog;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.DeleteTokenHandler;
import com.huawei.android.hms.agent.push.handler.EnableReceiveNormalMsgHandler;
import com.huawei.android.hms.agent.push.handler.EnableReceiveNotifyMsgHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.huawei.hms.core.aidl.IMessageEntity;

import java.util.Arrays;

/**
 * Created by tf on 3/26/2018.
 */

class HuaweiBusiness {

    private static boolean sHuaWeiInitialized;

    static void init(Application app) {
        HMSAgentLog.setHMSAgentLogCallback(new HMSAgentLog.IHMSAgentLogCallback() {
            @Override
            public void logD(String tag, String log) {
                L.i(log);
            }

            @Override
            public void logV(String tag, String log) {
                L.i(log);
            }

            @Override
            public void logI(String tag, String log) {
                L.i(log);
            }

            @Override
            public void logW(String tag, String log) {
                L.i(log);
            }

            @Override
            public void logE(String tag, String log) {
                L.i(log);
            }
        });


        Class<?> clz = null;
        try {
            clz = Class.forName("com.huawei.hms.api.IPCTransport");
            clz.getConstructor(new Class[]{String.class,
                    IMessageEntity.class,
                    Class.class}).newInstance("", null, null);

            if (null != clz) {
                L.i(Arrays.toString(clz.getDeclaredFields()));
                L.i(Arrays.toString(clz.getDeclaredMethods()));
                L.i(Arrays.toString(clz.getDeclaredConstructors()));
                L.i(Arrays.toString(clz.getConstructors()));
            } else {
                L.i("EMPTY CLZ");
            }
        } catch (Exception e) {
            L.e(e);
        }

        sHuaWeiInitialized = HMSAgent.init(app);
        L.i("HMSAgent.init:" + sHuaWeiInitialized);
        if (sHuaWeiInitialized) {
            HMSAgent.connect(new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    L.i("HMS connect end:" + rst);

                    L.i("OP-" + ApiClientMgr.INST.getApiClient().getTransportName());
                }
            });

        }
    }

    /**
     * 设置是否接收普通透传消息 | Set whether to receive normal pass messages
     *
     * @param enable 是否开启 | enabled or not
     */
    private static void setReceiveNormalMsg(boolean enable) {
        L.i("enableReceiveNormalMsg:begin");
        HMSAgent.Push.enableReceiveNormalMsg(enable, new EnableReceiveNormalMsgHandler() {
            @Override
            public void onResult(int rst) {
                L.i("enableReceiveNormalMsg:end code=" + rst);
            }
        });
    }

    /**
     * 设置接收通知消息 | Set up receive notification messages
     *
     * @param enable 是否开启 | enabled or not
     */
    private static void setReceiveNotifyMsg(boolean enable) {
        L.i("enableReceiveNotifyMsg:begin");
        HMSAgent.Push.enableReceiveNotifyMsg(enable, new EnableReceiveNotifyMsgHandler() {
            @Override
            public void onResult(int rst) {
                L.i("enableReceiveNotifyMsg:end code=" + rst);
            }
        });
    }

    static void enable(final Context cxt) {
        if (sHuaWeiInitialized) {
            L.i("get token: begin");


            setReceiveNormalMsg(true);
            setReceiveNotifyMsg(true);

            HMSAgent.Push.getToken(new GetTokenHandler() {
                @Override
                public void onResult(int rst) {
                    L.i("get token: end=" + rst);
                }
            });
        }
    }

    static void disable(Context cxt) {
        if (sHuaWeiInitialized) {
            String token = cxt.getSharedPreferences("huawei", Context.MODE_PRIVATE).getString("token", null);
            if (!TextUtils.isEmpty(token)) {
                L.i("deleteToken:begin");
                HMSAgent.Push.deleteToken(token, new DeleteTokenHandler() {
                    @Override
                    public void onResult(int rst) {
                        L.i("deleteToken:end code=" + rst);
                    }
                });
            }

            setReceiveNormalMsg(false);
            setReceiveNotifyMsg(false);
        }
    }
}
