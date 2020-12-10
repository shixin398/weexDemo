package tv.newtv.weexdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

public  class MainActivity extends AppCompatActivity implements IWXRenderListener {

    private static final String TAG = "MainActivity";

    private WXSDKInstance wxsdkInstance;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /**
             * 轮询访问 WXSDKEngine 初始化状态 防止异步造成的初始化失败问题
             */
            if (msg.what == 1) {
                if (WXSDKEngine.isInitialized()) {
                    startRender();
                } else {
                    handler.sendEmptyMessageDelayed(1, 300);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        wxsdkInstance = new WXSDKInstance(this);
        wxsdkInstance.registerRenderListener(this);
        if (WXSDKEngine.isInitialized()) {
            startRender();
        } else {
            handler.sendEmptyMessageDelayed(1, 300);
        }


        setContentView(R.layout.activity_main);
    }

    /**
     * WXSDKEngine 初始化成功后 开始渲染
     */
    private void startRender() {
        String pageName = "WXSample";
        /**
         * 防止空指针
         */
        if (wxsdkInstance == null) {
            wxsdkInstance = new WXSDKInstance(this);
            wxsdkInstance.registerRenderListener(this);
        }

        /**
         * 渲染远程js
         */
        String bundleUrl = "http://dotwe.org/raw/dist/38e202c16bdfefbdb88a8754f975454c.bundle.wx";
        wxsdkInstance.renderByUrl(pageName, bundleUrl, null, null, WXRenderStrategy.APPEND_ASYNC);

        /**
         * 渲染本地js
         */
        //   String bundleUrl = "index.js";
        //   wxsdkInstance.render(pageName, WXFileUtils.loadAsset(bundleUrl,this), null, null, WXRenderStrategy.APPEND_ASYNC);
    }

    /**
     * 重写生命周期方法
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wxsdkInstance != null) {
            wxsdkInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance wxsdkInstance, View view) {
        /**
         * 填充视图
         */
        setContentView(view);
        Log.d(TAG, "onViewCreated: .......");
    }

    @Override
    public void onRenderSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {

    }

    @Override
    public void onException(WXSDKInstance wxsdkInstance, String errCode, String msg) {
        Log.d("welog", "onException: " + errCode + "//" + msg);
    }
}