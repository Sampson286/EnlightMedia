package android.enlight.com.common.view;

import android.content.Context;
import android.enlight.com.R;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
/**
 * 自定义滚动框
 *
 */
public class CustomProgressdialog extends CustomDialog {
	TextView textViewMessage;
	/**显示的信息*/
	private String message = null;
	/**是否取消*/
	private boolean cancelable = false;


	public CustomProgressdialog(Context context, String message, boolean cancelable, boolean isShow) {
		super(context, R.style.TANCStyle);
		this.message = message;
		this.cancelable = cancelable;
		if(isShow){
			show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCanceledOnTouchOutside(false);
		setCancelable(cancelable);
		setContentView(R.layout.dialog_progress_layout);
		textViewMessage= (TextView) this.findViewById(R.id.dialog_progress_layout_tv_message);
		if(null!= message||""!=message){
			textViewMessage.setText(message);
		}
	}
    public void setMessage(String message){
    	if(textViewMessage!=null){
    		textViewMessage.setText(message);
    	}
    } 

}
