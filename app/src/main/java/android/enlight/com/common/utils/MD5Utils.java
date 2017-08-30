package android.enlight.com.common.utils;

import java.security.MessageDigest;

public class MD5Utils {
    /**
     * 请求播放信息sign   key
     */
    public static String key = "3d6a640a475e5ac1a5191ebea8ff441a";

    public static String encode(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] digest = md.digest();
            String text;
            for (int i = 0; i < digest.length; i++) {
                text = Integer.toHexString(0xFF & digest[i]);
                if (text.length() < 2) {
                    text = "0" + text;
                }
                hexString.append(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    public static String encode(String text) {
        return encode(text.getBytes());
    }

    /**
     * 支付宝加密   key
     *
     * @param text
     * @return
     */
    public static String encodexxx(String text) {
        text = text + "rtdcm6gryzby0s657f6ev22971j9n0ai";
        return encode(text.getBytes());
    }
}
