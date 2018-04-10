package cc.mystory.android;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;
import android.widget.FrameLayout;

import cc.mystory.android.util.CrashHandler;
import cn.jpush.android.api.JPushInterface;

//import cn.jpush.android.api.JPushInterface;


/**
 * 自定义Application
 * Create by Lei on 2016/9/20
 */
public class App extends Application /*implements IUserInfoView*/ {
    static App app;
    Context context;
    WindowManager mWindowManager;
    FrameLayout mFloatLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        /*ShareConfig config = ShareConfig.instance()// 初始化微信

                .wxId("wx735628c36e275539")
                // 下面两个，如果不需要登录功能，可不填写
                .wxSecret("00c3ab93e51b8f2b1dad3ff4570bfe21");

        ShareManager.init(config);*/
//        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
//初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        app = this;
        context = this;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

    }
    public WindowManager getWindowManager() {
        return mWindowManager;
    }
    public static App getInstance() {
        return app;
    }
    public Context getContext() {
        return context;
    }

}