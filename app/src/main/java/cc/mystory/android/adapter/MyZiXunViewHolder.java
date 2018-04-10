package cc.mystory.android.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import cc.mystory.android.R;
import cc.mystory.android.moudle.ui.caipiao.CaiPiaoInfoModle;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jiang on 2017/6/27.
 */


public class MyZiXunViewHolder extends BaseViewHolder<CaiPiaoInfoModle> {
    TextView tv_tag, tv_username, tv_time, tv_title, tv_description, tv_favarite, tv_pinglun_num, tv_favorite_num;
    CircleImageView cv_hearder;
    ImageView iv_image;
    LinearLayout ll_list_item;
    RelativeLayout rl_header;

    public MyZiXunViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);

        tv_username = $(R.id.tv_username);
        tv_time = $(R.id.tv_time);
        tv_title = $(R.id.tv_title);
        tv_description = $(R.id.tv_description);
        tv_favarite = $(R.id.tv_favorite);
        tv_pinglun_num = $(R.id.tv_pinglun_num);
        tv_favorite_num = $(R.id.tv_favorite_num);

        cv_hearder = $(R.id.cv_header);
        iv_image = $(R.id.iv_image);
        ll_list_item = $(R.id.ll_list_item);
        rl_header=$(R.id.rl_header);
    }

    @Override
    public void setData(final CaiPiaoInfoModle data) {
        super.setData(data);


        tv_favarite.setText("0");


        if (data.getTitle() != null && data.getTitle() != "") {

            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(data.getTitle());
        } else {
            tv_title.setVisibility(View.GONE);
        }

        if (data.getSummary() != null) {

                tv_description.setText(data.getSummary());

        }

        //加载图片
        Glide.with(getContext()).load(data.getLogoFile()).into(iv_image);

        /*if (iv_image != null && data.getImage().contains("http")) {
            Glide.with(getContext()).load(data.getImage()).into(iv_image);

        } else if*/

    }
}


