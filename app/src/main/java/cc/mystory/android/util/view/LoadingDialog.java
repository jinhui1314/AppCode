package cc.mystory.android.util.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.mystory.android.R;
import cc.mystory.android.util.UiUtils;


/**
 * Created by jiang on 2017/3/16.
 */

public class LoadingDialog {
    LVCircularRing mLoadingView;
    Dialog mLoadingDialog;
    TextView loadingText;
    Context mContext;

    public LoadingDialog(Context context, String msg) {
        mContext = context;
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.loading_dialog_view, null);
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = (LVCircularRing) view.findViewById(R.id.lv_circularring);
        // 页面中显示文本
        loadingText = (TextView) view.findViewById(R.id.loading_text);
        // 显示文本
        loadingText.setText(msg);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);

        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void setText(String s) {
        loadingText.setText(s);

    }

    public void show() {
        mLoadingDialog.show();
        mLoadingDialog.setCancelable(true);
        mLoadingView.startAnim();
    }

    public void close() {
        if (mLoadingDialog != null) {
            mLoadingView.stopAnim();
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public void setLisenter(final Context context) {
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (b)
                    UiUtils.Toast(context, "后台下载中。。。");
            }
        });
    }

    boolean b = true;

    public void setCancle() {
        b = false;
    }
}
