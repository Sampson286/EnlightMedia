package android.enlight.com.common.network;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.enlight.com.R;
import android.enlight.com.common.exception.NetworkConnectException;
import android.enlight.com.common.exception.NetworkForceCloseException;
import android.enlight.com.common.exception.NetworkNotException;
import android.enlight.com.common.exception.NetworkTimeoutException;
import android.enlight.com.common.exception.ParseException;
import android.enlight.com.common.exception.SdcardException;
import android.enlight.com.common.view.CustomProgressdialog;
import android.enlight.com.common.view.CustomTextViewDialog;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 联网基类，其他联网类都继承此类
 * @author zyc
 *
 */
public abstract class RequestBase {

	public final String apiUser = "/api";

	public static final int RequestCode_DATAULT = 0;
	/**操作成功的返回码*/
	public static final int RequestCode_SUCCESS_OPERATION = 100;
	/**未知错误*/
	public static final int RequestCode_UNKNOWN_ERROR = 101;
	/**参数错误*/
	public static final int RequestCode_PARAM_ERROR = 102;
	/**登录失败*/
	public static final int RequestCode_LOGIN_FAILED = 104;
	/**账号已在其他设备登录*/
	public static final int RequestCode_LOGIN_OUT = 105;
	/**有新版本更新*/
	public static final int RequestCode_VERSION_UPDATE = 106;
	/**当前用户登录状态失效*/
	public static final int RequestCode_SESSION_INVALID=121;
	/**当前视频已经下架*/
	public static final int RequestCode_VIDEO_UNSHELVE=126;
    /**联网底层类*/
	private RequestConnection remoteRequester = new RequestConnection();

	/**对话框显示的context*/
	private Activity context = null;
	
	/**dialog的显示信息*/
	protected String dialogMessage = "正在联网，请稍后...";
	/**是否可以取消dialog*/
	protected boolean isCancelDialog = true;

	/**取消标记*/
	private boolean cancel = false;
    /**联网类型*/
	private final int requestGet = 0;
	private final int requestPost = requestGet+1;
	private final int requestPostFile = requestPost+1;
	private final int requestGetFile = requestPostFile+1;
    /**联网类型*/
	private int requestType = requestPost;

    /**url地址*/
	protected String url = "";
    /**上传的参数*/
	private Map<String,String> params;
	/**上传文件的参数*/
	private Map<String,File> files;
	/**下载文件路径*/
	private String filePath = "";
	/**下载文件当前长度*/
	private long currentLength = 0;
	/**下载文件分段长度*/
	private long targetLength = 0;
	/**是否需要请求头*/
	private boolean ifHead=true;
    /**回调函数*/
	private RequestCallback requestCallback;

	/**联网返回的状态码*/
	protected int responseCode = RequestCode_DATAULT;
	/**联网返回的状态信息*/
	protected String responseMessage = "";
	
	/**是否是下载文件*/
	private boolean isDownloadFile = false;
	
	/**
	 * 获取Context
	 * @return
	 */
	protected Context getContext(){
		return context;
	}
	
	/**
	 * 开始联网，Get
	 * @param context 不为空则显示联网对话框
	 * @param requestCallback 子类实现的接口
	 * @param url 联网地址
	 * @param params 联网参数
	 */
	protected final void onStartTaskGet(Activity context, RequestCallback requestCallback, String url, Map<String,String> params,boolean ifHead){
		this.context = context;
		this.requestCallback = requestCallback;
		this.requestType = requestGet;
		this.url = url;
		this.params = params;
		this.ifHead=ifHead;
		startAsyncTask();
	}

	/**
	 * 开始联网，post
	 * @param context 不为空则显示联网对话框
	 * @param requestCallback 子类实现的接口
	 * @param url 联网地址
	 * @param params 联网参数
	 */
	protected final void onStartTaskPost(Activity context, RequestCallback requestCallback, String url, Map<String,String> params){
		this.context = context;
		this.requestCallback = requestCallback;
		this.requestType = requestPost;
		this.url = url;
		this.params = params;
		startAsyncTask();
	}

