package com.example.administrator.async_sample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.async_sample.adapter.NewsAdapter;
import com.example.administrator.async_sample.bean.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listView ;

    private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list) ;
        new NewsAsyncTask().execute(URL);
    }

    //异步加载类
    class NewsAsyncTask extends AsyncTask<String, Void, List<News>>{

        @Override
        protected List<News> doInBackground(String... params) {

            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<News> newses) {
            super.onPostExecute(newses);
            NewsAdapter adapter = new NewsAdapter(MainActivity.this, newses) ;
            listView.setAdapter(adapter);
        }
    }

    //得到Json数据
    private List<News> getJsonData(String url) {
        List<News> newsList = new ArrayList<News>() ;
        try {
            /*
            此句的功能与url.openConnection().getInputStream()相同，可以根据URL直接联网获取网络数据，简单粗暴
            返回值类型为InputStream
            */

            //得到json数据
            String jsonString = readStream(new URL(url).openStream()) ;
            //进行JSON解析数据操作
            JSONObject jsonObject = new JSONObject(jsonString) ;
            JSONArray jsonArray = jsonObject.getJSONArray("data") ;
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                News news = new News();
                news.setIconUrl(json.getString("picSmall"));
                news.setTitle(json.getString("name"));
                news.setContent(json.getString("description"));
                newsList.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList ;
    }

    //读取字节流数据,并返回字符数据
    private String readStream(InputStream is){
        InputStreamReader isr ;
        String result = "" ;

        try {
            String line = "" ;
            isr = new InputStreamReader(is, "UTF-8") ;
            BufferedReader br = new BufferedReader(isr) ;
            while ((line = br.readLine()) != null){
                result += line ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result ;
    }
}
