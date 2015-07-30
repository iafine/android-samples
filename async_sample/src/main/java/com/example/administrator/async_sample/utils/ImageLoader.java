package com.example.administrator.async_sample.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/7/30.
 */
public class ImageLoader {

    private ImageView mImageView ;

    private String mUrl ;

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
            Thread.sleep(500);
            return bitmap ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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

    //利用AsyncTask实现异步加载
    public void showImageViewByAsyncTask(ImageView imageView, String url){
        new NewsAsyncTask(imageView, url).execute(url) ;
    }
    //实现news的AsyncTask类
    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>{

        private ImageView mImageView ;

        private String mUrl ;

        public NewsAsyncTask(ImageView imageView, String url){
            mImageView = imageView ;
            mUrl = url ;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap(bitmap);
            }
        }
    }
}
