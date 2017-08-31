package com.enlight.android.request;

import android.app.Activity;
import com.enlight.android.common.exception.ParseException;
import com.enlight.android.common.network.RequestBase;
import com.enlight.android.common.network.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyc on 2017/8/17.
 */

public class NormalRequest extends RequestBase implements RequestCallback{
    public void getTest(Activity context){
        //String url= Tools.getString(R.string.address)+"commpackage_info.js";
        String url="http://dev.ewang.com/Ewang/movie/movielist?movietype=4";
        onStartTaskGet(context,this,url,null,false);
    }
    @Override
    public Object parserData(JSONObject jsonObject) throws JSONException {
        String s=jsonObject.getString("status");
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
            String newResponse=response.substring(32);
            return parserData(new JSONObject(newResponse));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
