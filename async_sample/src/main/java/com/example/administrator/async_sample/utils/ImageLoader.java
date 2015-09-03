package com.example.administrator.async_sample.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.async_sample.R;
import com.example.administrator.async_sample.adapter.NewsAdapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2015/7/30.
 */
public class ImageLoader {

    private ImageView mImageView ;

    private String mUrl ;

    //创建LruCache
    private LruCache<String, Bitmap> mCache ;

    private ListView mListView ;

    private Set<NewsAsyncTask> mTask   ;

    public ImageLoader(ListView listView){
        mListView = listView ;
        mTask = new HashSet<>() ;
        //获取最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 4 ;
        mCache = new LruCache<String, Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存时调用
                return value.getByteCount();
            }
        } ;
    }

    //将bitmap资源添加到cache里
    public void addBitmapToCache(String url, Bitmap bitmap){
        //在增加之前前判断一下缓存中是否存在
        if(getBitmapFromCache(url) == null){
            mCache.put(url, bitmap) ;
        }
    }

    //根据url从cache中获取btimap资源
    public Bitmap getBitmapFromCache(String url){
        return mCache.get(url) ;
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    //利用多线程方式实现异步加载
    public void showImageByThread(ImageView imageView, final String url){

        mImageView = imageView ;
        mUrl = url ;
        //创建一个线程
        new Thread(){

            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromUrl(url) ;
                Message message = Message.obtain() ;
                message.obj = bitmap ;
                mHandler.sendMessage(message) ;
            }
        }.start();
    }

    //利用url得到bitmap格式的图片资源
    public Bitmap getBitmapFromUrl(String remoteUrl){

        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(remoteUrl) ;
            connection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(connection.getInputStream()) ;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream) ;
            //自行模拟网络不好的状况，每次加载睡眠500毫秒
            //Thread.sleep(500);
            return bitmap ;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 利用AsyncTask实现异步加载
    public void showImageViewByAsyncTask(ImageView imageView, String url){
        // 从缓存中取出图片
        Bitmap bitmap = getBitmapFromCache(url) ;

        // 判断当前缓存中是否存在此图片
        if(bitmap == null){
            imageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            imageView.setImageBitmap(bitmap) ;
        }
    }

    //从可见项的开始位置到结束位置加载图片
    public void loadImages(int start, int end){
        for (int i = start; i < end; i++) {
            String url = NewsAdapter.URLs[i] ;
            Log.i("url", url) ;
            //先从缓存中去加载图片
            Bitmap bitmap = getBitmapFromCache(url) ;
            if(bitmap == null){
                //如果缓存中不存在的话，那么去下载该图片
                NewsAsyncTask task = new NewsAsyncTask(url) ;
                task.execute(url) ;
                mTask.add(task) ;
            }else{
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    //取消当前正在加载图片的任务
    public void cancelAllTask(){
        if(mTask != null){
            for (NewsAsyncTask task : mTask){
                task.cancel(false) ;
            }
        }
    }
    // 实现news的AsyncTask类
    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>{

        //private ImageView mImageView ;

        private String mUrl ;

        public NewsAsyncTask(String url){
            //mImageView = imageView ;
            mUrl = url ;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            // 从网络获取图片
            Bitmap bitmap = getBitmapFromUrl(params[0]) ;
            if(bitmap != null){
                //获取成功，加入缓存
                addBitmapToCache(params[0], bitmap);
            }
            return getBitmapFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if (imageView != null && bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
            mTask.remove(this) ;
        }
    }
}
