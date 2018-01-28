package com.randomizerapp.randomizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jenny on 1/8/2018.
 */

public class Grid extends Activity {
    PackageManager myPackageManager;

    public class MyBaseAdapter extends BaseAdapter {
        private Context myContext;
        private List<ResolveInfo> appList;

        MyBaseAdapter(Context c, List<ResolveInfo> l){
            myContext = c;
            appList = l;
        }

        @Override
        public int getCount(){
            return appList.size();
        }

        @Override
        public Object getItem(int position){
            return appList.get(position);
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView imageView;
            View grid;
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(convertView == null){
                /*grid = new View(myContext);
                grid = inflater.inflate(R.layout.item, null);
                TextView textView = (TextView)grid.findViewById(R.id.name);
                imageView = grid.findViewById(R.id.icon);*/
                imageView = new ImageView(myContext);
                imageView.setLayoutParams(new GridView.LayoutParams(165,165));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,16,8,16);
            }else{
                imageView = (ImageView)convertView;
            }

            ResolveInfo resolveInfo = appList.get(position);
            imageView.setImageDrawable(resolveInfo.loadIcon(myPackageManager));


            return imageView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_secondary);

        myPackageManager = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);

        GridView gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new MyBaseAdapter(this, intentList));

        gridView.setOnItemClickListener(myOnItemClickLIstener);
    }

    AdapterView.OnItemClickListener myOnItemClickLIstener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            ResolveInfo cleckedResolveInfo = (ResolveInfo)parent.getItemAtPosition(position);
            ActivityInfo clickedActivityInfo = cleckedResolveInfo.activityInfo;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClassName(clickedActivityInfo.applicationInfo.packageName, clickedActivityInfo.name);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startActivity(intent);
        }
    };
}
