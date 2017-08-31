package com.enlight.android.common.network.model;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.enlight.com.R;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.enlight.android.common.exception.NetworkConnectException;
import com.enlight.android.common.exception.NetworkForceCloseException;
import com.enlight.android.common.exception.NetworkNotException;
import com.enlight.android.common.exception.NetworkTimeoutException;
import com.enlight.android.common.exception.ParseException;
import com.enlight.android.common.exception.SdcardException;
import com.enlight.android.common.network.RequestConnection;
import com.enlight.android.common.network.listener.RequestListener;
import com.enlight.android.common.utils.Tools;
import com.enlight.android.common.view.CustomProgressdialog;
import com.enlight.android.common.view.CustomTextViewDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 请求任务类
 * 
 * @author liheng
 *
 * create at 2015-5-18
 */
public abstract class RequestTask {
	/**操作成功的返回码*/
	public static final int RequestCode_SUCCESS_OPERATION = 100;
	/**未知错误*/
	public static final int RequestCode_UNKNOWN_ERROR = 101;
	/**参数错误*/
	public static final int RequestCode_PARAM_ERROR = 102;
	/**xml解析模式*/
	private static final String PARSE_MODE_XML = "xml";
	/**json解析模式*/
	private static final String PARSE_MODE_JSON = "json";
	
	protected Activity activity;
	/** 是否显示dialog */
	private boolean isShowDialog = false;
	/** 是否可以取消dialog */
	private boolean isCancelDialog = true;
	/*
	 *  出现错误时是否关闭当前界面 
	 *  TODO: 是否需要这个，还是放在各个调用activity里面自己调用
	 *  */
	private boolean isFinishActivity = false;
	/** dialog的显示信息 */
	private String dialogMessage = "";
    /**联网底层类*/
	protected RequestConnection remoteRequester = new RequestConnection();
	/** 取消标记 */
	private boolean cancel = false;

	private final int requestPost = 0;
	private final int requestPostFile = requestPost+1;
	private final int requestGetFile = requestPostFile+1;
	private final int requestGet = requestGetFile+1;
    /**联网类型*/
	private int requestType = requestPost;

    /**url地址*/
	protected String url = "";
    /**网络请求的参数*/
	private Map<String,String> params;
	/**上传文件的参数*/
	private Map<String,File> files;
    /**下载文件的参数*/
	private DownloadParamModel downloadParamModel;

    /**回调函数*/
	protected RequestListener requestListener;

	/**联网返回的状态码*/
	protected ResponseInfo responseInfo = null;

//	/**
//	 * 初始化,不显示loading条
//	 * 
//	 * @param activity
//	 */
//	public RequestTask(Activity activity) {
//		this.activity = activity;
//	}
	
	/**
	 * 初始化
	 */
	protected RequestTask() {}

	/**
	 * 初始化
	 * @param activity
	 */
	protected RequestTask(Activity activity) {
		this.activity = activity;
	}

	/**
	 * TODO: delete
	 * 初始化,不显示loading条
	 * 
	 * @param activity
	 * @param isFinishActivity
	 *            是否结束activity
	 */
	public RequestTask(Activity activity, boolean isFinishActivity) {
		this.activity = activity;
		this.isFinishActivity = isFinishActivity;
	}

	/**
	 * TODO: delete
	 * 初始化，显示loading条
	 * 
	 * @param activity
	 * @param requestBean
	 *            请求bean
	 * @param dialogMessage
	 *            loading对话框显示内容
	 * @param isShowDialog
	 *            是否显示loading对话框
	 * @param isCancelDialog
	 *            是否可以取消
	 * @param isFinishActivity
	 *            出现错误时是否关闭当前界面
	 */
	public RequestTask(Activity activity, String dialogMessage,
					   boolean isShowDialog, boolean isCancelDialog,
					   boolean isFinishActivity) {
		this.activity = activity;
		// this.requestBean = requestBean;
		this.isShowDialog = isShowDialog;
		this.isCancelDialog = isCancelDialog;
		this.dialogMessage = dialogMessage;
		this.isFinishActivity = isFinishActivity;
	}

	/**
	 * TODO: delete
	 * 初始化，显示loading条
	 * 
	 * @param requestBean
	 * @param dialogMessage
	 *            loading对话框显示内容
	 * @param isShowDialog
	 *            是否显示loading对话框
	 * @param isCancelDialog
	 *            是否可以取消
	 * @param isFinishActivity
	 *            出现错误时是否关闭当前界面
	 */
	public RequestTask(boolean isCancelDialog, boolean isFinishActivity) {
//		this.requestBean = requestBean;
		this.isCancelDialog = isCancelDialog;
		this.isFinishActivity = isFinishActivity;
	}
	
	/**
	 * 开始联网，post
	 * @param dialogMessage 显示信息
	 * @param isShowDialog 是否显示dialog
	 * @param isCancelDialog 是否可以取消
	 * @param url 联网地址
	 */
	protected final void onStartTaskPost(String dialogMessage, boolean isShowDialog, boolean isCancelDialog, String url, Map<String,String> params){
		this.requestType = requestPost;
		this.params = params;
		onStartTask(dialogMessage, isShowDialog, isCancelDialog, url);
	}
	
