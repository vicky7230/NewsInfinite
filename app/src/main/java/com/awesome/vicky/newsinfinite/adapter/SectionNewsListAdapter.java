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
import com.awesome.vicky.newsinfinite.pojoSections.Result;

import java.util.List;

/**
 * Created by vicky on 4/1/16.
 */
public class SectionNewsListAdapter extends BaseAdapter {

    Context context;
    List<Result> resultList;
    String fontPath;
    Typeface typeface;

    public SectionNewsListAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
        fontPath = "fonts/NimbusRomNo9L-Reg.otf";
        typeface = Typeface.createFromAsset(this.context.getAssets(), fontPath);
    }

    private class ViewHolder {
        TextView sectionName;
        TextView webTitle;
        TextView publishedAt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.serach_list_item, null);
            holder = new ViewHolder();
            holder.sectionName = (TextView) convertView.findViewById(R.id.section_name);
            holder.webTitle = (TextView) convertView.findViewById(R.id.web_title);
            holder.publishedAt = (TextView) convertView.findViewById(R.id.published_at);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.sectionName.setTypeface(typeface);
        holder.sectionName.setText(resultList.get(position).getSectionName());
        holder.webTitle.setTypeface(typeface);
        holder.webTitle.setText(resultList.get(position).getWebTitle());
        holder.publishedAt.setTypeface(typeface);
        holder.publishedAt.setText(resultList.get(position).getWebPublicationDate().replace("T", ", ").replace("Z", ""));
        return convertView;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

