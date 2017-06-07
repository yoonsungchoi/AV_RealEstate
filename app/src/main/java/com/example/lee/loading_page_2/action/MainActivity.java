package com.example.lee.loading_page_2.action;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.PM.Demo.UnityPlayerActivity;
import com.example.lee.loading_page_2.R;
import com.example.lee.loading_page_2.util.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    Button fa_button;
    Button ar_button;
    Button favorite_button;
//    public String user_id;
    org.json.JSONObject jsonObj;

    List<JSONObject> house_jsonArr;
    Intent btn;
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
//                Intent btn = new Intent(MainActivity.this, UnityPlayerActivity.class);
//                btn.putExtra("user_id",user_id);
//                startActivity(btn);

                Intent [] btn = new Intent[2];
                btn[0] = new Intent(MainActivity.this, UnityPlayerActivity.class); //넘기는 Intent

                btn[1] = getIntent(); //받아오는 Intent
                String main_user_id = btn[1].getExtras().getString("user_id"); //login 에서 값 가져옴

                btn[0].putExtra("main_pass_user_id",main_user_id);
                Log.i("Main_page******","user_id : " + main_user_id);
                startActivity(btn[0]);
            }
        });

        //즐겨찾기
        favorite_button = (Button)findViewById(R.id.favoriteButton);
        favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = new Intent(MainActivity.this, FavoriteActivity.class);
//                btn = new Intent(MainActivity.this, TestActivity.class);  // Favorite -> VR test
                btn.putExtra("user_id",user_id);
                startActivity(btn);
            }
        });
    }

    /* Testing Async Task */
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
