package cc.mystory.android.util;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cc.mystory.android.R;
import cc.mystory.android.util.view.LoadingDialog;

/**
 * 对话框、菜单、Toast、SnakeBar等控制类
 * Create by Lei on 2016/9/20
 */

public class UiUtils {
    /**
     * 禁止实例化
     *
     * @throws RuntimeException
     */
    private UiUtils() throws RuntimeException {
        throw new RuntimeException("此类不支持实例化");
    }

    public static MaterialDialog dialog = null;
    static LoadingDialog dialog1;

    /**
     * 显示一个正在加载的对话框
     *
     * @param context
     * @param title
     * @param content
     * @param horizontal 对话框样式设置
     */
    public static void showIndeterminateProgressDialog(Context context, String title, String content, boolean horizontal) {
        dialog1 = new LoadingDialog(context, title);
        try {
            dialog1.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        /*dialog = new_commend MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .show();

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);*/

    }

    public static void setDialogText(String s) {
        if (dialog1 != null)
            dialog1.setText(s);
    }

    public static void showProgressDialog(Context context, int process) {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(context)
                    .title("文件上传中")
                    .content("正在上传音频文件，请稍候...")
                    .progress(false, 100, false)
                    .cancelable(false).positiveText("取消")
                    .show();

        }
        dialog.setProgress(process);
        if (process == 100) {
            dialog.dismiss();
        }
    }

    public static void setDialogListener(Context context){
        dialog1.setLisenter(context);
    }
    static MaterialDialog inputDialog = null;

    public static void hideInputDialog() {
        if (inputDialog != null) {
            inputDialog.dismiss();
            inputDialog.cancel();
            inputDialog = null;
            System.gc();
        }
    }

    public static void showInputDialog(Context context,
                                       String title,
                                       int start,
                                       int end,
                                       String hint,
                                       MaterialDialog.InputCallback callback) {
        inputDialog = new MaterialDialog.Builder(context)
                .title(title)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(start, end)
                .positiveText("确定")
                .input(hint, hint, false, callback).show();
    }


    /**
     * 让对话框消失
     */
    public static void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
            System.gc();
        }
        if (dialog1 != null) {
            dialog1.close();
            dialog1 = null;
            System.gc();
        }
    }


    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    /**
     * Toast
     *
     * @param context
     * @param message
     */
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



    static CountDownTimer countDownTimer = null;

    public static void Toast(final Context context, final String message) {
        Logger.d("提示消息====" + message);
        //是否包括中文
        for (int i=message.length(); --i>=0;) {
            String b = message.substring(i, i + 1);
            boolean c = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", b);
            if (c) {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }

    }
    /**
     * 弹出一个选择删除
     *
     * @param context
     * @param listCallback
     */
    public static void chooseDelete(Context context, MaterialDialog.ListCallback listCallback) {
        new MaterialDialog.Builder(context)
                .items(R.array.ppt_delete)
                .itemsCallback(listCallback)
                .show();
    }


    /**
     * 弹出一个选择删除
     *
     * @param context
     * @param listCallback
     */
    public static void chooseDeleteAndEdit(Context context, MaterialDialog.ListCallback listCallback) {
        new MaterialDialog.Builder(context)
                .items(R.array.ppt_delete_edit)
                .itemsCallback(listCallback)
                .show();
    }
    private static final int RequestCode = 1;

    public static void setCancleListener() {
        dialog1.setCancle();
    }

    /*public static void goLogin(Activity activity) {
        System.gc();
        Intent intent = new_commend Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, RequestCode);

//        activity.startActivity(intent);
        ((Activity) activity).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }*/


    public interface OnCountDownListener {
        void onTick(long millisUntilFinished);

        void onFinish();
    }

    public static void countDownTimer(int seconds, final OnCountDownListener countDownListener) {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (countDownListener != null) {
                        countDownListener.onTick(millisUntilFinished);
                    }
                }

                @Override
                public void onFinish() {
                    if (countDownListener != null) {
                        countDownListener.onFinish();
                    }
                }
            };
        } else {
            countDownTimer.start();
        }
    }

    public static void countCancle() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    /**
     * 倒计时
     *
     * @param seconds
     */
    public static void countDown(int seconds, final OnCountDownListener countDownListener) {

        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (countDownListener != null) {
                    countDownListener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (countDownListener != null) {
                    countDownListener.onFinish();
                }
            }
        };
        countDownTimer.start();

    }

    /**
     * 倒计时
     *
     * @param seconds
     */
    public static void countDownSocket(int seconds, final OnCountDownListener countDownListener) {
        countDownTimer = new CountDownTimer(seconds * 100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (countDownListener != null) {
                    countDownListener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (countDownListener != null) {
                    countDownListener.onFinish();
                }
            }
        };
        countDownTimer.start();
    }

    public static void cancelCountDown() {
        if (countDownTimer != null)
            countDownTimer.cancel();
    }


    /**
     * 监听软键盘状态，并在软键盘状态改变时回调
     *
     * @param activity
     * @param listener
     */
    public static void observeSoftKeyboard(Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            int previousKeyboardHeight = -1;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                int height = decorView.getHeight();
                int keyboardHeight = height - displayHeight;
                if (previousKeyboardHeight != keyboardHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    listener.onSoftKeyBoardChange(keyboardHeight, !hide);
                }

                previousKeyboardHeight = height;

            }
        });
    }

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeybardHeight, boolean visible);
    }

    /**
     * 纵向滑动View，以自适应软键盘出现
     *
     * @param view
     * @param action
     * @param startY
     * @param toY
     */
    public static void slideView(View view, String action, float startY, float toY) {
        ObjectAnimator.ofFloat(view, action, startY, toY).setDuration(500).start();
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }


    /**
     * 修改状态栏为全透明
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }



}
