package com.randomizerapp.randomizer;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppsListActivity extends AppCompatActivity{ //} implements View.OnTouchListener, View.OnDragListener {

    private PackageManager manager;
    private List<Item> apps;

    private ListView list;

    private GridView grid;

    String state;
    private List<Item> appsoriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_secondary);

        state = getIntent().getStringExtra("EXTRA");

        loadApps();
        //loadListView();
        loadGridView();
        addClickListener();

    }

    private void loadApps() {
        manager = getPackageManager();
        apps = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            Item app = new Item();
            app.label = ri.activityInfo.packageName; //get app package
            app.name = ri.loadLabel(manager); //get app name
            app.icon = ri.loadIcon(manager); //get app icon
            apps.add(app);
        }
        appsoriginal=apps;
        if(state.equals("switchon"))
            Collections.shuffle(apps);
        else
            apps=appsoriginal;
    }

    private void loadListView() {
        list = (ListView) findViewById(R.id.list);

        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this, R.layout.item, apps) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item, null);
                }

                ImageView appIcon = (ImageView) convertView.findViewById(R.id.icon);


                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appName = (TextView) convertView.findViewById(R.id.text);
                //appName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 5);
                appName.setText(apps.get(position).name);



                return convertView;
            }
        };
        list.setAdapter(adapter);
    }

    private void loadGridView(){
        grid = (GridView) findViewById(R.id.grid);
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this, R.layout.item, apps){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent){
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.item, null);
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(145,145);


                ImageView appIcon = (ImageView)convertView.findViewById(R.id.icon);
                appIcon.setLayoutParams(layoutParams);
                appIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //appIcon.setPadding(5,3,5,3);
                appIcon.setImageDrawable(apps.get(position).icon);
                TextView appName = (TextView)convertView.findViewById(R.id.text);
                appName.setText(apps.get(position).name);

                return convertView;
            }
        };
        grid.setAdapter(adapter);
    }

    private void addClickListener() {
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = manager.getLaunchIntentForPackage(apps.get(position).label.toString());
                startActivity(i);
            }
        });
    }

    /*private void addLongClickListener(){
        grid.setOnLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?>, View view, int position, long id){
                ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());
                Toast.makeText(getApplicationContext(),"long click", Toast.LENGTH_LONG);
                return true;
            }
        });

    }

    public boolean onLongClick(View v){

    }*/

}
