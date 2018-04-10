package cc.mystory.android.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import cc.mystory.android.util.ActivityCollector;


/**
 * Create by Lei on 2016/11/25
 */

public abstract class BaseActivity1 extends AppCompatActivity implements IBaseViewNew {
    public String TAG;
    public Context tag;

    protected abstract int setLayoutId();

    protected abstract void findView();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        tag = this;

        Logger.d(this.getClass() + "----------------------onCreate");

        ActivityCollector.addActivity(this, this.getClass());

        setContentView(setLayoutId());

        findView();

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(this.getClass() + "----------------------onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();

        Logger.d(this.getClass() + "----------------------onResume");

    }

    @Override
    protected void onPause() {
        Logger.d(this.getClass() + "----------------------onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Logger.d(this.getClass() + "----------------------onStop");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d(this.getClass() + "----------------------onRestart");

    }

    @Override
    protected void onDestroy() {
        Logger.d(this.getClass() + "----------------------onDestory");

        ActivityCollector.removeActivity(this);

        super.onDestroy();

        System.gc();
    }
}
