package com.example.lee.loading_page_2.action;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.PM.Demo.UnityPlayerActivity;
import com.example.lee.loading_page_2.R;
import com.example.lee.loading_page_2.util.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    Button fa_button;
    Button ar_button;
    ImageButton favorite_button;
    org.json.JSONObject jsonObj;
    List<JSONObject> house_jsonArr;
    //    ImageButton ar_button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final String user_id = intent.getStringExtra("user_id");

        //AR모드
        ar_button = (Button)findViewById(R.id.imageButton1);
        ar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn = new Intent(MainActivity.this, UnityPlayerActivity.class);
                startActivity(btn);
                //startActivity(new Intent(MainActivity.this,SubActivity.class));
            }
        });

        //즐겨찾기
        favorite_button = (ImageButton)findViewById(R.id.favoriteButton);
        favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn = new Intent(MainActivity.this, FavoriteActivity.class);
//                initHttp(user_id);
                RequestParams params = new RequestParams();
                params.put("id", user_id);
                HttpClient.post("getFavorite", params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            jsonObj = response;
                            JSONArray jsonArr = (JSONArray) response.get("house_id");

                            //사용자의 즐겨찾기 등록한 house_id 값 arrlist 저장.
                            for(int i=0; i<jsonArr.length(); i++){
//                        house_id_arr.add(jsonArr.getString(i));

                                //house_id를 이용해 부동산 정보 가져옴
                                RequestParams params = new RequestParams();
                                params.put("house_id", jsonArr.getString(i));
                                HttpClient.post("getInfoID", params, new JsonHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        try {
                                            jsonObj = response;
                                            house_jsonArr.add(jsonObj);
                                            Log.d("houseData", "[" +
                                                    jsonObj.get("house_div") + ", " +
                                                    jsonObj.get("house_item") + ", " +
                                                    jsonObj.get("house_addr") + ", " +
                                                    jsonObj.get("price") + ", " +
                                                    jsonObj.get("floor") + ", " + "]");
                                        } catch(Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                Log.d("house 정보 with house_id", "END");
                                Log.d("house_jsonArr : ", jsonArr+"");
//                        Log.i("house_id #"+(i+1)+": ", jsonArr.getString(i));
                            }
                            Log.d("house_id : ", jsonObj.get("house_id").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                btn.putExtra("house_jsonArr", house_jsonArr.toString());
                startActivity(btn);
            }
        });
    }
    private void initHttp(String user_id){
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
                    jsonObj = response;
                    Log.i("houseData", "[" + jsonObj.get("house_id") + ", " +
                            jsonObj.get("house_div") + ", " +
                            jsonObj.get("house_item") + ", " +
                            jsonObj.get("house_addr") + ", " +
                            jsonObj.get("agency") + ", " +
                            jsonObj.get("price") + ", " +
                            jsonObj.get("floor") + ", " +
                            jsonObj.get("item_x") + ", " +
                            jsonObj.get("item_y") + "]");
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        Log.i("execute end", "execute end");
    }
}
