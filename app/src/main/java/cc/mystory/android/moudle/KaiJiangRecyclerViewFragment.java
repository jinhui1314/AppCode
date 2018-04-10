package cc.mystory.android.moudle;

import android.annotation.SuppressLint;
import android.content.Context;
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
import cc.mystory.android.adapter.MyKaiJiangAdapter;
import cc.mystory.android.model.KaiJIangModel;
import cc.mystory.android.util.UiUtils;


public class KaiJiangRecyclerViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAdapterView.OnListLoadListener {
    private SwipeRefreshRecyclerView swipeRefreshRecyclerView;
    private RecyclerArrayAdapter adapter;
    Context context;
    int from = 0;
    TextView tv_empty;

    List list = new ArrayList();

    @SuppressLint("ValidFragment")
    public KaiJiangRecyclerViewFragment() {
    }

    @SuppressLint("ValidFragment")
    public KaiJiangRecyclerViewFragment(Context context) {
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

        adapter = new MyKaiJiangAdapter(context);

        /*adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, StoryInfoActivity.class);
                intent.putExtra("linkUrl", ((LeTouModle)list.get(position)).getUrl());
                startActivity(intent);
            }
        });*/
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
                    Document doc = Jsoup.connect("http://caipiao.163.com/award/")
                            .timeout(30000)
                            .userAgent("UA")
                            .validateTLSCertificates(false).get();
                    final List<KaiJIangModel> list = parseHtml(doc);

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
            if (data.getNum() != null && data.getNum() != "")
                list.add(data);
        }

        return list;
    }

    @Override
    public void onListLoad() {
        UiUtils.Toast(context, "已经没有更多了");
        swipeRefreshRecyclerView.setLoading(false);

    }

    public void onListLoad(List<KaiJIangModel> list) {
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
