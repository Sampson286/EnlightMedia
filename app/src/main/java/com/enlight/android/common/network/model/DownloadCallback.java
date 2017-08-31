package com.enlight.android.common.network.model;

/**
 * 文件下载回调
 * @author zyc
 *
 */
public interface DownloadCallback {
	/**下载进度*/
	void onDownloadLenght(long totalLength, long lenght);
}
