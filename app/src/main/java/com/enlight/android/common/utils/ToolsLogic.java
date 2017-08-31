package com.enlight.android.common.utils;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import com.enlight.android.common.application.ApplicationData;

/**
 * Created by zyc on 2017/8/15.
 */

public class ToolsLogic {
    /**
     * 退出软件
     */
    public static void exitApp() {
        Context context = ApplicationData.globalContext;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        // 退出所有activity
        ApplicationData.globalContext.exit();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.restartPackage(context.getPackageName());
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
