package com.awesome.vicky.newsinfinite.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.awesome.vicky.newsinfinite.R;
import com.awesome.vicky.newsinfinite.pojo.Result;
import com.awesome.vicky.newsinfinite.pojo.Section;

import java.util.List;

/**
 * Created by vicky on 4/1/16.
 */
public class SectionListAdapter extends BaseAdapter {
    Context context;
    List<Section> sectionList;
    String fontPath;
    Typeface typeface;

    public SectionListAdapter(Context context, List<Section> sectionList) {
        this.context = context;
        this.sectionList = sectionList;
        fontPath = "fonts/NimbusRomNo9L-Reg.otf";
        typeface = Typeface.createFromAsset(this.context.getAssets(), fontPath);
    }

    private class ViewHolder {
        TextView sectionName;
        TextView apiUrl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.section_list_item, null);
            holder = new ViewHolder();
            holder.sectionName = (TextView) convertView.findViewById(R.id.section_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.sectionName.setTypeface(typeface);
        holder.sectionName.setText(sectionList.get(position).getSectionName());
        return convertView;
    }

    @Override
    public int getCount() {
        return sectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return sectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
