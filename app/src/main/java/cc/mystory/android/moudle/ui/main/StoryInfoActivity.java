package cc.mystory.android.moudle.ui.main;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.mylhyl.crlayout.SwipeRefreshWebView;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import cc.mystory.android.R;
import cc.mystory.android.base.BaseActivity;
import cc.mystory.android.net.NetApi;
import cc.mystory.android.util.StatusBarFontHelper;
import cc.mystory.android.util.UiUtils;

/**
 * Created by jiang on 2017/6/21.
 */

public class StoryInfoActivity extends BaseActivity implements View.OnClickListener {
    private SwipeRefreshWebView mWebView;

    RelativeLayout rl_back;

    String url;
    String articleUniqueId;
    String imageUrl = "https://s3.cn-north-1.amazonaws.com.cn/s.ziyoufang.cn/static/image/mystory_logo_256.jpg";
    WebView wb_view1;
    String title = "";


    @Override
    protected int setLayoutId() {
        return R.layout.activity_story_info;
    }

    @Override
    protected void findView() {
        StatusBarFontHelper.setStatusBarMode(this, true);
        articleUniqueId = getIntent().getStringExtra("articleUniqueId");

        rl_back = (RelativeLayout) findViewById(R.id.rl_back);


        mWebView = (SwipeRefreshWebView) findViewById(R.id.wb_view);
        wb_view1 = (WebView) findViewById(R.id.wb_view1);

        mWebView.autoRefresh(R.color.colorPrimary);
        WebView scrollView = mWebView.getScrollView();


        url = getIntent().getStringExtra("linkUrl");

        Logger.d("地址" + url);

        /*scrollView.loadUrl("http://192.168.20.2:8080/viewMobile?articleUniqueId=3vk4zdum");

        scrollView.loadUrl("http://www.ziyoufang.com");

        scrollView.setWebViewClient(new_commend SampleWebViewClient());*/

        generateWebView();
        wb_view1.loadUrl(url);

        wb_view1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                // 返回true则表明使用的是WebView
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }
        });
        // 获取焦点
        mWebView.requestFocus();


    }


    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebView.autoRefresh();
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void initView() {


        rl_back.setOnClickListener(this);

        Map<String, Object> map = new HashMap<>();

        map.put("articleUniqueId", articleUniqueId);
        String md5 = UiUtils.stringToMD5(articleUniqueId + NetApi.API_KEY);
        map.put("sign", md5);

    }

//    private SocialShareScene scene;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;


        }
    }

    /**
     * 生成web view
     */
    private void generateWebView() {
        WebSettings settings = wb_view1.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        settings.setAppCachePath(getCacheDir().getAbsolutePath());
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);


        settings.setJavaScriptEnabled(true);// 设置支持javascript脚本
        //编写 javaScript方法
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
        settings.setUseWideViewPort(true);
        //设置默认加载的可视范围是大视野范围
        settings.setLoadWithOverviewMode(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

//        wb_daka.addJavascriptInterface(, "control");

    }

    @Override
    public void showFailedError(int code, String message) {
        UiUtils.Toast(this, message);
    }

    @Override
    public void onEmptyOrNull() {

    }

    @Override
    public void onOtherCode(int code, String info) {
        if (code == -604) {
            //登录失效重新登录

        }
    }

    @Override
    public void onProgress(long progress, long total, boolean done) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroyDrawingCache();

        }
        mWebView = null;

        if (wb_view1 != null) {
            wb_view1.clearCache(true);
            wb_view1.loadUrl("");
            wb_view1.destroy();
        }
        wb_view1 = null;
    }


}
