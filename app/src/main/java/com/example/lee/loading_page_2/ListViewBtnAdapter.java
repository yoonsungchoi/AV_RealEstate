package com.example.lee.loading_page_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.lee.loading_page_2.util.HttpClient;
import com.example.lee.loading_page_2.util.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by younsung on 2017-06-01.
 */

public class ListViewBtnAdapter extends ArrayAdapter implements View.OnClickListener {

    //생성자로부터 전달된 resource id 값을 저장
    int resourceId;
    //생성자로부터 전달된 ListBtnClickListener 저장
    private ListBtnClickListener listBtnClickListener;
    ArrayList<ListViewBtnItem> list;
    //버튼 클릭 이벤트를 위한 listener 인터페스 정의
    public interface ListBtnClickListener {
        void onListBtnClick(int position);
    }

    //사용 안됨..
    @Override
    public void onClick(View v) {
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        Log.d("onClick event", "ListViewBtnAdapter onClick 이벤트 발생");
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag()) ;
        }
    }

    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    public ListViewBtnAdapter(Context context, int resource, ArrayList<ListViewBtnItem> list, ListBtnClickListener clickListener) {
        super(context, resource, list) ;

        this.list = list;
        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;

        this.listBtnClickListener = clickListener ;
    }

    public ListBtnClickListener getListBtnClickListener() {
        return listBtnClickListener;
    }

    public void setListBtnClickListener(ListBtnClickListener listBtnClickListener) {
        this.listBtnClickListener = listBtnClickListener;
    }

    //새롭게 만든 layout을 위한 View를 생성하는 코드
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 layout을 inflate해 convertView 참조 획득
       if(convertView == null) {
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.listview_btn_item, parent, false);
       }

       final TextView textTextView = (TextView) convertView.findViewById(R.id.textView);
       //Data set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewBtnItem listViewItem = (ListViewBtnItem) getItem(position);

        //아이템 내 각 위젯에 데이터 반영
        textTextView.setText(listViewItem.getTextStr());

        //button클릭 시 TextView(textView)의 내용변경
        Button button1 = (Button) convertView.findViewById(R.id.removeButton);
        button1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                String data = ((ListViewBtnItem) getItem(position)).getTextStr();
                String house_id = ((ListViewBtnItem) getItem(position)).getHouse_id();
                String user_id = ((ListViewBtnItem) getItem(position)).getUser_id();
                Log.d("remve data in db", data);
                Log.d("Adapter ##house / user", house_id+", "+user_id);

                //listView에서 삭제
                list.remove(position); //or some other task
                notifyDataSetChanged();

                //DB에서 삭제
                RequestParams params = new RequestParams();
                params.put("house_id", house_id);
                params.put("user_id", user_id);
                HttpClient.post("removeMapData", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                    }
                });

//                Log.d("버튼1 event", "ListViewBtnAdapter 이벤트 발생");
//                textTextView.setText(Integer.toString(pos + 1) +"번 버튼 선택됨.");
            }
        });
        return convertView;
    }

}