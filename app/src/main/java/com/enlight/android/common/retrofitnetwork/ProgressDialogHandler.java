package com.enlight.android.common.retrofitnetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by zyc on 2017/8/30.
 * ProgressDialog使用的通信
 */

public class ProgressDialogHandler extends Handler {
    //显示对话框
    public static final int SHOW_PROGRESS_DIALOG = 1;
    //销毁对话框
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    //对话框
    private ProgressDialog pd;
    //当前的环境变量
    private Context context;
    //dialog是否可以取消
    private boolean cancelable;
    //取消监听
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(boolean cancelable, Context context, ProgressCancelListener mProgressCancelListener) {
        this.cancelable = cancelable;
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
    }

    /**
     * 初始化对话框
     */
    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancelable);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    /**
     * 销毁对话框
     */
    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
