package android.enlight.com.common.data;

import android.enlight.com.common.utils.Tools;

/**
 * Created by zyc on 2017/8/15.
 * 全局常量
 */

public class AppConstant {
    /** 机身根目录 */
    public static final String dataRootPath = android.os.Environment
            .getDataDirectory() + "/data/" + Tools.getPackageName() + "/files/";
    /** 客户端文件根目录 */
    public static final String softwareRootPath = "/enlight/";
    /** 崩溃日志存放目录*/
    public static final String crashPath="/crash/";
}
