package cc.mystory.android.moudle.view;

import java.util.List;

import cc.mystory.android.moudle.ui.caipiao.CaiPiaoInfoModle;

/**
 * Created by edianzu on 2017/9/1.
 */

public interface IZiXunView {
    void doGetSucess(List<CaiPiaoInfoModle> list, boolean loadMore);
}
