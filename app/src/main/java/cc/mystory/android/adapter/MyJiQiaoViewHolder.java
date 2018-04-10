package cc.mystory.android.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import cc.mystory.android.R;
import cc.mystory.android.model.JiQiaoModle;

/**
 * Created by jiang on 2017/6/27.
 */


public class MyJiQiaoViewHolder extends BaseViewHolder<JiQiaoModle> {
    TextView tv_title, tv_time,tv_nat;


    public MyJiQiaoViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);


        tv_time = $(R.id.tv_time);
        tv_title = $(R.id.tv_title);
        tv_nat = $(R.id.tv_nat);


    }

    @Override
    public void setData(final JiQiaoModle data) {
        super.setData(data);
        tv_time.setText(data.getTime());
        tv_title.setText(data.getTitle().trim());
        tv_nat.setText(data.getNat());
    }
}


