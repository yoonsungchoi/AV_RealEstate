package com.example.lee.loading_page_2.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.lee.loading_page_2.action.LoginActivity;

/**
 * Created by Lee on 2017. 5. 17..
 */

public class SplashActivity extends Activity {
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
