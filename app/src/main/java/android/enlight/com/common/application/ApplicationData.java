package android.enlight.com.common.application;

import android.app.Activity;
import android.app.Application;
import android.enlight.com.common.exception.CrashException;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zyc on 2017/8/15.
 */

public class ApplicationData extends Application{
    /** 全局上下文 */
    public static ApplicationData globalContext;
    /**创建本地目录*/
    public static String softwareRotoPath;
    /** 用于保存当前打开的activity,为了不影响系统回收activity，所以用软引用 */
    private final HashMap<String, SoftReference<Activity>> activityMaps = new HashMap<String, SoftReference<Activity>>();
    @Override
    public void onCreate() {
        super.onCreate();
        globalContext=this;
        //创建全局的崩溃日志抓取
        CrashException crashException = CrashException.getInstance();
        crashException.init(getApplicationContext());
    }
    /**
     * 将打开的activity添加到map集合
     *
     * @param activity
     *            打开的activity
     */
    public void addActivityToMap(Activity activity) {
        activityMaps.put(activity.getClass().getName(), new SoftReference<Activity>(
                activity));
    }
    /**
     * activity关闭时 将其从列表中移除 便于系统及时回收
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityMaps.remove(activity.toString());
    }

    /**
     * 判断当前Activity栈状态
     * @return
     */
    public boolean isEmpty() {
        return activityMaps.isEmpty();
    }
    /**
     * 循环遍历并退出列表中打开的activity
     */
    public void exit() {
        for (Iterator<Map.Entry<String, SoftReference<Activity>>> iterator = activityMaps
                .entrySet().iterator(); iterator.hasNext();) {
            SoftReference<Activity> activityReference = iterator.next()
                    .getValue();
            Activity activity = activityReference.get();
            if (activity != null) {
                activity.finish();
            }
        }
        activityMaps.clear();
        System.exit(0);
    }
}
