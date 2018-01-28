package com.randomizerapp.randomizer;

/**
 * Created by jenny on 1/25/2018.
 */

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private static final String TAG = GridActivity.class.getSimpleName();
    private DynamicGridView gridView;

    private PackageManager manager;
    private List<Item> apps;
    private List<Item> appsoriginal;

    String state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_grid);

        state = getIntent().getStringExtra("EXTRA");

        loadApps();
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);

        GridViewAdapter gridViewAdpter = new GridViewAdapter(getApplicationContext(), apps, 5);
        gridView.setAdapter(gridViewAdpter);

        //Handling click event of each Grid view item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
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

                Intent n = manager.getLaunchIntentForPackage(apps.get(position).getLabel());
                startActivity(n);
            }
        });

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
    }

    private void loadApps() {
        manager = getPackageManager();
        apps = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            Item app = new Item();
            app.label = ri.activityInfo.packageName;
            app.name = ri.loadLabel(manager); //get app name
            app.icon = ri.loadIcon(manager); //get app icon
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
