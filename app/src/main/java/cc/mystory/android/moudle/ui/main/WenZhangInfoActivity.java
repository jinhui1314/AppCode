package cc.mystory.android.moudle.ui.main;

import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mylhyl.crlayout.SwipeRefreshWebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import cc.mystory.android.R;
import cc.mystory.android.base.BaseActivity;
import cc.mystory.android.util.StatusBarFontHelper;
import cc.mystory.android.util.UiUtils;

/**
 * Created by jiang on 2017/6/21.
 */

public class WenZhangInfoActivity extends BaseActivity implements View.OnClickListener {
    private SwipeRefreshWebView mWebView;

    RelativeLayout rl_back;
    ImageView iv_back;

    String url;
    String articleUniqueId;
    String imageUrl = "https://s3.cn-north-1.amazonaws.com.cn/s.ziyoufang.cn/static/image/mystory_logo_256.jpg";
    WebView wb_view1;
    String title = "";


    @Override
    protected int setLayoutId() {
        return R.layout.activity_story_info;
    }

    private Handler mHander = new Handler();

    @Override
    protected void findView() {
        StatusBarFontHelper.setStatusBarMode(this, true);
        articleUniqueId = getIntent().getStringExtra("articleUniqueId");

        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        mWebView = (SwipeRefreshWebView) findViewById(R.id.wb_view);
        wb_view1 = (WebView) findViewById(R.id.wb_view1);

        mWebView.autoRefresh(R.color.colorPrimary);
        WebView scrollView = mWebView.getScrollView();


        url = getIntent().getStringExtra("linkUrl");


       // generateWebView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();

                    final Elements elements = doc.select("div[class=text_L ]");

                    String html=elements.toString();
                    if (html.contains("src=\"")){
                        html = html.replace("src=\"","src=\""+"http://m.zhcw.com");
                    }
                    if (html.contains(".shtml")){
                        html = html.replace(".shtml","");
                    }

                    final String finalHtml = html;
                    mHander.post(new Runnable() {
                        @Override
                        public void run() {
                            saveObject(url.substring(url.lastIndexOf("/")+1, url.lastIndexOf(".")), finalHtml);
                            //wb_view1.loadData(String.valueOf(elements),"","utf-8");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();



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

    @Override
    protected void initView() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;


        }
    }

    /**
     * 生成web view
     */
    private void generateWebView() {
        WebSettings settings = wb_view1.getSettings();
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

    public  void saveObject(String name, String html) {

        if(html.contains("点击关注竞彩网微博：")){
            html=html.replace("点击关注竞彩网微博：","");
        }

        if(html.contains("新浪")){
            html=html.replace("新浪","");
        }

        if(html.contains("腾讯")){
            html=html.replace("腾讯","");
        }


        String filepath = Environment.getExternalStorageDirectory().getPath() + "/html";
        File file = new File(filepath);
        if (!file.exists()) {
            file.mkdirs();
        }

       // html="<html><head></head><body>"+html+"</body></html>";

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            file = new File(filepath, name + ".html");
            fos = new FileOutputStream(file);
//            fos = context.openFileOutput(name, Activity.MODE_APPEND);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(null);
            oos.writeObject(html);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
            String url1=file.getAbsolutePath();

            wb_view1.loadUrl("file://"+url1);
        }
    }


}
