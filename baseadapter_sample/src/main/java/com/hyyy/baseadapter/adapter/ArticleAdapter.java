package com.hyyy.baseadapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyyy.baseadapter.R;
import com.hyyy.baseadapter.model.Article;

import java.util.List;

/**
 * Project name: AndroidSamples
 * Author: hyyy
 * Date: 15/7/25 下午8:35
 * Description: Article Adapter
 * **************************************************
 * Github: http://github.com/castial/android-samples
 * Blog: http://castial.github.io
 * **************************************************
 */
public class ArticleAdapter extends BaseAdapter{

    private List<Article> articles ;

    private LayoutInflater mInflater ;

    public ArticleAdapter(Context context, List<Article> datas){
        mInflater = LayoutInflater.from(context) ;
        articles = datas ;
    }
    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null ;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item, parent, false) ;
            viewHolder = new ViewHolder() ;

            viewHolder.title = (TextView) convertView.findViewById(R.id.item_title);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.item_desc) ;
            viewHolder.date =  (TextView) convertView.findViewById(R.id.item_date) ;

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Article article = articles.get(position) ;
        viewHolder.title.setText(article.getTitle());
        viewHolder.desc.setText(article.getDesc());
        viewHolder.date.setText(article.getDate());

        return convertView;
    }

    private class ViewHolder{
        TextView title ;
        TextView desc ;
        TextView date ;
    }
}
