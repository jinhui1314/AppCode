package cc.mystory.android.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import cc.mystory.android.R;
import cc.mystory.android.model.KaiJIangModel;

/**
 * Created by jiang on 2017/6/27.
 */


public class MyKaiJiangViewHolder extends BaseViewHolder<KaiJIangModel> {
    TextView tv_title, tv_time, tv_num;


    public MyKaiJiangViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);


        tv_time = $(R.id.tv_time);
        tv_title = $(R.id.tv_title);
        tv_num = $(R.id.tv_num);

    }

    @Override
    public void setData(final KaiJIangModel data) {
        super.setData(data);
        tv_time.setText(data.getQishu());

        tv_title.setText(data.getTitle());

        tv_num.setText(data.getNum());
    }
}


