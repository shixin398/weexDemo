package tv.newtv.weexdemo;

import android.app.Application;
import android.util.Log;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.adapter.DefaultWXHttpAdapter;
import com.taobao.weex.bridge.WXBridgeManager;

/**
 * @ProjectName: WeexDemo
 * @Package: tv.newtv.weexdemo
 * @ClassName: MyApplication
 * @Description:
 * @Author: shitao.li
 * @CreateDate: 2020/12/10 13:42
 * @UpdateUser:
 * @UpdateDate: 2020/12/10 13:42
 * @UpdateRemark:
 * @Version: 1.0
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: .......");
        InitConfig config = new InitConfig.Builder()
                .setHttpAdapter(new DefaultWXHttpAdapter()).build();
        WXSDKEngine.initialize(this, config);

        WXBridgeManager.updateGlobalConfig("wson_on");
        WXEnvironment.setOpenDebugLog(true);
        WXEnvironment.setApkDebugable(true);
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");

    }
}
