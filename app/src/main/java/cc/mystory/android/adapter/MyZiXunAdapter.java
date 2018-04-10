package cc.mystory.android.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import cc.mystory.android.R;
import cc.mystory.android.moudle.ui.caipiao.CaiPiaoInfoModle;

/**
 * Created by jiang on 2017/6/12.
 */

public class MyZiXunAdapter extends RecyclerArrayAdapter<CaiPiaoInfoModle> {
    private List list;
    BaseViewHolder holder1 = null;
    Context context;

    public MyZiXunAdapter(Context context) {
        super(context);
        this.context = context;

    }

    private final static int ONE_TYPE = 1;
    private final static int TWE_TYPE = 2;

    @Override
    public int getViewType(int position) {
        return ONE_TYPE;

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;

        layout = R.layout.list_items_story_zhuye2;

        holder1 = new MyZiXunViewHolder(parent, layout);
        return holder1;
    }


    public interface IOnItemClickListener {
        void onItemClick(int position, String id, String img_url, String title);
    }

}