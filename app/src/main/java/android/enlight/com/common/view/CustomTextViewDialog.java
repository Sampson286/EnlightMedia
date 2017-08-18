package android.enlight.com.common.view;

import android.content.Context;
import android.enlight.com.R;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 *  自定义纯文本显示对话框
 * @author zyc
 *
 */
public class CustomTextViewDialog extends CustomDialog {

	private TextView textview_content;
	private Button certainButton;
	private Button cancelButton;
	private TextView textvie_title;
	public CheckBox checkBox;
	private boolean isShowChexBox=false;
	public CustomTextViewDialog(Context context) {
		super(context, R.style.TANCStyle);
		show();
	}
	public CustomTextViewDialog(Context context, boolean isShowChexBox){
		super(context,R.style.TANCStyle);
		this.isShowChexBox=isShowChexBox;
		show();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_textview_layout);
		checkBox=(CheckBox)findViewById(R.id.checkbox);
		if(isShowChexBox){
			checkBox.setVisibility(View.VISIBLE);
		}
		textview_content = (TextView) this.findViewById(R.id.dialog_textview_layout_tv_content);
		textview_content.setMovementMethod(ScrollingMovementMethod.getInstance());
		certainButton = (Button) this.findViewById(R.id.dialog_textview_layout_btn_1);
		cancelButton = (Button) this.findViewById(R.id.dialog_textview_layout_btn_2);
		textvie_title=(TextView)findViewById(R.id.dialog_textview_layout_tv_title);
	}

	public void setTitleView(int visibility) {
		findViewById(R.id.custem_dialog_title_layout).setVisibility(visibility);
	}

	public TextView getContentTextview() {
		return textview_content;
	}
	/**
	 * 设置title
	 * @param title
	 */
    public void setTitle(String title){
    	if(textvie_title!=null){
    		textvie_title.setText(title);
    	}
    }

	/**
	 * 设置需要显示的文字
	 * @param message 需要的文字
     */
	public void setMessage(String message){
		if(textview_content!=null){
			textview_content.setText(message);
		}
	}

	/**
	 * 设置显示内容
	 * @param messageId 内容ID
     */
	public void setMessage(int messageId){
		if(textview_content!=null){
			textview_content.setText(messageId);
		}
	}
	/**
	 * 设置确认Button 是否显示
	 * @param flag
     */
	public void showCertainButton(boolean flag){
		if(flag){
			if(certainButton!=null){
				certainButton.setVisibility(View.VISIBLE);
			}
		}else{
			if(certainButton!=null){
				certainButton.setVisibility(View.GONE);
			}
		}
	}

	public void showButtonVisable(){
		certainButton.setVisibility(View.INVISIBLE);
		cancelButton.setVisibility(View.INVISIBLE);
	}
	/**
	 * 设置确定按键
	 */
	public void setCertainButton(int id,View.OnClickListener onClickListener){
		if(certainButton!=null){
			certainButton.setText(id);
			certainButton.setOnClickListener(onClickListener);
		}
	}

	/**
	 * 设置确定按键
	 */
	public void setCertainButton(String id, View.OnClickListener onClickListener){
		if(certainButton!=null){
			certainButton.setText(id);
			certainButton.setOnClickListener(onClickListener);
		}
	}

	/**
	 *设置取消按键
	 */
	public void setCancelButton(int id,View.OnClickListener onClickListener){
		if(cancelButton!=null){
			cancelButton.setText(id);
			cancelButton.setOnClickListener(onClickListener);
		}
	}

	/**
	 *设置取消按键
	 */
	public void setCancelButton(String id, View.OnClickListener onClickListener){
		if(cancelButton!=null){
			cancelButton.setText(id);
			cancelButton.setOnClickListener(onClickListener);
		}
	}


}
