package android.enlight.com.request;

import android.app.Activity;
import android.enlight.com.R;
import android.enlight.com.common.exception.ParseException;
import android.enlight.com.common.network.RequestBase;
import android.enlight.com.common.network.RequestCallback;
import android.enlight.com.common.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyc on 2017/8/17.
 */

public class NormalRequest extends RequestBase implements RequestCallback{
    public void getTest(Activity context){
        String url= Tools.getString(R.string.address)+"commpackage_info.js";
        onStartTaskGet(context,this,url,null,false);
    }
    @Override
    public Object parserData(JSONObject jsonObject) throws JSONException {
        return null;
    }

    @Override
    public void requestParserFinishedOnAysncTask(Object o) {

    }

    @Override
    public boolean handleCode(int responseCode, String responseMessage) {
        return true;
    }

    @Override
    public void requestCancel() {

    }

    @Override
    public boolean requestFailed(Exception e) {
        return false;
    }

    @Override
    public void requestSuccess(Object o) {

    }

    @Override
    public void uploadProgress(long totalSize, long currentSize) {

    }

    @Override
    protected Object onParserTask(String response) throws ParseException {
        try {
            return parserData(new JSONObject(response));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
