package android.enlight.com;

import android.enlight.com.common.view.BaseActivity;
import android.enlight.com.request.NormalRequest;

public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initialized() {
        NormalRequest normalRequest=new NormalRequest();
        normalRequest.getTest(this);
    }
}
