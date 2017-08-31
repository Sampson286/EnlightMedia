package com.enlight.android.common.network;

import com.enlight.android.common.utils.Tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求头类
 *
 * @author zyc
 */
public class RequestHeaders {

    /**
     * 获取header
     *
     * @return
     */
    public static Map<String, String> getHeaders() {
        Map<String, String> map = new HashMap<String, String>();
        try {
            map.put("X-Client", getXClient());
            map.put("sessionid", getCookie());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取cookie
     *
     * @return
     */
    private static String getCookie() {
//    	return "sessionid="+ToolsPreferences.getPreferences(ToolsPreferences.sessionKey);
        return "";
    }

    /**
     * webView请求时发送的客户端信息
     *
     * @return xClient信息
     */
    public static Map<String, String> webViewXClient() {
        Map<String, String> map = new HashMap<String, String>();
        try {
            map.put("X-Client", getXClient());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获得上传的X-Client信息
     */
    private static String getXClient() {
        String sdk = "";
        try {
            sdk = URLEncoder.encode(Tools.getSDK(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String type = "";
        try {
            type = URLEncoder.encode(Tools.getPhoneModel(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String rootPath = "";
        try {
            rootPath = URLEncoder.encode((Tools.getRootPath()), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//		String channelId = CbcTools.hot == null ? Tools.getString(R.string.default_channel) : CbcTools.hot.getChannelId();
        String screenSize = Tools.getScreenWidth() + "*" + Tools.getScreenHeight();
        String imei = Tools.getIMEI();
        String imsi = Tools.getIMSI();
        String mac = Tools.getMacAddress();
        String cell_id = Tools.getNetWorkBaseStation();
        String version = Tools.getVersionName();
        String rn = Tools.getRandomNumber();

        String xClient =
                "sdk=" + sdk + ";" +
                        "screenSize=" + screenSize + ";" +
                        "type=" + type + ";" +
                        "imei=" + imei + ";" +
                        "imsi=" + imsi + ";" +
                        "cell_id=" + cell_id + ";" +
                        //在版本号中加上渠道号
                        "version=" + version + ";" +
                        "mac=" + mac + ";" +
                        "rootPath=" + rootPath + ";" +
                        "rn=" + rn + ";";
        return xClient;
    }

}
