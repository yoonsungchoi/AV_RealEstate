package com.example.lee.loading_page_2.action;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lee.loading_page_2.ListViewBtnAdapter;
import com.example.lee.loading_page_2.ListViewBtnItem;
import com.example.lee.loading_page_2.R;
import com.example.lee.loading_page_2.util.HttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FavoriteActivity extends Activity implements ListViewBtnAdapter.ListBtnClickListener{

    org.json.JSONObject jsonObj;
    List<JSONObject> house_jsonList = new ArrayList<>();

    @Override
    public void onListBtnClick(int position) {
        Log.d("event", "favoriteActivity/ onListBtnClock 이벤트 발생");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_listview);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_actionbar);

        house_jsonList = new ArrayList<>();

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
                    final JSONArray jsonArr = (JSONArray) response.get("house_id");
                    Log.d("house_jsonArr : ", jsonArr+"");
                    //사용자의 즐겨찾기 등록한 house_id 값 arrlist 저장.
                    for(int i=0; i<jsonArr.length(); i++){
//                        house_id_arr.add(jsonArr.getString(i));

                        //house_id를 이용해 부동산 정보 가져옴
                        RequestParams params = new RequestParams();
                        params.put("house_id", jsonArr.getString(i));
                        Log.d("처리할 house_id : ", jsonObj.get("house_id").toString());
                        HttpClient.post("getInfoID", params, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                try {
                                    jsonObj = response;
                                    house_jsonList.add(jsonObj);
                                    Log.d("houseData", "[" +
                                            jsonObj.get("house_div") + ", " +
                                            jsonObj.get("house_item") + ", " +
                                            jsonObj.get("house_name") + ", " +
                                            jsonObj.get("house_addr") + ", " +
                                            jsonObj.get("price") + ", " +
                                            jsonObj.get("floor") + ", " + "]");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (house_jsonList.size() == jsonArr.length()) {
                                    final ListView listview;
                                    final ListViewBtnAdapter adapter;
                                    final ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>();
                                    // items 로드
                                    loadItemsFromDB(house_jsonList, items, user_id);

                                    // Adapter 생성
                                    adapter = new ListViewBtnAdapter(getApplicationContext(), R.layout.listview_btn_item, items, FavoriteActivity.this);

                                    // 리스트뷰 참조 및 Adapter달기
                                    listview = (ListView) findViewById(R.id.listview);
                                    listview.setAdapter(adapter);

                                    // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의
                                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView parent, View v, int position, long id) {
                                            // TODO : item click
//                                            adapter.notifyDataSetChanged();
                                            Toast.makeText(FavoriteActivity.this ,"클릭됨" ,Toast.LENGTH_LONG).show();
                                            String house_id = ((ListViewBtnItem) adapter.getItem(position)).getHouse_id();
                                            RequestParams params = new RequestParams();
                                            params.put("house_id", house_id);
                                            HttpClient.post("getVRfromFV", params, new JsonHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                                                    try {
                                                        jsonObj = response;
                                                        Log.d("## VR Path = ", jsonObj.get("path").toString());
                                                        String path = jsonObj.get("path").toString();
                                                        String url = "http://52.43.75.43:8080/VRDIR/"+path;
                                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                                        startActivity(intent);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    Log.d("2번째 onSuccess", "END");
                                }
                            }
                        }); //end of onSuccess (house 상세정보)

                    } //end of for statement(유저의 등록한 여러개 house id, 개별 처리)
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("1번째 onSuccess", "end");
            }//end of onSuccess
        }); //end of httpResponseHandle

        /*ListView listview ;
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
        }) ;*/

    }   // end of onCreate

    public boolean loadItemsFromDB(List<JSONObject> house_arr, ArrayList<ListViewBtnItem> list, String user_id) {
        ListViewBtnItem item;
        if (list == null) {
            list = new ArrayList<>();
        }
        //item생성
        for(int i=0; i<house_arr.size(); i++){
            item = new ListViewBtnItem() ;
            try {
                JSONObject houseObj = house_arr.get(i);
                Log.d("loadItem ##house_id",houseObj.get("house_id").toString());
                item.setHouse_id(houseObj.get("house_id").toString());
                item.setUser_id(user_id);
                item.setTextStr(" 구  분 : " + houseObj.get("house_div").toString()+"\n "+
                        "종  류 : " + houseObj.get("house_item").toString()+"\n "+
                        "매물명 : " + houseObj.get("house_name").toString()+"\n "+
                        "소재지 : " + houseObj.get("house_addr").toString()+"\n "+
                        "층  수 : " + houseObj.get("floor").toString()+"층\n "+
                        "가  격 : " + houseObj.get("price")+"만원\n");
                list.add(item) ;
            } catch (JSONException e){
                e.printStackTrace();
            }
//            item.setTextStr(Integer.toString(i) + "번 아이템입니다."); ;
//            list.add(item) ;
        }
        return true;
    }
}   // end of class
