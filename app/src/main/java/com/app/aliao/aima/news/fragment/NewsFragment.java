package com.app.aliao.aima.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.aliao.aima.R;
import com.app.aliao.aima.utils.L;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by 丽双 on 2015/3/9.
 */
public class NewsFragment extends Fragment{

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;
    private RequestQueue mRequestQueue;
    String mWikiSearchURL = "http://zh.wikipedia.org/w/api.php?action=opensearch&search=Android";
    String mWikiSearchURL2 = "http://eh.wikipedia.org/w/api.php?action=query&prop=revision&rvprop=content&titles=Android&form=json";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pull_refresh_listview, null);
        initViews(view);
        requestDatas();
        requestDataUseHttpClient();
        requestDataUseURLConnection();
        return view;
    }

    private void requestDataUseURLConnection() {

    }



    private void requestDataUseHttpClient() {

        new Thread(){
            @Override
            public void run() {
                super.run();
                //1.创建一个httpclient实例
                HttpClient client = new DefaultHttpClient();
                //2.创建某种连接方式的实例，在这里是GetMethod。在GetMethod的构造函数中传入连接的地址
                HttpGet httpGet = new HttpGet(mWikiSearchURL);

                //添加请求报头
                httpGet.addHeader("Accept-Encoding", "gzip");
                //3.使用httpclient实例的execute执行method实例
                try {
                    HttpResponse httpResponse = client.execute(httpGet);
                    //4.读response
                    //HTTP响应报文-状态行
                    L.d("http", "http 状态行status line Http协议版本 = "+httpResponse.getStatusLine().getProtocolVersion());
                    L.d("http", "http 状态行status line 状态码 = "+httpResponse.getStatusLine().getStatusCode());
                    L.d("http", "http 状态行status line 状态码的文本描述 = "+httpResponse.getStatusLine().getReasonPhrase());
                    //HTTP响应报文-响应报头/消息报头
                    L.d("http", "http 地点 = "+httpResponse.getLocale());//单个添加，也可以整体以header来添加
                    //HTTP响应报文-响应正文
                    HttpEntity httpEntity = httpResponse.getEntity();
                    L.d("http", "http 响应正文 = "+httpEntity.toString());
                    L.d("http", "http 响应正文getContentEncoding = "+httpEntity.getContentEncoding());
                    L.d("http", "http 响应正文getContentLength = "+httpEntity.getContentLength());
                    L.d("http", "http 响应正文getContentType = "+httpEntity.getContentType());//application/json; charset=utf-8用于定义网络文件的类型和网页的编码，决定浏览器将以什么形式，什么编码读取这个文件
//                    InputStream inputStream = httpEntity.getContent();

                    InputStream inputStream = httpEntity.getContent();
                    inputStream = new GZIPInputStream(inputStream);
                    //将stream转换问string
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String str = "";
                    StringBuilder bufferStr = new StringBuilder();
                    while ((str = bufferedReader.readLine()) != null){
                        bufferStr.append(str);
                    }

                    //5 释放连接。无论执行方法是否成功，都必须释放连接.
                    if (null != inputStream)
                        inputStream.close();
                    //6.对得到后的内容进行处理。
                    L.d("http", "http 响应正文内容 = "+bufferStr.toString());
                    try {
                       /* JSONObject jsonObject = new JSONObject(bufferStr.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("Android");
                        for (int i = 0; i<jsonArray.length(); i++){
                            L.d("http", i+" = "+jsonArray.getString(i));
                        }
*/
                        JSONArray array = new JSONArray(bufferStr.toString());
                        for (int i = 0; i<array.length(); i++){
                            L.d("http", i+" = "+array.getString(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    private void initViews(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);

        mListView = (ListView) view.findViewById(R.id.listview_common);


    }

    private void requestDatas() {

        mRequestQueue = Volley.newRequestQueue(getActivity());
        String xmlUrl = String.format("http://www.oschina.net/%s", "action/api/news_list?catalog=1&pageIndex=1&pagesize=10&dataType=json");
        String jsonUrl = String.format("http://open.iciba.com/dsapi");
        StringRequest stringRequest = new StringRequest(xmlUrl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.d("json response = " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.d("json errormsg = "+error.getMessage());
            }
        });

        //http://www.oschina.net/action/api/news_list?catalog=1&pageIndex=1&pagesize=10&dataType=json
        //http://www.oschina.net/action/api/news_list&catalog=1&pageIndex=0&pageSize=20&dataType=json
        //每日一读
     /*   String url = String.format("http://www.oschina.net/%s", "action/api/news_list?catalog=1&pageIndex=1&pagesize=10&dataType=json");
        JsonObjectRequest objectRequest = new JsonObjectRequest("http://open.iciba.com/dsapi",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                L.d("json response = " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.d("json errormsg = "+error.getMessage());
            }
        });
       */
        mRequestQueue.add(stringRequest);




    }
}
