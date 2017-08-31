package com.enlight.android.common.network.model;

import com.enlight.android.common.network.listener.RequestListener;

/**
 * 下载文件联网类
 * @author zyc
 *
 */
public class DownloadFileRequest extends RequestTask {
    /**下载文件的参数*/
	private DownloadParamModel downloadParamModel;

	/**
	 * 下载文件请求
	 * @param url
	 * @param filePath
	 * @param fileName
	 * @param requestListener
     */
	public DownloadFileRequest(String url, String filePath, String fileName, RequestListener requestListener) {
		this.requestListener = requestListener;
		downloadParamModel = new DownloadParamModel();
		downloadParamModel.setFilePath(filePath);
		downloadParamModel.setFileName(fileName);
		downloadParamModel.setTargetLength(0);
		onStartTaskGetFile("", false, false, url,downloadParamModel);
	}

	/**
	 * 文件下载请求
	 * @param url 请求地址
	 * @param filePath 本地存放地址
	 * @param fileName 文件名
	 * @param downloadCallback 下载回调
     */
	public DownloadFileRequest(String url, String filePath, String fileName, DownloadCallback downloadCallback) {
		downloadParamModel = new DownloadParamModel();
		downloadParamModel.setFilePath(filePath);
		downloadParamModel.setFileName(fileName);
		downloadParamModel.setTargetLength(0);
		downloadParamModel.setDownloadCallback(downloadCallback);
		onStartTaskGetFile("", false, false, url,downloadParamModel);
	}
	
	public DownloadFileRequest(String url, String filePath, String fileName) {
		downloadParamModel = new DownloadParamModel();
		downloadParamModel.setFilePath(filePath);
		downloadParamModel.setFileName(fileName);
		downloadParamModel.setTargetLength(0);
		onStartTaskGetFile("", false, false, url,downloadParamModel);
	}

	@Override
	public void onFinishTask(Object object) throws Exception {
		if(requestListener != null){
			requestListener.callBack(object);
		}
	}

	@Override
	public void onExceptionTask(Exception e) {

	}
}
