package com.awesome.vicky.newsinfinite.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awesome.vicky.newsinfinite.R;

public class About extends Fragment {

    public About() {
    }

    private String fontPath;
    private Typeface typeface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fontPath = "fonts/NimbusRomNo9L-Reg.otf";
        typeface = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        ((TextView) getView().findViewById(R.id.news_label)).setTypeface(typeface);
        ((TextView) getView().findViewById(R.id.infinte_label)).setTypeface(typeface);
    }
}
