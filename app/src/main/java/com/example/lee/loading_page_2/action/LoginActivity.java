package com.example.lee.loading_page_2.action;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lee.loading_page_2.R;
import com.example.lee.loading_page_2.util.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    static int flag = 1;
    EditText id;
    EditText password;
    Button loginButton;
    String enpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText) findViewById(R.id.login_id_edittext);
        password = (EditText) findViewById(R.id.login_pw_edittext);

        // 로그인 버튼 클릭 시
        loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.put("id", id.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                HttpClient.post("test2", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String login = response.getString("login");
                            if (login.equals("success")) {

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_id", id.getText().toString());
                                Log.i("Login_page*****","user_id : " + id);
                                startActivity(intent); // 다음 화면으로 넘어간다.
                            } else {
                                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                                //로그인 실패 다이얼로그
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                        super.onFailure(statusCode, headers, throwable, response);

                    }
                });
            }
        });
        // 회원가입 버튼 클릭 시
        TextView signupButton = (TextView) findViewById(R.id.login_link_signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(
                        LoginActivity.this, SignupActivity.class);
                startActivity(intent);

                //회원가입 버튼 클릭 시
            }
        });
    }
}
