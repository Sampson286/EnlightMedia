package com.enlight.android.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.enlight.android.common.application.ApplicationData;
import com.enlight.android.common.data.AppConstant;
import com.enlight.android.common.network.NetworkInfo;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by zyc on 2017/8/15.
 * 全局使用的工具类
 */

public class Tools {
    /**
     * 获取软件包名
     * @return
     */
    public static String getPackageName(){
        return ApplicationData.globalContext.getPackageName();
    }
    /**
     * 获取字符串
     * @return
     */
    public static String getString(int id){
        return ApplicationData.globalContext.getString(id);
    }
    /**
     * 获取平台号+版本号+渠道号
     * @return
     */
    public static String getVersionName() {
        try {
            PackageInfo pinfo = ApplicationData.globalContext.getPackageManager().getPackageInfo(getPackageName(), 0);
            //1 为平台号，android
            return pinfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 根据SD卡挂载和可读取状况获取程序根路径
     * @return 当前的根路径
     */
    public static String getRootFile(){
        String rootPath = "";
        //判断SD卡是否挂载并且有写的权限
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && Environment.getExternalStorageDirectory().canWrite()) {
            rootPath = getSdcardRootPath()+ AppConstant.softwareRootPath;
        } else {
            rootPath = getDataRootPath()+AppConstant.softwareRootPath;
        }
        return rootPath;
    }

    /**
     * 获取SD卡根路径
     * @return
     */
    public static String getSdcardRootPath(){
        return android.os.Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取机身路径
     * @return
     */
    public static String getDataRootPath(){
        return android.os.Environment.getDataDirectory()+"/data/"+getPackageName()+"/files/";
    }
    /**
     * 获取当前根路径
     * @return
     */
    public static String getRootPath(){
        String rootPath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && Environment.getExternalStorageDirectory().canWrite()) {
            rootPath = getSdcardRootPath();
        } else {
            rootPath = getDataRootPath();
        }
        return rootPath;
    }
    /********获取手机设备信息*********/
    /**
     * 获取屏幕宽度
     * @return 屏幕宽度
     */
    public static int getScreenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        dm = ApplicationData.globalContext.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return 屏幕高度
     */
    public static int getScreenHeight(){
        DisplayMetrics dm = new DisplayMetrics();
        dm = ApplicationData.globalContext.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
    /**
     * 获得系统版本号
     *
     * @return 当前系统版本
     */
    public static String getSDK() {
        try {
            String release = Build.VERSION.RELEASE;
            if(null != release){
                return release;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获得手机型号
     * @return
     */
    public static String getPhoneModel(){
        try{
            String phoneVersion = android.os.Build.MODEL;
            if(null != phoneVersion){
                return phoneVersion;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获得手机IMEI
     * @return
     */
    public static String getIMEI(){
        try{
            TelephonyManager tm = (TelephonyManager) ApplicationData.globalContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if(null != imei){
                return imei;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获得手机IMSI
     * @return
     */
    public static String getIMSI(){
        try{
            TelephonyManager tm = (TelephonyManager) ApplicationData.globalContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi = tm.getSubscriberId();
            if(null != imsi){
                return imsi;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取wifi的mac地址，需要ACCESS_WIFI_STATE权限
     * @return
     */
    public static String getMacAddress(){
        try{
            WifiManager wifi = (WifiManager) ApplicationData.globalContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String mac = info.getMacAddress();
            if(null != mac){
                return mac;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 基站信息
     * @return
     */
    public static String getNetWorkBaseStation() {
        //TODO:需要的时候在打开，需要添加权限 ：<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
/*		try {
			TelephonyManager tm = (TelephonyManager) ApplicationData.globalContext.getSystemService(Context.TELEPHONY_SERVICE);
			// 获取网络类型
			int type = tm.getNetworkType();
			// 在中国，移动的2G是EGDE，联通的2G为GPRS，电信的2G为CDMA，电信的3G为EVDO
			// NETWORK_TYPE_EVDO_A  中国电信3G
			// NETWORK_TYPE_CDMA    中国电信2G
			if (type == TelephonyManager.NETWORK_TYPE_EVDO_A
					|| type == TelephonyManager.NETWORK_TYPE_CDMA
					|| type == TelephonyManager.NETWORK_TYPE_1xRTT) {
				CdmaCellLocation location = (CdmaCellLocation) tm.getCellLocation();
				if (location != null){
					//[31188,575299,1675765,13824,2]
					int baseStationId = location.getBaseStationId();
					int a = location.getBaseStationLatitude();
					int b = location.getBaseStationLongitude();
					return baseStationId+"_"+a+"_"+b;
				}
			}else{//其他网络全部使用GsmCellLocation
				GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
				if (location != null){
					int cid = location.getCid();
					int lac = location.getLac();
					return cid+"_"+lac;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
        return "";
    }

    /**
     * 获取手机网络信息
     * @return 手机当前的网络状况
     */
    public static NetworkInfo getNetworkInfo() {
        NetworkInfo myNetworkInfo = new NetworkInfo();
        try {
            ConnectivityManager manager = (ConnectivityManager) ApplicationData.globalContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
                myNetworkInfo.setConnectToNetwork(true);
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    myNetworkInfo.setProxy(false);
                    myNetworkInfo.setProxyName(networkInfo.getTypeName());
                } else {
                    // 取得代理信息
                    String proxyHost = android.net.Proxy.getDefaultHost();
                    if (proxyHost != null) {
                        myNetworkInfo.setProxy(true);
                        myNetworkInfo.setProxyHost(proxyHost);
                        myNetworkInfo.setProxyPort(android.net.Proxy.getDefaultPort());
                    } else {
                        myNetworkInfo.setProxy(false);
                    }
                    myNetworkInfo.setProxyName(networkInfo.getExtraInfo());
                }
            } else {
                myNetworkInfo.setConnectToNetwork(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myNetworkInfo;
    }


    /**
     * 获取随机数
     * @return
     */
    public static String getRandomNumber(){
        return new DecimalFormat("0000000000").format(new Random().nextInt(1000000000));
    }
}
