package com.example.lee.loading_page_2.action;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lee.loading_page_2.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends Activity {
    String [] data = { "앤트맨","함정","오피스","셀프/리스","베테랑","스트레이트 아웃 오브 컴턴",
            "뷰티 인사이드","치외법권","사도","미쓰 와이프","메이즈 러너: 스코치 트라이얼",
            "암살","제 7기사단","라이즈 오브 더 레전드: 황비홍","피케이: 별에서 온 얼간이",
            "성실한 나라의 앨리스","앙: 단팥 인생 이야기","우먼 인 블랙: 죽음의 천사",
            "러브 라이브! 더 스쿨 아이돌 무비","젊은 엄마 3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(), // 현재 화면의 제어권자
                R.layout.myrow,  //한행마다 보여줄 레이아웃을 지정
                data); // 다량의 데이터

        ListView lv = (ListView)findViewById(R.id.list);
        lv.setAdapter(adapter);  // 리스트 뷰에 adapter 를 등록한다

        /*setTitle("즐겨찾기");
        setContentView(R.layout.activity_favorite);
        final EditText et = (EditText) findViewById(R.id.editText1);
        Button bSave = (Button) findViewById(R.id.button1);
        Button bLoad = (Button) findViewById(R.id.button2);

        final LinearLayout dynamicLayout = (LinearLayout) findViewById(R.id.dynamicArea);

        //file 저장
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override    // 입력한 데이터를 파일에 추가로 저장하기
            public void onClick(View v) {
                String data = et.getText().toString();

                try {
                    FileOutputStream fos = openFileOutput
                            ("favoriteList.txt", // 파일명 지정
                                    Context.MODE_APPEND);// 저장모드
                    File temp = new File("favoriteList.txt");
                    PrintWriter out = new PrintWriter(fos);
                    out.println(data);
                    out.close();

//                    tv.setText("파일 저장 완료");
//                    pathView01.setText(temp.getAbsolutePath());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //file 로딩
        bLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 파일의 내용을 읽어서 TextView 에 보여주기
                try {
                    // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
                    StringBuffer data = new StringBuffer();
                    FileInputStream fis = openFileInput("favoriteList.txt");//파일명
                    BufferedReader buffer = new BufferedReader
                            (new InputStreamReader(fis));

                    List<String> dataList = new ArrayList<String>();

                    String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                    while (str != null) {
                        dataList.add(str); //전체 리스트
                        data.append(str + "\n");

                        str = buffer.readLine();
                    }

                    //개별 필요한 정보 파싱.
                    String[][] houseData = new String[dataList.size()][];
                    for(int i=0; i<dataList.size(); i++){
                        houseData[i] = dataList.get(i).split(" ");
                    }

                    int cnt = 0;
                    StringBuffer strBuff = new StringBuffer();
                    while(true) {
                        for(int i=0; i<houseData[cnt].length-2; i++) {
                            Log.i("houseData", houseData[cnt][i]);
                            strBuff.append(houseData[cnt][i] + " ");
//                            tv.setText(houseData[cnt][i]);
                        }
                        makeButton(strBuff, dynamicLayout);
                        strBuff.setLength(0);
//                        strBuff.append("\n");
                        if(cnt==dataList.size()-1)
                            break;
                        cnt++;
                    }
//                    tv.setText(strBuff);
                    buffer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
    }   // end of onCreate

    //동적 버튼 생성
    private void makeButton(StringBuffer favoriteData, LinearLayout dynamicLayout) {
        Button dynamicButton = new Button(this);
        dynamicButton.setText(favoriteData);

        dynamicLayout.addView(dynamicButton, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}   // end of class
