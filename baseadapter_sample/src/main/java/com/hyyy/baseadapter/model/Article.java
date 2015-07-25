package com.hyyy.baseadapter.model;

/**
 * Project name: AndroidSamples
 * Author: hyyy
 * Date: 15/7/25 下午8:33
 * Description: Article model
 * **************************************************
 * Github: http://github.com/castial/android-samples
 * Blog: http://castial.github.io
 * **************************************************
 */
public class Article {

    private String title ;

    private String desc ;

    private String date ;

    public Article(String title, String desc, String date) {
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