	/**
	 * 开始联网,上传文件
	 * @param context 不为空则显示联网对话框
	 * @param requestCallback 子类实现的接口
	 * @param url 联网地址
	 * @param params 联网参数
	 * @param files 文件参数
	 */
	protected final void onStartTaskPostFile(Activity context, RequestCallback requestCallback, String url, Map<String,String> params, Map<String,File> files){
		this.context = context;
		this.requestCallback = requestCallback;
		this.requestType = requestPostFile;
		this.url = url;
		this.params = params;
		this.files = files;
		startAsyncTask();
	}

	/**
	 * 异步下载文件
	 * @param context 不为空则显示联网对话框
	 * @param requestCallback 子类实现的接口
	 * @param url 联网地址
	 * @param filePath 联网参数
	 */
	protected final void onStartAsyncTaskGetFile(Activity context, RequestCallback requestCallback, String url, String filePath){
		this.isDownloadFile = true;
		this.context = context;
		this.requestCallback = requestCallback;
		this.requestType = requestGetFile;
		this.url = url;
		this.filePath = filePath;
		startAsyncTask();
	}

	/**
	 * 异步请求文件
	 * @param url
	 * @param filePath
	 * @param requestCallback
     * @return
     */
	protected final boolean onStartSyncTaskGetFile(String url, String filePath, RequestCallback requestCallback){
		try {
			remoteRequester.doDownloadFile(url,filePath,currentLength,targetLength,requestCallback);
			requestCallback.requestSuccess(true);
            return true;
		} catch (Exception e) {
			e.printStackTrace();
            return false;
		}
	}

	/**
	 * 取消联网请求
	 */
	protected void cancleRequest(){
		cancel = true;
		remoteRequester.cancel();
		onCancelTask();
	}
	
