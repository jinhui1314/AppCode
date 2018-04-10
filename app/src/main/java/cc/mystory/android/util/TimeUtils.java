package cc.mystory.android.util;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static cn.jpush.android.api.b.d;

/**
 * Create by Lei on 2016/9/30
 */

public class TimeUtils {
    private TimeUtils() throws Exception {
        throw new Exception("不能实例化本类");
    }

    /**
     * 将秒转换为hh:mm:ss格式
     *
     * @param time s
     * @return
     */
    public static String format(long time) {
        int h = 0;
        int m = 0;
        int s = 0;
        StringBuilder sb = new StringBuilder();
        if (time > 3600 * 24) {
            return null;
        } else {
            if (time >= 3600) {
                h = (int) time / 3600;
                time -= h * 3600;
            }
            if (time >= 60) {
                m = (int) time / 60;
                time -= m * 60;
            }
            if (time != 0) {
                s = (int) time;
            }
            if (h == 0) {

            } else if (h < 10) {
                sb.append("0" + h);
                sb.append(":");
            } else {
                sb.append(h);
                sb.append(":");
            }
            if (m < 10) {
                sb.append("0" + m);
            } else {
                sb.append(m);
            }
            sb.append(":");
            if (s < 10) {
                sb.append("0" + s);
            } else {
                sb.append(s);
            }
            return sb.toString();
        }
    }

    public static String formatToy(long time) {


        SimpleDateFormat time1 = new SimpleDateFormat("MM月dd日");
        return time1.format(time);
    }

    public static String formatymdhms(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日",
                Locale.getDefault());
        String time2 = sdf.format(time);

        String date = new SimpleDateFormat("yyyy年MM月dd ").format(new Date(time));
        /*SimpleDateFormat time1=new_commend SimpleDateFormat("yyyy-MM-dd HH:mm");
        return  time1.format(time);*/
        return date;
    }

    public static String format(long ms, String middle) {
        int h = 0;
        int m = 0;
        int s = 0;
        int ss = (int) (ms / 1000);
        StringBuilder sb = new StringBuilder();
        if (ss > 3600 * 24) {
            return null;
        } else {
            if (ss >= 3600) {
                h = (int) ss / 3600;
                ss -= h * 3600;
            }
            if (ss >= 60) {
                m = (int) ss / 60;
                ss -= m * 60;
            }
            if (ss != 0) {
                s = (int) ss;
            }
            if (h == 0) {

            } else if (h < 10) {
                sb.append("0" + h);
                sb.append(middle);
            } else {
                sb.append(h);
                sb.append(middle);
            }
            if (m < 10) {
                sb.append("0" + m);
            } else {
                sb.append(m);
            }
            sb.append(middle);
            if (s < 10) {
                sb.append("0" + s);
            } else {
                sb.append(s);
            }
            return sb.toString();
        }
    }

    public static int deFormat(String time) {
        if (time.contains(":")) {
            String times[] = time.split(":");
            int totalTime = 0;
            if (times.length == 3) {
                return Integer.parseInt(times[0]) * 3600 +
                        Integer.parseInt(times[1]) * 60 +
                        Integer.parseInt(times[2]);

            } else if (times.length == 2) {
                return Integer.parseInt(times[0]) * 60 +
                        Integer.parseInt(times[1]);
            } else return -1;
        } else return -1;
    }

    /**
     * 将秒转换为mm:ss
     *
     * @param s
     * @return
     */
    public static String changeSecond2Time(String s) {
        if (s == null || s.isEmpty())
            return s;
        int seconds = Integer.parseInt(s);
        return format((long) seconds);
    }

    /**
     * 将ms转换为mm-dd hh:mm
     *
     * @param ms
     * @return
     */
    public static String changeMS2Time(String ms) {
        return changeMS2Time(ms, "MM-dd HH:mm");
    }

    public static String changeMS2Time(String ms, String format) {
        if (ms == null || ms.isEmpty())
            return ms;
        long mseconds = Long.parseLong(ms);
        return changeMS2Time(mseconds, format);
    }

    public static String changeMS2Time(long ms, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(ms);
    }

    /**
     * 获取时间格式 2007/10/10 星期一 10：10
     *
     * @param time
     * @return
     */
    public static String getNYRX(long time) {
        return TimeUtils.changeMS2Time(time, "yyyy/MM/dd ") + convertTimeAndWeek(time) + changeMS2Time(time, " hh:mm");
    }

    public static String changeMS2Time(long ms) {
        return changeMS2Time(ms, "yyyy-MM-dd HH:mm");
    }

    public static String changeMS2MD(long ms) {
        return changeMS2Time(ms, "MM-dd");
    }

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "s";
    private static final String ONE_MINUTE_AGO = "min";
    private static final String ONE_HOUR_AGO = "h";
    private static final String ONE_DAY_AGO = "天";
    private static final String ONE_MONTH_AGO = "月";
    private static final String ONE_YEAR_AGO = "年";


    /**
     * 将时间转换成距离当前多久
     *
     * @param time
     * @return
     */
    public static String formatto(long time) {
        long delta = new Date().getTime() - time;
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    public static String changtoday(long time) {
        long delta = new Date().getTime() - time;
        long days = toDays(delta);

//        long days = toDays(toHours(toMinutes(toSeconds(delta))));
        Logger.d("时间戳==" + delta + "天" + days);
        return (days <= 0 ? 1 : days)+"";
    }

    /**
     * 根据时间戳判断是周几
     */

    public static String convertTimeAndWeek(long longTime) {
        Calendar c = Calendar.getInstance(); // 日历实例
        c.setTime(new Date(longTime));

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int week = c.get(Calendar.DAY_OF_WEEK);
        int minute = c.get(Calendar.MINUTE);

        return converWeek(week);
    }

    /**
     * 转换数字的周为字符串的
     *
     * @param w
     * @return
     */
    public static String converWeek(int w) {
        String week = null;

        switch (w) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }

        return week;
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
