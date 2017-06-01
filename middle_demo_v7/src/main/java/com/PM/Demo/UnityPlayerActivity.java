package com.PM.Demo;

import com.PM.Demo.util.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unity3d.player.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.json.*;
import org.json.simple.*;
import org.json.simple.JSONObject;

import com.PM.Demo.util.HttpUtil;

import cz.msebera.android.httpclient.Header;

public class UnityPlayerActivity extends Activity
{
    String strData;
    String strAgencyNum;

    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    // Setup activity layout
    @Override protected void onCreate (Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        initHttp();

        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }

    //부동산 정보 get을 위한 통신
    private void initHttp(){
        Log.i("start init", "http 통신 시작");
//        HttpUtil util = new HttpUtil();
//        util.execute();
        RequestParams params = new RequestParams();
        params.put("item_x", 123.123);
        params.put("item_y", 123.123);
        HttpClient.post("test", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    org.json.JSONObject jsonObj = response;
                    //tmp = jsonObj.get("house_id")+" "+jsonObJSONObject jsonObj = util.get();
                    strData ="아파트 이름 : " + jsonObj.get("house_item") + "\n" +
                            "주     소 : " + jsonObj.get("house_addr") + "\n" +
                            "   층    :" + jsonObj.get("floor") + "\n"  +
                            "가     격 :" + jsonObj.get("price") + "\n"  +
                            "매매 / 전세 / 월 : " + jsonObj.get("house_div");

                    strAgencyNum = "" + jsonObj.get("agency") + " : " + jsonObj.get("agency_phoneNum");

                    Log.i("houseData", "[" + jsonObj.get("house_id") + ", " +
                            jsonObj.get("house_div") + ", " +
                            jsonObj.get("house_item") + ", " +
                            jsonObj.get("house_addr") + ", " +
                            jsonObj.get("agency") + ", " +
                            jsonObj.get("price") + ", " +
                            jsonObj.get("floor") + ", " +
                            jsonObj.get("item_x") + ", " +
                            jsonObj.get("item_y") + "]");
                    //strData = jsonObj.toJSONString();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override protected void onNewIntent(Intent intent)
    {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL)
        {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }

    public void CallByUnity(){

        //Plugins plugin = new Plugins();
        //strMsg_2 = plugin.CallByUnity_A();

        UnityPlayer.UnitySendMessage("EstateInfo", "GetMsgFromAndroid", strData);
    }

    public void CallByUnity_Agency(){
        UnityPlayer.UnitySendMessage("Agency_num","GetAgencyCallNum", strAgencyNum);
    }
}
