package com.example.lee.loading_page_2.action;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class SignupActivity extends AppCompatActivity {
    private EditText id;
    private EditText email;
    private EditText passwd;
    private EditText passConfText;
    private EditText name;
    private TextView textView;
    private Button signupbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        id = (EditText) findViewById(R.id.input_id);
        email = (EditText) findViewById(R.id.input_email);
        passwd = (EditText) findViewById(R.id.input_passwd);
        name = (EditText) findViewById(R.id.input_name);

        passConfText = (EditText) findViewById(R.id.confirm_passwd);
        textView = (TextView) findViewById(R.id.TextVIew_PwdProblem);
        textView.setVisibility(View.GONE);
        signupbtn = (Button) findViewById(R.id.new_member_button);
        //intent

        passConfText.addTextChangedListener(passwordWatcher);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String pass1 = passwd.getText().toString();
                    RequestParams params = new RequestParams();
                    params.put("id", id.getText().toString().trim());
                    params.put("email", email.getText().toString().trim());
                    params.put("password", pass1);
                    params.put("name", name.getText().toString().trim());
                    HttpClient.post("test3", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                            try {
                                if (responseBody.getString("signup").equals("success")) {
                                    Intent intent;
                                    intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignupActivity.this,"회원가입이 실패하였습니다",Toast.LENGTH_LONG).show();
                                    //아이디 중복 다이얼로그
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Toast.makeText(SignupActivity.this,"회원가입이 실패하였습니다",Toast.LENGTH_LONG).show();
                        }
                    });
            }

        });
    }



    private final TextWatcher passwordWatcher  =  new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textView.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged (Editable s){
            if(passwd.getText().toString().equals(passConfText.getText().toString())){
                textView.setText("Password correct");
            } else textView.setText("Password Do not Matched");
            // textView.setVisibility(View.GONE);
        }
    };
}
