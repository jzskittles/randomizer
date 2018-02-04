package com.randomizerapp.randomizer;

/**
 * Created by jenny on 1/25/2018.
 */

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;


import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private static final String TAG = GridActivity.class.getSimpleName();
    private DynamicGridView gridView;

    private PackageManager manager;
    private List<Item> apps;
    private List<Item> appsoriginal;


    String state="";
    String selectedAppName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_grid);
        if(getIntent().hasExtra("EXTRA"))
            state = getIntent().getStringExtra("EXTRA");
        else{
            state="";

        }
        if(getIntent().hasExtra("quote")){
            new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                    .setTitle("Inspirational Quote")
                    .setMessage(""+getIntent().getStringExtra("quote"))
                    .setPositiveButton(android.R.string.yes, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        selectedAppName = getIntent().getStringExtra("appSelected");
        /*Bundle bundle = getIntent().getExtras();
        apps = bundle.getParcelableArrayList("app_list");*/

        loadApps();
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);

        GridViewAdapter gridViewAdpter = new GridViewAdapter(getApplicationContext(), apps, 5);
        gridView.setAdapter(gridViewAdpter);
        /*gridView.setOnDragListener(new View.OnDragListener(){
            @Override
            public boolean onDrag(View v, DragEvent event){
                View app2;
                int action = event.getAction();
                switch(event.getAction()){
                    case DragEvent.ACTION_DRAG_STARTED:
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //app2 = (View)event.getLocalState();
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:
                        View view = (View)event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        //Folder newFolder = new Folder(view, v);
                        //newFolder.addView();
                        view.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getApplicationContext(), FolderActivity.class);
                        GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)view.getTag();
                        GridViewAdapter.ViewHolder hol2 = (GridViewAdapter.ViewHolder)v.getTag();
                        intent.putExtra("app1name",hol.getAppName());
                        intent.putExtra("app2name",hol2.getAppName());
                        startActivity(intent);

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });*/

        //Active dragging mode when long click at each Grid view item
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                gridView.startEditMode(position);
                //GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)view.getTag();

                //updateApps();

                /*new AlertDialog.Builder(GridActivity.this)
                        .setTitle("Item information")
                        .setMessage("You clicked at position: " + position +"\n"
                                + apps.get(position).getName()+ " \n"+gridView.getstartDragPosition(position)+"\n"+ apps.get(position).getPosition()
                        +"\n"+ hol.getAppName()+"\n"+hol.getAppPos())
                        .setPositiveButton(android.R.string.yes, null)

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();*/
                return true;
            }
        });

        //Handling click event of each Grid view item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)view.getTag();
                //updateApps();
                /*new AlertDialog.Builder(GridActivity.this)
                    .setTitle("Item information")
                    .setMessage("You clicked at position: " + position +"\n"
                            + apps.get(position).getName()+ " \n"+gridView.getstartDragPosition(position)+"\n"+ apps.get(position).getPosition()
                            +"\n"+ hol.getAppName()+"\n"+hol.getAppPos())
                    .setPositiveButton(android.R.string.yes, null)

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();*/
                Intent n;
                if(hol.getAppName().equals(selectedAppName)) {
                    /*new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                        .setTitle("Item information")
                        .setMessage("You clicked at "+selectedAppName+"what")
                        .setPositiveButton(android.R.string.yes, null)

                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();*/
                    n = new Intent(getApplicationContext(), FolderActivity.class);

                    n.putExtra("appSelected",selectedAppName);
                }
                else
                    n = manager.getLaunchIntentForPackage(apps.get(position).getLabel());
                startActivity(n);
            }
        });


    }

    private void loadApps() {
        manager = getPackageManager();
        apps = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            Item app = new Item();

            app.name = ri.loadLabel(manager); //get app name
            app.icon = ri.loadIcon(manager); //get app icon
            if(!app.name.equals(selectedAppName)){
                //Folder nineApp = new Folder(app);
                /*intent.putExtra("appSelected",selectedAppName);
            }else*/
                app.label = ri.activityInfo.packageName;}
            /*if(!app.name.equals(selectedAppName))
                apps.add(app);*/
            apps.add(app);
            app.position = apps.indexOf(app);
        }
        appsoriginal=apps;
        if(state.equals("switchon")){
            Collections.shuffle(apps);
        }
        else
            apps=appsoriginal;
    }

    @Override
    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
            updateApps();
        } else {
            super.onBackPressed();
        }
    }

    public void updateApps(){
        int size = gridView.getChildCount();
        manager = getPackageManager();

        Intent m = new Intent(Intent.ACTION_MAIN, null);
        m.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(m, 0);
        for(int i=0;i<size;i++){
            View gridChild = (View)gridView.getChildAt(i);
            GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)(gridChild.getTag());
            apps.get(i).setName(hol.getAppName());
            for(int y=0;y<availableActivities.size();y++) {
                if (hol.getAppName().equals(availableActivities.get(y).loadLabel(manager)))
                    apps.get(i).setLabel(availableActivities.get(y).activityInfo.packageName);
            }
        }
    }
}