	/**
	 * 开始联网，post
	 * @param dialogMessage 显示信息
	 * @param isShowDialog 是否显示dialog
	 * @param isCancelDialog 是否可以取消
	 * @param url 联网地址
	 */
	protected final void onStartTaskGet(String dialogMessage, boolean isShowDialog, boolean isCancelDialog, String url, Map<String,String> params){
		this.requestType = requestGet;
		this.params = params;
		onStartTask(dialogMessage, isShowDialog, isCancelDialog, url);
	}

	/**
	 * 开始联网,上传文件
	 * @param dialogMessage
	 * @param isShowDialog
	 * @param isCancelDialog
	 * @param url
	 * @param
	 */
	protected final void onStartTaskPostFile(String dialogMessage, boolean isShowDialog, boolean isCancelDialog, String url, Map<String,String> params, Map<String,File> files){
		this.requestType = requestPostFile;
		this.params = params;
		this.files = files;
		onStartTask(dialogMessage, isShowDialog, isCancelDialog, url);
	}

	/**
	 * 开始联网,下载文件
	 * @param dialogMessage
	 * @param isShowDialog
	 * @param isCancelDialog
	 * @param downloadParamModel
	 */
	protected final void onStartTaskGetFile(String dialogMessage, boolean isShowDialog, boolean isCancelDialog, String url, DownloadParamModel downloadParamModel){
		this.requestType = requestGetFile;
		this.downloadParamModel = downloadParamModel;
		onStartTask(dialogMessage, isShowDialog, isCancelDialog, url);
	}
	
	/**
	 * 开始联网
	 * @param dialogMessage 显示信息
	 * @param isShowDialog 是否显示dialog
	 * @param isCancelDialog 是否可以取消
	 * @param url 联网地址
	 */
	private void onStartTask(String dialogMessage, boolean isShowDialog, boolean isCancelDialog, String url){
		this.dialogMessage = dialogMessage;
		this.isShowDialog = isShowDialog;
		this.isCancelDialog = isCancelDialog;

		this.url = url;
		startAsyncTask();
	}

