package com.example.lee.loading_page_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.PM.Demo.UnityPlayerActivity;
import com.example.lee.loading_page_2.util.HttpUtil;

import org.json.simple.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button fa_button;
    Button ar_button;
    ImageButton favorite_button;
//    ImageButton ar_button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initHttp();
        ar_button = (Button)findViewById(R.id.imageButton1);
        ar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn = new Intent(MainActivity.this, UnityPlayerActivity.class);
                startActivity(btn);
                //startActivity(new Intent(MainActivity.this,SubActivity.class));
            }
        });

        favorite_button = (ImageButton)findViewById(R.id.favoriteButton);
        favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(btn);
            }
        });
    }
    private void initHttp(){
        Log.i("start init", "http 통신 시작");
        HttpUtil util = new HttpUtil();
        util.execute();
        Log.i("execute end", "execute end");
        try {
            JSONObject jsonObj = util.get();
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
}
