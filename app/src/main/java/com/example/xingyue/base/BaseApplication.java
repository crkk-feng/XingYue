package com.example.xingyue.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.xingyue.utils.MyCrashHandler;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.DeviceInfoProviderDefault;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDeviceInfoProvider;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.util.SharedPreferencesUtil;

/**
 *BaseApplication类，用于解决meta-data的问题
 */
public class BaseApplication extends Application {

    private static Handler sHandler = null;
    private static final String KEY_LAST_OAID = "last_oaid";
    private static Context sContext=null;

    String oaid;
    @Override
    public void onCreate() {
        super.onCreate();
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        oaid = SharedPreferencesUtil.getInstance(getApplicationContext()).getString(KEY_LAST_OAID);

        if(DTransferConstants.isRelease) {
            String mAppSecret = "8646d66d6abe2efd14f2891f9fd1c8af";
            mXimalaya.setAppkey("9f9ef8f10bebeaa83e71e62f935bede8");
            mXimalaya.setPackid("com.app.test.android");
            mXimalaya.init(this ,mAppSecret,getDeviceInfoProvider(this));
        } else {
            String mAppSecret = "0a09d7093bff3d4947a5c4da0125972e";
            mXimalaya.setAppkey("f4d8f65918d9878e1702d49a8cdf0183");
            mXimalaya.setPackid("com.example.app.himalayafmproject");
            mXimalaya.init(this,mAppSecret,getDeviceInfoProvider(this));
        }

        //初始化喜马拉雅播放器
        XmPlayerManager.getInstance(this).init();

        sContext=getBaseContext();

        sHandler = new Handler();
        MyCrashHandler myCrashHandler = MyCrashHandler.getInstance();
        myCrashHandler.init(this);
        initARouter();
    }



    public IDeviceInfoProvider getDeviceInfoProvider(Context context) {
        return new DeviceInfoProviderDefault(context) {
            @Override
            public String oaid() {
                // 合作方要尽量优先回传用户真实的oaid，使用oaid可以关联并打通喜马拉雅主app中记录的用户画像数据，对后续个性化推荐接口推荐给用户内容的准确性会有极大的提升！
                return oaid;
            }
        };
    }

    public static Context getAppContext(){
        return sContext;
    }
    public static Handler getHandler() {
        return sHandler;
    }

    private void initARouter() {
        ARouter.openLog();     // Print log
        ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        ARouter.init(this);
    }
}