	/**
	 * 开始异步联网
	 */
	protected void startAsyncTask() {
		//设置联网dialog
		final CustomProgressdialog pd;
		if(hasContext()){
			pd = new CustomProgressdialog(context, dialogMessage,isCancelDialog,true);
			pd.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancleRequest();
				}
			});
		}else{
			pd = null;
		}

		//开启异步联网
		new AsyncTask<String,Integer,Object>() {
			private Exception myException = null;

			@Override
			protected Object doInBackground(String... params) {
				try {
					Object o = onRequestTask();
					//假如不是下载，解析返回的数据
					if(!isDownloadFile){
						o = onParserTask((String)o);
					}
					//联网解析完成，提供一个方法供子类使用，做一些数据库，本地存储等耗时操作
					if(requestCallback != null){
						requestCallback.requestParserFinishedOnAysncTask(o);
					}
                    return o;
				} catch (Exception e) {
					myException = e;
				}
				return null;
			}

			protected void onPostExecute(Object object) {
				if(!cancel){
					try {
						if (myException==null) {
							if(isDownloadFile || onHandleCodeTask()){
								if(pd != null && pd.isShowing() && hasContext()){
									pd.dismiss();
								}
								onFinishTask(object);
							}
						}else{
							onFailedTask(myException);
						}
					} catch (Exception e) {
						onFailedTask(e);
					} finally{
						if(pd != null && pd.isShowing() && hasContext()){
							pd.dismiss();
						}
					}
				}
			};
		}.executeOnExecutor(Executors.newCachedThreadPool());

	}

	/**
	 * 联网中
	 * @return
	 * @throws Exception
	 */
	private Object onRequestTask() throws Exception {
		Object o = null;
		//根据不同的类型选择不同的处理方式
		switch (requestType) {
		case requestGet:
			o = remoteRequester.doGet(url,params,ifHead);
			break;
		case requestPost:
			o = remoteRequester.doPost(url,params,ifHead);
			break;
		case requestPostFile: 
			o = remoteRequester.doPostFile(url, params, files);
			break;
		case requestGetFile: 
			o = remoteRequester.doDownloadFile(url,filePath,currentLength,targetLength,requestCallback);
			break;
		}
		return o;
	}
	
	/**
	 * 解析数据，如果解析的数据不止状态码，需要子类重写此方法
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	protected Object onParserTask(String response) throws ParseException {
		try{
			//解析状态码
			JSONObject jsonObject = new JSONObject(response);
            
			String code = jsonObject.getString("code");
			if (code != null && code.length() > 0) {
				responseCode = Integer.parseInt(code);
			}
			responseMessage = jsonObject.getString("msg");
			
            //解析状态码之后的信息，子类实现
			if(this.requestCallback != null){
				return this.requestCallback.parserData(jsonObject);
			}
			return null;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	/**
	 * 统一处理状态码
	 * @return true 继续之后的操作，false，不进行之后的操作
	 */
	private boolean onHandleCodeTask(){
		//子类处理
		if(this.requestCallback != null){
			boolean result = this.requestCallback.handleCode(this.responseCode,this.responseMessage);
			if(result){
				return false;
			}
		}
		//子类没有处理，父类处理
        switch(responseCode){
        case RequestCode_SUCCESS_OPERATION:
        	return true;
        case RequestCode_LOGIN_FAILED:
        case RequestCode_UNKNOWN_ERROR:
        case RequestCode_PARAM_ERROR:
        case RequestCode_DATAULT:
        	showRetryDialog(responseMessage);
        	return false;
        }
		return true;
	}

	/** 联网完成，处理一些UI事件，严禁放入耗时操作，如联网，操作数据库，操作本地文件 */
	private void onFinishTask(Object o)throws Exception {
		if(requestCallback != null){
			requestCallback.requestSuccess(o);
		}
	}
	
	/** 取消联网 */
	private void onCancelTask(){
		if(requestCallback != null){
			requestCallback.requestCancel();
		}
	}

	/**
	 * 子类可重写，统一的异常的处理方式，实现此方法，
	 * 如果还需要基类处理统一错误，请添加super.handleException(e);
	 * @param e
	 */
	private void onFailedTask(Exception e) {
		//子类处理
		if(requestCallback != null){
			boolean result = requestCallback.requestFailed(e);
			if(result){
				return;
			}
		}
		//子类没有处理，父类处理
	    if (e instanceof NetworkForceCloseException) {
		    //主动取消联网返回异常，不做任何处理
	    }else if (e instanceof NetworkNotException) {
			showRetryDialog("无网络，请确认网络正常后重试！");
	    } else if (e instanceof NetworkTimeoutException) {
			showRetryDialog("网络连接超时！");
		} else if (e instanceof NetworkConnectException) {
			showRetryDialog("网络连接错误！"+e.getMessage());
		} else if (e instanceof ParseException) {
			showRetryDialog("数据解析错误！");
		} else if (e instanceof SdcardException) {
			SdcardException ex = (SdcardException) e;
			String message;
			if (ex.getErrorCode() == SdcardException.SDCARD_ERROR) {
				message = "存储卡错误！";
			} else if (ex.getErrorCode() == SdcardException.SDCARD_FULL) {
				message = "存储卡已满！";
			}else {
				message = "存储卡错误！";
			}
			showRetryDialog(message);
		}  else {
			showRetryDialog("网络连接失败！");
		}
	}

	/**
	 * 重试对话框
	 * @param message 文本内容
	 */
	private final void showRetryDialog(final String message) {
		if(hasContext()){
			final CustomTextViewDialog dialog = new CustomTextViewDialog(context);
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
					onCancelTask();
				}
			});
			dialog.setOnKeyListener(new OnKeyListener() {
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						dialog.cancel();
						onCancelTask();
						return true;
					}
					return false;
				}
			});
			dialog.show();
		}
	}
	
	private boolean hasContext() {
		return context != null && !context.isFinishing();
	}

}


