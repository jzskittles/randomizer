package com.randomizerapp.randomizer;

/**
 * Created by jenny on 1/25/2018.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridViewAdapter extends BaseDynamicGridAdapter {
    //Context context;
    List<Item> items;
    LayoutInflater inflter = null;

    public GridViewAdapter(Context context, List<Item> apps, int columnCount) {
        super(context, apps, columnCount);
        //for(int i=0;i<apps.size();i++)
        items=apps;
        //inflter = (LayoutInflater.from(context));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
            holder = new ViewHolder(convertView,position);
            convertView.setTag(holder);
        }/* else {
            holder = (ViewHolder) convertView.getTag();
        }*/

        /*LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(145,145);


        ImageView appIcon = (ImageView)convertView.findViewById(R.id.icon);
        appIcon.setLayoutParams(layoutParams);
        appIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //appIcon.setPadding(5,3,5,3);
        appIcon.setImageDrawable(items.get(position).icon);
        TextView appName = (TextView)convertView.findViewById(R.id.text);
        appName.setText(items.get(position).name);*/

        //holder.build(getItem(position).toString());

        //convertView = inflter.inflate(R.layout.item, null);
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

        /*void build(String title) {
            letterText.setText(title);
        }*/
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
