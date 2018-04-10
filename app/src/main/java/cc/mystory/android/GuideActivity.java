package cc.mystory.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;

import cc.mystory.android.util.CheckUtil;

/**
 * 欢迎页
 * Create by Lei on 2016/9/20
 */
public class GuideActivity extends AppIntro {
    Context tag = GuideActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(GuideFragment.newInstance(R.layout.intro1));
        addSlide(GuideFragment.newInstance(R.layout.intro2));
        addSlide(GuideFragment.newInstance(R.layout.intro3));
//        addSlide(GuideFragment.newInstance(R.layout.intro4));

        setScrollDurationFactor(2);
        setFadeAnimation();
        setSkipText("跳过");
        setDoneText("完成");
    }

    @Override
    public void onDonePressed() {
        transferActivity();
    }

    public void getStarted(View view) {
        transferActivity();
    }

    /**
     * 跳转Activity
     */
    private void transferActivity() {
        Intent intent = new Intent();
        if (CheckUtil.isLogin(GuideActivity.this)) {
            intent.setClass(tag, cc.mystory.android.moudle.ui.MainActivity.class);
        } else {
            intent.setClass(tag, cc.mystory.android.moudle.ui.MainActivity.class);
        }
        startActivity(intent);
        GuideActivity.this.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        transferActivity();
    }

}
