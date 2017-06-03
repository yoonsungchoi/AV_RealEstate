package com.example.lee.loading_page_2.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.lee.loading_page_2.ListViewBtnAdapter;
import com.example.lee.loading_page_2.ListViewBtnItem;
import com.example.lee.loading_page_2.R;
import com.example.lee.loading_page_2.util.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FavoriteActivity extends Activity implements ListViewBtnAdapter.ListBtnClickListener{

    org.json.JSONObject jsonObj;
    List<JSONObject> house_jsonArr = new ArrayList<>();
//    org.json.JSONArray house_jsonArr;
    List<String> house_id_arr;
    //해당 제거 버튼 누른 경우
    @Override
    public void onListBtnClick(int position) {
        Log.d("event", "click on 제거 버튼");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        house_jsonArr = new ArrayList<>();
        Intent intent = getIntent();
        final String user_id = intent.getStringExtra("user_id");

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
        ListView listview ;
        ListViewBtnAdapter adapter;
        ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>() ;

        // items 로드.
        loadItemsFromDB(house_jsonArr, items) ;

        // Adapter 생성
        adapter = new ListViewBtnAdapter(this, R.layout.listview_btn_item, items, this) ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // TODO : item click
                Log.d("event", "Click on listview아이템");
            }
        }) ;

    }   // end of onCreate

    public boolean loadItemsFromDB(List<JSONObject> house_arr, ArrayList<ListViewBtnItem> list) {
//    public boolean loadItemsFromDB(ArrayList<ListViewBtnItem> list) {
        ListViewBtnItem item;
//        int i;
        if (list == null) {
            list = new ArrayList<>();
        }
        //순서를 위한 i값을 1초기화
//        i = 1;

        //item생성
        for(int i=0; i<house_arr.size(); i++){
            item = new ListViewBtnItem() ;
            try {
                JSONObject houseObj = house_arr.get(i);
                item.setTextStr(houseObj.get("house_div").toString()+" "+
                        houseObj.get("house_item").toString()+" "+
                        houseObj.get("house_div").toString()+" "+
                        houseObj.get("house_addr").toString()+" "+
                        houseObj.get("floor").toString()+" "+
                        houseObj.get("price"));
                list.add(item) ;
            } catch (JSONException e){
                e.printStackTrace();
            }
//            item.setTextStr(Integer.toString(i) + "번 아이템입니다."); ;
//            list.add(item) ;
        }
        /*
        item = new ListViewBtnItem() ;
        item.setTextStr();
        item.setTextStr(Integer.toString(i) + "번 아이템입니다."); ;
        list.add(item) ;
        i++ ;

        item = new ListViewBtnItem() ;
        item.setTextStr(Integer.toString(i) + "번 아이템입니다."); ;
        list.add(item) ;
        i++ ;

        item = new ListViewBtnItem() ;
        item.setTextStr(Integer.toString(i) + "번 아이템입니다."); ;
        list.add(item) ;
        i++ ;

        item = new ListViewBtnItem() ;
        item.setTextStr(Integer.toString(i) + "번 아이템입니다."); ;
        list.add(item) ;*/

        return true;
    }


}   // end of class
