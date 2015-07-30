package com.example.administrator.async_sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.async_sample.R;
import com.example.administrator.async_sample.bean.News;
import com.example.administrator.async_sample.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/7/30.
 */
public class NewsAdapter extends BaseAdapter {

    private List<News> newsList ;

    private LayoutInflater inflater ;

    public NewsAdapter(Context context, List<News> mDatas){
        newsList = mDatas ;
        inflater = LayoutInflater.from(context) ;
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
        new ImageLoader().showImageViewByAsyncTask(viewHolder.iconView, url);
        viewHolder.titleView.setText(newsList.get(position).getTitle());
        viewHolder.contentView.setText(newsList.get(position).getContent());
        return convertView;
    }

    class ViewHolder{
        ImageView iconView ;
        TextView titleView ;
        TextView contentView ;
    }
}
