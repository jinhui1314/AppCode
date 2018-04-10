package cc.mystory.android.moudle.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cc.mystory.android.moudle.ui.MainActivity;
import cn.jpush.android.api.JPushInterface;

import static cc.mystory.android.FragmentType.XIOAXI;


/**
 * Created by mac on 2017/4/25.
 */

public class InfoReceiver extends BroadcastReceiver {

    public static final String EXTRA_BUNDLE = "launchBundle";

    @Override
    public void onReceive(Context context, Intent intent) {
        //判断app进程是否存活
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //保存消息状态
//            SharePrefHelper.getInstance(context).put(SharePrefHelper.MESSAGE, true);
            if (isAppRunning(context, "cc.mystory.android")) {
                //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
                //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
                //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
                //DetailActivity前，要先启动MainActivity。
                Log.i("NotificationReceiver", "the app process is alive");
                Intent mainIntent = new Intent(context, MainActivity.class);
                //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
                //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
                //如果Task栈不存在MainActivity实例，则在栈顶创建
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent detailIntent = new Intent(context, MainActivity.class);

                detailIntent.putExtra("type", XIOAXI);

                mainIntent.putExtra("type1", "InfoReceiver");

                Bundle args = new Bundle();
                args.putString("type1", "infoReceiver");
                mainIntent.putExtra(EXTRA_BUNDLE, args);
                Intent[] intents = {mainIntent, detailIntent};

                context.startActivities(intents);
            } else {
                //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
                //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入             //参数跳转到DetailActivity中去了
                Log.i("NotificationReceiver", "the app process is dead");
                Intent launchIntent = context.getPackageManager().
                        getLaunchIntentForPackage("cc.mystory.android");

                launchIntent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

                Bundle args = new Bundle();
                args.putString("type1", "infoReceiver");
                launchIntent.putExtra(EXTRA_BUNDLE, args);
                context.startActivity(launchIntent);
            }
        }
    }

    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
