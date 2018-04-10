package cc.mystory.android.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Map;

import cc.mystory.android.base.BasePresenterNew;
import cc.mystory.android.base.IBaseViewNew;
import cc.mystory.android.moudle.ui.caipiao.CaiPiaoInfoModle;
import cc.mystory.android.moudle.view.IZiXunView;


/**
 * Created by jiang on 2017/6/13.
 */

public class MyZiXunPresenter extends BasePresenterNew {

    IZiXunView iZiXunView;

    public MyZiXunPresenter(Context context, IBaseViewNew baseView, IZiXunView iZiXunView) {
        super(context, baseView);
        this.iZiXunView = iZiXunView;
    }


    public void getZiXun(Map<String, Object> map, final boolean isMore) {

        getList("clienth5.do", map, CaiPiaoInfoModle.class, new OnResponse<List<CaiPiaoInfoModle>>() {
            @Override
            public void onSuccess(List<CaiPiaoInfoModle> data) {

                Log.d("=====数据", data.toString());
                iZiXunView.doGetSucess(data,isMore);

            }

            @Override
            public void onSuccessEmptyOrNull() {

            }

            @Override
            public void onOtherCode(int code, String info) {

            }
        });
    }
}
