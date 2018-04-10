package cc.mystory.android.moudle.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.mystory.android.R;
import cc.mystory.android.adapter.FragmentAdapter;
import cc.mystory.android.moudle.ui.caipiao.RecyclerViewFragment;
import cc.mystory.android.moudle.ui.caipiao.RecyclerViewFragment1;
import cc.mystory.android.moudle.ui.caipiao.RecyclerViewFragment2;
import cc.mystory.android.moudle.ui.caipiao.RecyclerViewFragment3;
import cc.mystory.android.moudle.ui.caipiao.RecyclerViewFragment4;
import cc.mystory.android.util.view.ViewPagerFragment;

import static cc.mystory.android.R.id.viewpager;

/**
 * Created by jinhui on 2018/3/26.
 */

public class CaiPiaoZiXunFragment extends ViewPagerFragment {

    ViewPager mViewPager;
    MagicIndicator magicIndicator;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_caipiao, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(viewpager);

        magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator1);

        initData();
    }

    private void initData() {
        initMagicIndicator1();

        List fragments=new ArrayList();
        fragments.add(new RecyclerViewFragment(getContext(),300203));
        fragments.add(new RecyclerViewFragment1(getContext(),300205));
        fragments.add(new RecyclerViewFragment2(getContext(),300207));
        fragments.add(new RecyclerViewFragment3(getContext(),300209));
        fragments.add(new RecyclerViewFragment4(getContext(),300211));
//        fragments.add(new RecyclerViewFragment5(getContext(),300213));
//        fragments.add(new RecyclerViewFragment6(getContext(),300215));



        mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments) );

//        mViewPager.setOffscreenPageLimit(6);

    }
    private List<String> mDataList;
    private static String[] CHANNELS= new String[]{"头 条", "彩 讯", "公 益", "视 频", "政 策"};

    private void initMagicIndicator1() {
        mDataList = Arrays.asList(CHANNELS);


        magicIndicator.setBackgroundColor(getResources().getColor(R.color.transparent));
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setSkimOver(true);
        int padding = UIUtil.getScreenWidth(getContext()) / 2;
//        commonNavigator.setRightPadding(padding);
//        commonNavigator.setLeftPadding(padding);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#D1D1D1"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setPadding(45, 0, 41, 0);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }
}
