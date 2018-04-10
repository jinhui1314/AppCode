package cc.mystory.android.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import cc.mystory.android.R;
import cc.mystory.android.model.LeTouModle;

/**
 * Created by jiang on 2017/6/27.
 */


public class MyLeTouViewHolder extends BaseViewHolder<LeTouModle> {
    TextView tv_title, tv_time;


    public MyLeTouViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);


        tv_time = $(R.id.tv_time);
        tv_title = $(R.id.tv_title);

    }

    @Override
    public void setData(final LeTouModle data) {
        super.setData(data);
        tv_time.setText(data.getDate());
        tv_title.setText(data.getTitle().trim());
    }
}


