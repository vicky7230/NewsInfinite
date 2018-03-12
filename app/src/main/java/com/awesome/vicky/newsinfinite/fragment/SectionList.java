package com.awesome.vicky.newsinfinite.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.awesome.vicky.newsinfinite.R;
import com.awesome.vicky.newsinfinite.activity.SectionNewsListActivity;
import com.awesome.vicky.newsinfinite.adapter.SectionListAdapter;
import com.awesome.vicky.newsinfinite.pojo.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SectionList extends Fragment {


    public SectionList() {
        // Required empty public constructor
    }

    private ListView sectionListView;
    private SectionListAdapter sectionListAdapter;
    private List<Section> sectionList = new ArrayList<Section>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeSectionList();
        sectionListView = (ListView) getView().findViewById(R.id.section_list_view);
        sectionListAdapter = new SectionListAdapter(getActivity().getApplicationContext(), sectionList);
        sectionListView.setAdapter(sectionListAdapter);
        sectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SectionNewsListActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    private void initializeSectionList() {
        sectionList.add(new Section("Technology"));
        sectionList.add(new Section("Sports"));
        sectionList.add(new Section("Politics"));
        sectionList.add(new Section("Travel"));
        sectionList.add(new Section("World News"));
        sectionList.add(new Section("Business"));
        sectionList.add(new Section("Education"));
        sectionList.add(new Section("Film"));
        sectionList.add(new Section("Football"));
    }
}
