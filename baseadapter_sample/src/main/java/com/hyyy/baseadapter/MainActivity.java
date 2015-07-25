package com.hyyy.baseadapter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.hyyy.baseadapter.adapter.ArticleAdapter;
import com.hyyy.baseadapter.model.Article;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ArticleAdapter mAdapter ;

    private ListView listView ;

    private List<Article> articleList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDate() ;

        initView()  ;
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list_id) ;
        listView.setAdapter(mAdapter);
    }

    private void initDate() {

        articleList = new ArrayList<Article>() ;

        Article science = new Article("Science", "您好，这是第一条item，题目是Science！", "2015-07-23") ;
        articleList.add(science) ;
        Article music = new Article("Music", "您好，这是第二条item，题目是Music！", "2015-07-23") ;
        articleList.add(music) ;
        Article sport = new Article("Sport", "您好，这是第三条item，题目是Sport！", "2015-07-23") ;
        articleList.add(sport) ;
        Article food = new Article("Food", "您好，这是第四条item，题目是Food！", "2015-07-23") ;
        articleList.add(food) ;
        Article book = new Article("Book", "您好，这是第五条item，题目是Book！", "2015-07-23") ;
        articleList.add(book) ;
        Article game = new Article("Game", "您好，这是第六条item，题目是Game！", "2015-07-23") ;
        articleList.add(game) ;
        Article travel = new Article("Travel", "您好，这是第七条item，题目是Travel！", "2015-07-23") ;
        articleList.add(travel) ;

        mAdapter = new ArticleAdapter(MainActivity.this, articleList) ;
    }
}
