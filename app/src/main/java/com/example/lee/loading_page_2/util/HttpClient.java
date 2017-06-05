package com.example.lee.loading_page_2.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by jongchan on 2015-11-02.
 */
public class HttpClient {

    private static final String BASE_URL = "http://52.43.75.43:8080/RealEstateServer/Test?cmd=";  //집 : 192.168.0.84 // 52.78.184.207
//    private static final String BASE_URL = "http://192.168.28.84:8080/RealEstateServer/Test?cmd=";  //집 : 192.168.0.84 // 52.78.184.207

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


    }