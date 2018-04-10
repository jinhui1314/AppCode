package cc.mystory.android.moudle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cc.mystory.android.R;
import cc.mystory.android.adapter.MyJiQiaoAdapter;
import cc.mystory.android.model.JiQiaoModle;
import cc.mystory.android.moudle.ui.main.JiQiaoInfoActivity;
import cc.mystory.android.util.UiUtils;


public class JiQiaoRecyclerViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAdapterView.OnListLoadListener {
    private SwipeRefreshRecyclerView swipeRefreshRecyclerView;
    private RecyclerArrayAdapter adapter;
    Context context;
    int from = 0;
    TextView tv_empty;

    List list=new ArrayList();
    @SuppressLint("ValidFragment")
    public JiQiaoRecyclerViewFragment() {
    }

    @SuppressLint("ValidFragment")
    public JiQiaoRecyclerViewFragment(Context context) {
        this.context = context;
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

        adapter = new MyJiQiaoAdapter(context);

        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, JiQiaoInfoActivity.class);
                intent.putExtra("linkUrl", ((JiQiaoModle)list.get(position)).getId());
                startActivity(intent);
            }
        });
        swipeRefreshRecyclerView.setEmptyText("数据又没有了!");
        swipeRefreshRecyclerView.setAdapter(adapter);

        onRefresh();
    }
    private Handler mHander = new Handler();

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://www.zhcw.com/ssq/szjq/").get();
                    final List<JiQiaoModle> list=parseHtml(doc);
                    mHander.post(new Runnable() {
                        @Override
                        public void run() {
                            onListLoad(list);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private List<JiQiaoModle> parseHtml(Document document) {
        Elements elements = document.select("div[class=Hleftbox2] > ul > li");
        List<JiQiaoModle> list = new ArrayList<>();

        JiQiaoModle data;
        for (Element element : elements) {
            data = new JiQiaoModle();
            if(element.select("span").size()>2) {
                Element element1 = element.select("span").get(0);
                Elements elements2 = element1.select("span > a");
                String v = elements2.get(0).attributes().get("href");

                data.setTitle(elements2.text());//开奖详情地址
                data.setId(v);//开奖描述
                data.setNat(element.select("span").get(1).text());
                data.setTime(element.select("span").get(2).text());//开奖日期

                list.add(data);
            }
        }

        return list;
    }
    @Override
    public void onListLoad() {
        UiUtils.Toast(context, "已经没有更多了");
        swipeRefreshRecyclerView.setLoading(false);

    }

    public void onListLoad(List<JiQiaoModle> list) {
        swipeRefreshRecyclerView.setLoading(false);
        swipeRefreshRecyclerView.setRefreshing(false);

        this.list.clear();
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
