package cc.mystory.android.base;

/**
 * Created by jiang on 2017/6/12.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import cc.mystory.android.R;
import cc.mystory.android.util.view.ViewPagerFragment;


/**
 * 所有需要刷新的Fragment的基类
 * <p>
 * Create by Lei on 2016/9/5
 */
public abstract class BaseFragment extends ViewPagerFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,IBaseViewNew {
    protected EasyRecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    public static RecyclerArrayAdapter adapter;
    protected SpaceDecoration itemDecoration;

    /**
     * 设置LayoutManager
     */
    public abstract void setLayoutManager();

    /**
     * 设置Adapter
     */
    public abstract void setAdapter();
    public abstract void initPresenter();

    /**
     * 点击事件
     *
     * @param position
     */
    public abstract void onRecyclerItemClick(int position);

    /**
     * 长按事件
     *
     * @param position
     */
    public abstract void onRecyclerItemLongClick(int position);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_base, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
        initPresenter();
        setLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

//        itemDecoration = new_commend SpaceDecoration((int) UiUtils.convertDpToPixel(8, getActivity()));
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            itemDecoration.setPaddingEdgeSide(true);
            itemDecoration.setPaddingStart(false);
            itemDecoration.setPaddingHeaderFooter(false);
        } else {
            itemDecoration.setPaddingEdgeSide(false);
            itemDecoration.setPaddingStart(false);
            itemDecoration.setPaddingHeaderFooter(false);
        }

        recyclerView.addItemDecoration(itemDecoration);*/

        setAdapter();
        recyclerView.setAdapterWithProgress(adapter);
        if (adapter != null) {
            adapter.setMore(R.layout.view_more, this);
            adapter.setNoMore(R.layout.view_nomore);

            adapter.setError(R.layout.view_error);/*.setOnClickListener(new_commend View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                adapter.resumeMore();//?????????????
                }
            });*/

            adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    onRecyclerItemClick(position);
                }
            });

            adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(int position) {
                    onRecyclerItemLongClick(position);

                    return true;
                }
            });
            recyclerView.setRefreshListener(this);
            onRefresh();
        }

    }

    /**
     * 刷新时进行的操作
     */
    public abstract void refresh();

    /**
     * 加载更多时进行的操作
     */
    public abstract void loadMore();

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMore() {
        loadMore();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
//            if (adapter.getAllData().size() == 0)
//            onRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        //MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}

