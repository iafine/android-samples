package com.hyyy.wechat.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Project name: AndroidSamples
 * Author: hyyy
 * Date: 8/22/15 12:32 PM
 * Description:
 * **************************************************
 * Github: http://github.com/castial/android-samples
 * Blog: http://castial.github.io
 * **************************************************
 */
public class TabFragment extends Fragment{

    public static final String TITLE = "title";

    private String mTitle = "title";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null){
            mTitle = getArguments().getString(TITLE);
        }
        TextView textView = new TextView(getActivity());
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
        textView.setText(mTitle);

        return textView;
    }
}
