package com.hyyy.swipetabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/8/26.
 */
public class SecondFragment extends android.support.v4.app.Fragment {

    public static final String ARG_SECTION_NUMBER = "fragment_number";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        Bundle args = getArguments();
        ((TextView)rootView.findViewById(R.id.txt_first)).setText(
                getString(R.string.fragment_text, args.getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}
