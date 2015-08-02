package com.example.administrator.async_sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.async_sample.R;
import com.example.administrator.async_sample.bean.News;
import com.example.administrator.async_sample.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/7/30.
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<News> newsList ;

    private LayoutInflater inflater ;

    private ImageLoader mImageLoader ;

    private int mStart ;

    private int mEnd ;

    public static String[] URLs ;

    public NewsAdapter(Context context, List<News> mDatas, ListView listView){
        newsList = mDatas ;
        inflater = LayoutInflater.from(context) ;
        mImageLoader = new ImageLoader(listView) ;

        URLs = new String[mDatas.size()] ;
        for (int i = 0; i < URLs.length; i++) {
            URLs[i] = mDatas.get(i).getIconUrl() ;
        }
        //一定要注册事件
        listView.setOnScrollListener(this);
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder ;
       if(convertView == null){
           viewHolder = new ViewHolder() ;
           convertView = inflater.inflate(R.layout.item_layout, null) ;
           viewHolder.iconView = (ImageView) convertView.findViewById(R.id.icon) ;
           viewHolder.titleView = (TextView) convertView.findViewById(R.id.title) ;
           viewHolder.contentView = (TextView) convertView.findViewById(R.id.content) ;
           convertView.setTag(viewHolder);
       }else {
           viewHolder = (ViewHolder) convertView.getTag();
       }
        //当网络状况不好时，ImageView默认显示的图片
        viewHolder.iconView.setImageResource(R.mipmap.ic_launcher);
        //为icon设置tag，以防止图片多次加载
        String url =  newsList.get(position).getIconUrl() ;
        viewHolder.iconView.setTag(url);

        /*
        //使用多线程方式加载图片
        new ImageLoader().showImageByThread(viewHolder.iconView, url);
        */

        //使用AsyncTask方式加载图片
        mImageLoader.showImageViewByAsyncTask(viewHolder.iconView, url);
        viewHolder.titleView.setText(newsList.get(position).getTitle());
        viewHolder.contentView.setText(newsList.get(position).getContent());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            //当滑动状态为停止时，加载可见项
            mImageLoader.loadImages(mStart, mEnd);
        }else{
            //当处在滑动过程中时，停止任务的下载
            mImageLoader.cancelAllTask();

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem ;
        mEnd = mStart + visibleItemCount ;


    }

    class ViewHolder{
        ImageView iconView ;
        TextView titleView ;
        TextView contentView ;
    }
}
