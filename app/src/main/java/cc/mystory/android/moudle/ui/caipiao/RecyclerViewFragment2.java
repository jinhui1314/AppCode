package cc.mystory.android.moudle.ui.caipiao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.mystory.android.R;
import cc.mystory.android.adapter.MyZiXunAdapter;
import cc.mystory.android.base.IBaseViewNew;
import cc.mystory.android.model.StoryBean;
import cc.mystory.android.moudle.ui.main.CaiPiaoInfoActivity;
import cc.mystory.android.moudle.view.IZiXunView;
import cc.mystory.android.presenter.MyZiXunPresenter;
import cc.mystory.android.util.BetterRecyclerView;
import cc.mystory.android.util.UiUtils;


public class RecyclerViewFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAdapterView.OnListLoadListener, IBaseViewNew,IZiXunView {
    private SwipeRefreshRecyclerView swipeRefreshRecyclerView;
    private RecyclerArrayAdapter adapter;
    private static List<StoryBean> objects = new ArrayList<>();
    private int index;
    private int footerIndex = 10;

    Context context;
    static Map<String, Object> map = new HashMap<>();

    MyZiXunPresenter mPresenter;
    BetterRecyclerView bt_recycler;
    static List<CaiPiaoInfoModle> list;
    int from = 0;
    TextView tv_empty;


    int busiCode;
    @SuppressLint("ValidFragment")
    public RecyclerViewFragment2() {
    }

    @SuppressLint("ValidFragment")
    public RecyclerViewFragment2(Context context, int busiCode) {
        this.context = context;
        this.busiCode = busiCode;
        RecyclerViewFragment2 fragment = new RecyclerViewFragment2();
        mPresenter = new MyZiXunPresenter(context, this, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshRecyclerView = (SwipeRefreshRecyclerView) view.findViewById(R.id.swipeRefresh);
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        swipeRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        swipeRefreshRecyclerView.setOnListLoadListener(this);
        swipeRefreshRecyclerView.setOnRefreshListener(this);


        adapter = new MyZiXunAdapter(context);
        list=new ArrayList<>();

        swipeRefreshRecyclerView.setEmptyText("数据又没有了!");
        swipeRefreshRecyclerView.setAdapter(adapter);

        onRefresh();

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, CaiPiaoInfoActivity.class);
                intent.putExtra("linkUrl", list.get(position).getUrl());

                startActivity(intent);
            }
        });

    }

    long time = System.currentTimeMillis();

    int page = 1;

    @Override
    public void onRefresh() {
//        UiUtils.showIndeterminateProgressDialog(context, "正在获取数据", "请稍候。。。", false);

        page = 1;
        map.put("transactionType", "8021");
        map.put("pageNo", 1);
        map.put("pageSize", 20);
        map.put("busiCode", busiCode);
        map.put("src", "0000100001%7C6000003060");

        Logger.d("请求参数" + map.toString());

        if (mPresenter != null)
            mPresenter.getZiXun(map, false);

    }

    @Override
    public void onListLoad() {
        page = page + 1;

        map.put("pageNo", page);

        mPresenter.getZiXun(map, true);

    }

    @Override
    public void showFailedError(int code, String message) {

        UiUtils.Toast(context, message);
    }

    @Override
    public void onEmptyOrNull() {

    }

    @Override
    public void onOtherCode(int code, String info) {
        swipeRefreshRecyclerView.setLoading(false);
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
        UiUtils.hideDialog();

    }

    @Override
    public void doGetSucess(List<CaiPiaoInfoModle> list, boolean loadMore) {
        swipeRefreshRecyclerView.setLoading(false);
        hideLoading();

        if (!loadMore) {
            this.list.clear();
            this.list=list;
            if (list.isEmpty()) {

                swipeRefreshRecyclerView.setEmptyText("数据又没有了!");
                tv_empty.setVisibility(View.VISIBLE);
//                adapter.pauseMore();

            } else {
                tv_empty.setVisibility(View.GONE);

                adapter.clear();

                adapter.addAll(list);
                adapter.notifyDataSetChanged();
                swipeRefreshRecyclerView.setRefreshing(false);
            }

        } else {

            this.list.addAll(list);
            if (list.isEmpty()) {
                UiUtils.Toast(context, "已经没有更多了");
            } else {
//                adapter.pauseMore();
                adapter.addAll(list);
                adapter.notifyDataSetChanged();
                swipeRefreshRecyclerView.setLoading(false);
            }
        }
    }
}
