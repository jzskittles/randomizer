package com.randomizerapp.randomizer;

/**
 * Created by jenny on 1/25/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

public class FolderAdapter extends BaseDynamicGridAdapter {
    List<Item> items;

    public FolderAdapter(Context context, List<Item> apps, int columnCount) {
        super(context, apps, columnCount);
        items=apps;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200,200);

            holder = new ViewHolder(convertView,position);
            holder.icon.setLayoutParams(layoutParams);
            holder.letterText.setTextSize(15);

            convertView.setTag(holder);
        }

        return convertView;
    }

    public class ViewHolder {
        private ImageView icon;
        private TextView letterText;
        private int pos;

        private ViewHolder(View view, int position) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(145,145);

            letterText = (TextView) view.findViewById(R.id.text);
            icon = (ImageView)view.findViewById(R.id.icon);
            icon.setLayoutParams(layoutParams);
            icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            icon.setImageDrawable(items.get(position).icon);
            letterText.setText(items.get(position).name);
            pos = items.get(position).position;
        }

        public int getAppPos(){
            return pos;
        }
        public String getAppName(){
            return letterText.getText().toString();
        }

        public void setAppPos(int newpos){
            pos=newpos;
        }
        public void setAppName(String newname){
            letterText.setText(newname);
        }

    }

}
