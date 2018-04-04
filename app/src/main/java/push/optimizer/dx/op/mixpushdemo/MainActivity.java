package push.optimizer.dx.op.mixpushdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements Runnable, TextWatcher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView content = findViewById(R.id.content_imei);
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String id;
        try {
            id = tm.getDeviceId();
        } catch (SecurityException e) {
            id = null;
            L.e(e);
        }

        if (TextUtils.isEmpty(id)) {
            id = Settings.System.getString(getContentResolver(), android.provider.Settings.System.ANDROID_ID);
        }

        StringBuilder info = new StringBuilder();
        info.append("IMEI:" + id);

        info.append(", AndroidVersion:" + Build.VERSION.RELEASE);
        content.setText(info.toString());
        startService(new Intent(this, MyService.class));
//        new Thread(this).start();

        ((EditText) findViewById(R.id.e1)).addTextChangedListener(this);
    }

    @Override
    public void run() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> pInfos = pm.getInstalledPackages(0);
        for (PackageInfo p : pInfos) {
            CharSequence label = p.applicationInfo.loadLabel(pm);
            if (!TextUtils.isEmpty(label) && label.toString().contains("华为")) {
                Log.i("ccmm", p.packageName + ":" + label);
            }
        }
    }

    public void execHuawei(View view) {
        Button t = (Button) view;
        if (t.getText().toString().equals(getString(R.string.enable_huawei_push))) {
            HuaweiBusiness.enable(this);
            t.setText(R.string.disable_huawei_push);
        } else {
            HuaweiBusiness.disable(this);
            t.setText(R.string.enable_huawei_push);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        L.i(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
