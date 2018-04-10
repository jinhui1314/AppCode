package cc.mystory.android.moudle.ui;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cc.mystory.android.R;
import cc.mystory.android.adapter.FragmentAdapter;
import cc.mystory.android.base.BaseActivity;
import cc.mystory.android.model.KaiJIangModel;
import cc.mystory.android.moudle.JiQiaoRecyclerViewFragment;
import cc.mystory.android.moudle.KaiJiangRecyclerViewFragment;
import cc.mystory.android.moudle.ZiXunRecyclerViewFragment;
import cc.mystory.android.moudle.ui.main.CaiPiaoZiXunFragment;
import cc.mystory.android.util.UiUtils;
import cc.mystory.android.util.view.NoScrollViewPager;

/**
 * Created by jinhui on 2018/3/26.
 */

public class MainActivity extends BaseActivity {

    NoScrollViewPager noScrollViewPager;
    TextView tv_title;
    /* RadioGroup导航 */
    private RadioGroup mNavigationGroup;

    @Override
    protected int setLayoutId() {
        return R.layout.web_mian;
    }

    @Override
    protected void findView() {
        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mNavigationGroup = (RadioGroup) findViewById(R.id.radioGroup);

        tv_title = (TextView) findViewById(R.id.tv_title);

        List fragments = new ArrayList();
        fragments.add(new CaiPiaoZiXunFragment());
        fragments.add(new JiQiaoRecyclerViewFragment(this));

        fragments.add(new KaiJiangRecyclerViewFragment(this));
        fragments.add(new ZiXunRecyclerViewFragment(this));

        noScrollViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));

        noScrollViewPager.setNoScroll(true);
        /*noScrollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mNavigationGroup.check(R.id.rb_More);
                } else if (position == 1) {
                    mNavigationGroup.check(R.id.rb_Course_Center);

                } else if (position == 2) {
                    mNavigationGroup.check(R.id.rb_study);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
        noScrollViewPager.setOffscreenPageLimit(4);
        mNavigationGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_Course_Center:
                        noScrollViewPager.setCurrentItem(1);
                        //                            ImageLoader.getInstance().clearCache();
                        tv_title.setText("彩票开奖");
                        break;
                    case R.id.rb_My_Course:
                        noScrollViewPager.setCurrentItem(3);
                        //                            ImageLoader.getInstance().clearCache();
//                        myCouresFragment.setTop();
                        break;
                    case R.id.rb_More:
                        tv_title.setText("彩票新闻");

                        noScrollViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_study:
                        tv_title.setText("乐透资讯");

                        noScrollViewPager.setCurrentItem(2);
                        break;

                }
            }
        });
    }


    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://caipiao.163.com/award/")
                            .timeout(30000)
                            .userAgent("UA")
                            .validateTLSCertificates(false).get();
                    final List<KaiJIangModel> list = parseHtml(doc);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        if(Build.VERSION.SDK_INT >= 23){
            //动态获取权限
            int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);



            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
                return;
            }else{

            }
        }
    }

    private List<KaiJIangModel> parseHtml(Document document) {
        Elements elements = document.select("table[class=awardList] > tbody > tr");
        List<KaiJIangModel> list = new ArrayList<>();
        KaiJIangModel data;
        for (Element element : elements) {
            data = new KaiJIangModel();

            Elements elements1 = element.select("td > a");

            data.setTitle(elements1.get(0).text());//开奖描述
            data.setQishu(elements1.get(1).text());
            data.setNum(elements1.get(2).parentNode().toString());

            Elements ems = element.select("td em");//开奖结果
            StringBuilder sb = new StringBuilder();
            for (Element em : ems) {
                sb.append(em.text());
            }
            data.setNum(sb.toString());
            list.add(data);
        }

        return list;
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
    }

    /**
     * 重写返回
     */
    @Override
    public void onBackPressed() {
        showQuitTips();
    }

    private long firstPressTime = -1;// 记录第一次按下的时间
    private long lastPressTime;// 记录第二次按下的时间

    /**
     * 双击返回退出
     */
    private void showQuitTips() {
        // 如果是第一次按下 直接提示
        if (firstPressTime == -1) {
            firstPressTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }
        // 如果是第二次按下，需要判断与上一次按下的时间间隔，这里设置2秒
        else {
            lastPressTime = System.currentTimeMillis();
            if (lastPressTime - firstPressTime <= 2000) {
                System.exit(0);
            } else {
                firstPressTime = lastPressTime;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            }
        }
    }
}