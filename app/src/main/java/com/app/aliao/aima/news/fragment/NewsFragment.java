package com.app.aliao.aima.news.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by 丽双 on 2015/3/9.
 */
public class NewsFragment extends Fragment{

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;
    private RequestQueue mRequestQueue;
    private WebView mWebView;
    String mWikiSearchURL = "http://zh.wikipedia.org/w/api.php?action=opensearch&search=Android";
    String mWikiSearchURL2 = "http://eh.wikipedia.org/w/api.php?action=query&prop=revision&rvprop=content&titles=Android&form=json";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pull_refresh_listview, null);
        initViews(view);
//        requestDatas();
//        requestDataUseHttpClient();
//        requestDataUseURLConnection();
        return view;
    }

    private void requestDataUseURLConnection() {
        try {
            URL url = new URL("");
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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

        L.d("initViews thread id = "+Thread.currentThread().getId());

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);

        mListView = (ListView) view.findViewById(R.id.listview_common);

        //webview相关设置
        mWebView = (WebView) view.findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
/*        mWebView.loadUrl("file:///android_asset/index.html");   //HTML文件存放在assets文件夹中
//        添加一个对象, 让JS可以访问该对象的方法, 该对象中也可以调用JS中的方法
        mWebView.addJavascriptInterface(new Contact(), "contact");*/

        mWebView.loadUrl("file:///android_asset/showtoast.html");   //HTML文件存放在assets文件夹中
        mWebView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");

        //GET http://www.oschina.net/action/api/news_detail?id=60382
        initWebViews(mWebView);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initWebViews(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(15);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 11) {
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
    }


    public interface OnWebViewImageListener {

        /**
         * 点击webview上的图片，传入该缩略图的大图Url
         * @param bigImageUrl
         */
        void onImageClick(String bigImageUrl);

    }

    private final class Contact{
        //JavaScript调用此方法拨打电话
        public void call(String phone){
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone)));
        }
        //HTML调用此方法传递数据
        public void showcontacts(){
            String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
            // 调用JS中的方法
            mWebView.loadUrl("javascript:show('" + json + "')");
            L.d("javascript:show thread id = "+Thread.currentThread().getId());
        }
    }

    public class WebAppInterface{
        //绑定到你的Javascript的对象在另一个线程中运行，而不是在创建它的线程中运行。
        Context mContext;

        WebAppInterface(Context c){
            mContext = c;
        }

        public void showToast(String toast){
            Toast.makeText(mContext,toast,Toast.LENGTH_LONG).show();

            L.d("javascript:showToast thread id = "+Thread.currentThread().getId());
        }
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
