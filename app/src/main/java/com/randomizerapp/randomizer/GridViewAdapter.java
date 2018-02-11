package com.randomizerapp.randomizer;

/**
 * Created by jenny on 1/25/2018.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridViewAdapter extends BaseDynamicGridAdapter {
    List<Item> items;

    public GridViewAdapter(Context context, List<Item> apps, int columnCount) {
        super(context, apps, columnCount);
        //takes in apps from gridview activity
        items=apps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //takes layout of item, makes it standardized
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
            holder = new ViewHolder(convertView);
            //holder.letterText = (TextView)convertView.findViewById(R.id.text);
            //holder.icon = (ImageView)convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();
        Item app = (Item)getItem(position);
        holder.letterText.setText(app.getName());
        holder.icon.setImageDrawable(app.getIcon());
        //holder.icon.setImageDrawable(getItem(position));
        //holder.build(items.get(position).getName(),position);
        return convertView;
    }

    public class ViewHolder {
        private ImageView icon; //app icon
        private TextView letterText; //app name
        private int pos; //app position

        private ViewHolder(View view) {
            //app formatting
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width/5-45,width/5-50);

            Typeface face = Typeface.createFromAsset(getContext().getAssets(),"Roboto-Regular.ttf");

            letterText = (TextView) view.findViewById(R.id.text);
            letterText.setTypeface(face);
            letterText.setTextSize(10);
            icon = (ImageView)view.findViewById(R.id.icon);
            icon.setLayoutParams(layoutParams);
            icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //icon.setImageDrawable(items.get(position).icon);
            //letterText.setText(items.get(position).name);
            //pos = items.get(position).position;
        }
        //methods to fetch characteristics of apps
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

        /*void build(String title, int position){
            letterText.setText(title);
            icon.setImageDrawable(items.get(position).icon);
        }*/

    }

}
