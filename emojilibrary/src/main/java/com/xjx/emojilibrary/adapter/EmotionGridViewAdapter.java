package com.xjx.emojilibrary.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 */
public class EmotionGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> emotionNames;
    private int itemWidth;

    public EmotionGridViewAdapter(Context context, List<String> emotionNames, int itemWidth) {
        this.context = context;
        this.emotionNames = emotionNames;
        this.itemWidth = itemWidth;
    }

    @Override
    public int getCount() {
        // +1 最后一个为删除按钮
        return emotionNames.size() + 1;
    }

    @Override
    public String getItem(int position) {
        return emotionNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        // 设置内边距
//        textView.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8);
        textView.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(itemWidth, itemWidth);
        textView.setLayoutParams(params);
        //判断是否为最后一个item
        if (position == getCount() - 1) {
            textView.setText("...");
        } else {
            String emotionName = emotionNames.get(position);
            textView.setText(emotionName);
        }
        textView.setTextSize(19);
        return textView;
    }

}
