package android.enlight.com.common.retrofitnetwork;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by zyc on 2017/8/30.
 * progress的订阅，继承Subscriber用于抽取开始、完成、错误的处理。具体的成功交给具体的实现
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    //使用到handler
    private ProgressDialogHandler mProgressDialogHandler;
    //当前环境变量
    private Context context;
    //是否显示
    private boolean isShow;
    public ProgressSubscriber(Context context,boolean isShow) {
        this.context = context;
        this.isShow=isShow;
        mProgressDialogHandler = new ProgressDialogHandler(false, this.context, this);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null&&isShow) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null&&isShow) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {

        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        /*if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            Toast.makeText(ApplicationData.globalContext, "出现错误" + apiException.getMsg() + apiException.getCode(), Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