	/**
	 * 开始联网
	 */
	private void startAsyncTask() {
		// 设置联网dialog
		final CustomProgressdialog pd;
		if (isShowDialog && null!=activity) {
			pd = new CustomProgressdialog(activity, dialogMessage,
					isCancelDialog, true);
			pd.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel = true;
					remoteRequester.cancel();
					onCancelTask();
					if (isFinishActivity) {
						activity.finish();
					}
				}
			});
		} else {
			pd = null;
		}

		new AsyncTask<String, Integer, Object>() {
			private Exception myException = null;

			@Override
			protected void onPreExecute() {

			}

			@Override
			protected Object doInBackground(String... params) {
				try {
					return onRequestTask();
				} catch (Exception e) {
					myException = e;
				}
				return null;
			}

			protected void onPostExecute(Object result) {
				if (!cancel) {
					try {
						if (myException == null) {
							if(onHandleCodeTask()) {
								onFinishTask(result);
							}
						} else {
							onExceptionTask(myException);
						}
					} catch (Exception e) {
						onExceptionTask(e);
					} finally {
						if (pd != null && pd.isShowing()) {
							pd.dismiss();
						}
					}

				}
			};
		}.executeOnExecutor(Executors.newCachedThreadPool());

	}

	/** 子类可重写，联网完成，处理一些UI事件，严禁放入耗时操作，如联网，操作数据库，操作本地文件 */
	protected void onFinishTask(Object object)throws Exception {
		if(requestListener != null) {
			requestListener.callBack(object);
		}
	}
	
	/**子类可重写，主动取消*/
	protected void onCancelTask(){

	}

	
	/**
	 * 联网中
	 * @return
	 * @throws Exception
	 */
	protected Object onRequestTask() throws Exception {
		Object o = null;
		//根据不同的类型选择不同的处理方式
		switch (requestType) {
		case requestPost: 
			o = onParserTask(remoteRequester.doPost(url, params,true));
			break;
		case requestPostFile: 
			o = remoteRequester.doPostFile(url, params, files);
			break;
		case requestGetFile: 
			o = remoteRequester.doDownloadFile(url, downloadParamModel);
//			o = onParserTask(remoteRequester.doGet(url, params));
			break;
		case requestGet:
			o = onParserTask(remoteRequester.doGet(url, params,true));
			break;
		} 

		//联网解析完成，提供一个方法供子类使用，做一些数据库，本地存储等耗时操作
		onRequestFinishTask(o);

		return o;
	}

	/**
	 * 解析数据，如果解析的数据不止状态码，需要子类重写此方法
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	private Object onParserTask(String response) throws ParseException {
		String parseMode = Tools.getString(R.string.config_network_parse_mode);
		Object o = null;
		try {
			//解析状态码
			if(PARSE_MODE_JSON.equals(parseMode)) {
			    JSONObject post = (JSONObject) new JSONTokener(response).nextValue();
			    responseInfo = new ResponseInfo();
				String code = post.getString("code");
				if (code != null && code.length() > 0) {
					responseInfo.setStatus(Integer.parseInt(code));
				}
				responseInfo.setMessage(post.getString("msg"));
	            //解析状态码之后的信息，子类实现
				o = onParseOtherInfo(post);
			} else if (PARSE_MODE_XML.equals(parseMode)) {
				//TODO: 如果有xml需求的话再去实现这个逻辑
				o = onParseOtherInfoByXML(response);
			}
			return o;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}
	
	/** 子类实现，解析状态码后返回的其它信息 */
	protected Object onParseOtherInfo(JSONObject post)throws JSONException {
		return null;
	}
	
	/** 子类实现，解析状态码后返回的其它信息 XML格式 */
	protected Object onParseOtherInfoByXML(String post){
		return null;
	}
	
	/** 子类实现,联网完成，在异步线程中处理一些耗时事件，如操作数据库，操作sdcard */
	protected void onRequestFinishTask(Object object)throws Exception {
		
	}
	
	/**
	 * 子类实现特殊状态码处理需求 
	 * @return true 继续之后的操作，false，不进行之后的操作
	 * */
	protected boolean onHandleCodeError () {
		return false;
	}
	
	/**
	 * 统一处理状态码
	 * 
	 * @return true 继续之后的操作，false，不进行之后的操作
	 */ 
	protected boolean onHandleCodeTask() {
		if (responseInfo != null) {
			int code = responseInfo.getStatus();
			switch (code) {
			case RequestCode_SUCCESS_OPERATION:
				return true;
			case RequestCode_UNKNOWN_ERROR:
			case RequestCode_PARAM_ERROR:
				showRetryDialog(responseInfo.getMessage());
				// onHandleCodeError();
				return false;
			default:
				// 对于所有没有考虑的request code 都当做错误在子类中处理
				return onHandleCodeError();
			}
		}
		return true;
	}

	/**
	 * 统一的异常的处理方式，实现此方法，如果还需要基类处理统一错误，请添加super.handleException(e);
	 * 
	 * @param e
	 *            异步处理抛出的异常
	 */
	public void onExceptionTask(Exception e) {
		// TODO:这段需要重构一下
		if (e instanceof NetworkForceCloseException) {
			// 主动取消联网返回异常，不做任何处理
		} else if (e instanceof NetworkNotException) {
			showRetryDialog("无网络，请确认网络正常后重试！");
		} else if (e instanceof NetworkTimeoutException) {
			showRetryDialog("网络连接超时！");
		} else if (e instanceof NetworkConnectException) {
			showRetryDialog("网络连接错误！" + e.getMessage());
		} else if (e instanceof ParseException) {
			showRetryDialog("数据解析错误，加载失败，请重新加载。");
		} else if (e instanceof SdcardException) {
			SdcardException ex = (SdcardException) e;
			String message = "存储卡错误！";
			if (ex.getErrorCode() == SdcardException.SDCARD_ERROR) {
				message = Tools.getString(R.string.loading_sdcard_error);
			} else if (ex.getErrorCode() == SdcardException.SDCARD_FULL) {
				message = Tools.getString(R.string.loading_sdcard_full);
			}
			showRetryDialog(message);
		} else {
			showRetryDialog(Tools.getString(R.string.connect_failed));
		}
	}

	/**
	 * 无网络对话框
	 */
	private void showNoNetWorkDialog() {
		final CustomTextViewDialog dialog = new CustomTextViewDialog(activity);
		dialog.setTitle(R.string.connect_message);
		dialog.setMessage(R.string.no_network);
		dialog.setCertainButton(R.string.setting, new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
				//Tools.openSystemNetworkSetting(activity);
				if (isFinishActivity) {
					activity.finish();
				}
			}
		});
		dialog.setCancelButton(R.string.cancel, new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
				if (isFinishActivity) {
					activity.finish();
				}
			}
		});
		dialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.cancel();
					onCancelTask();
					if (isFinishActivity) {
						activity.finish();
					}

					return true;
				}
				return false;
			}
		});
	}

	/**
	 * 重试对话框
	 * 
	 * @param message
	 *            提示的内容
	 */
	protected void showRetryDialog(final String message) {
		// 弹框之前判断一下activity是否finish
		if(null==activity || activity.isFinishing()) {
			return;
		}
		final CustomTextViewDialog dialog = new CustomTextViewDialog(activity);
		dialog.setTitle(R.string.connect_message);
		dialog.setMessage(message);
		dialog.setCertainButton(R.string.retry, new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
				startAsyncTask();
			}
		});
		dialog.setCancelButton(R.string.cancel, new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
				// activity.getClass();
				onCancelTask();
				if (isFinishActivity) {
					activity.finish();
				}
			}
		});
		dialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
								 KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.cancel();
					onCancelTask();
					if (isFinishActivity) {
						activity.finish();
					}
					return true;
				}
				return false;
			}
		});
		dialog.show();
	}
	
	public void setRequestListener(RequestListener requestListener) {
		this.requestListener = requestListener;
	}

}
