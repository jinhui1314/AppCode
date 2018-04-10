package cc.mystory.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import cc.mystory.android.util.CheckUtil;


/**
 * 启动页，加载时长1500ms，根据不同情况分别进入欢迎页、登录页或主页
 * Create by Lei on 2016/9/20
 */

public class LoadingActivity extends Activity {
    Context tag = LoadingActivity.this;
    //    BiaoQianPresenter mBiaoQianPresenter;
    String[] title;

    /**
     * 固定的最小加载时间
     */
    private static final long LOADING_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //拉去版本号检查更新
//        CheckUpdateUtils.checkVersion(this);
//        mBiaoQianPresenter=new_commend BiaoQianPresenter(this,this);

        //全屏模式
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loading);
        //固定等秒1500ms
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                long startTime = System.currentTimeMillis();
//                checkUpdate();
//                WrapperUtils.getInstance(LoadingActivity.this).downloadAll();
                //mBiaoQianPresenter.getBiaoQian();


                long loadingTime = System.currentTimeMillis() - startTime;
                if (loadingTime < LOADING_TIME) {
                    try {
                        Thread.sleep(LOADING_TIME - loadingTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                finish();
                transferActivity();
            }
        }.execute(new Void[]{});


    }

    /**
     * 跳转Activity
     */
    private void transferActivity() {
        /*if (CheckUtil.isFirstRun(LoadingActivity.this)) {
            //第一次运行
            Intent intent = new Intent();
            SharePrefHelper.getInstance(LoadingActivity.this)
                    .put(SharePrefHelper.FIRST_RUN, false);
            intent.setClass(tag, GuideActivity.class);
            startActivity(intent);

            LoadingActivity.this.finish();
        } else {*/
            if (!CheckUtil.isLogin(this)) {
                Intent intent = new Intent(LoadingActivity.this, cc.mystory.android.moudle.ui.MainActivity.class);
                startActivity(intent);
                LoadingActivity.this.finish();
            } else {
                Intent intent = new Intent(LoadingActivity.this, cc.mystory.android.moudle.ui.MainActivity.class);
                startActivity(intent);
                LoadingActivity.this.finish();
            }
//        }

        /*Intent intent = new_commend Intent();
        if (CheckUtil.isFirstRun(LoadingActivity.this)) {
            //第一次运行
            SharePrefHelper.getInstance(LoadingActivity.this)
                    .put(SharePrefHelper.FIRST_RUN, false);
            intent.setClass(tag, GuideActivity.class);
        }
//        else if (CheckUtil.isLogin(LoadingActivity.this)) {
        else {
            intent.setClass(tag, MainActivity.class);
            if(getIntent().getBundleExtra(CommonString.EXTRA_BUNDLE) != null){
                intent.putExtra(CommonString.EXTRA_BUNDLE,
                        getIntent().getBundleExtra(CommonString.EXTRA_BUNDLE));
            }
        }
        *//*else {
            intent.setClass(tag, LoginActivity.class);
        }*//*
        intent.putExtra("title",title);
        startActivity(intent);

        LoadingActivity.this.finish();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_out);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }


}
