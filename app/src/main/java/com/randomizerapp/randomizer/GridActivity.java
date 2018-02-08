package com.randomizerapp.randomizer;

/**
 * Created by jenny on 1/25/2018.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
    private List<Item> appsshuffle;
    private RelativeLayout mLayout;
    Switch switch1;

    Spinner sp;
    String quote="";


    String state="";
    String selectedAppName;
    SlidingUpPanelLayout slidingPaneLayout;
    GridViewAdapter gridViewAdpter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        apps = new ArrayList<Item>();
        appsoriginal = new ArrayList<Item>();
        appsshuffle= new ArrayList<Item>();

        tutorial();


        switch1 = (Switch)findViewById(R.id.switchon);

        slidingPaneLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.mainrelative);
        //rl.setBackgroundColor(Color.TRANSPARENT);
        addPanelListener();

        loadApps();

        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        gridViewAdpter = new GridViewAdapter(getApplicationContext(), appsshuffle, 5);
        gridView.setAdapter(gridViewAdpter);

        addListeners();


        for(int i=0;i<apps.size();i++){
            Item temp = new Item();
            temp.setIcon(apps.get(i).getIcon());
            temp.setLabel(apps.get(i).getLabel());
            temp.setName(apps.get(i).getName());
            appsoriginal.add(temp);
            //appsshuffle.add(temp);
        }





        /*manager = getPackageManager();
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
        }*/

        loadSpinner();

    }

    public void tutorial(){

        /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

        // set prompts.xml to alertdialog builder
        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                        .setTitle("Tutorial")
                        .setMessage("Your downloaded apps will be displayed in a grid as shown"+"\n"+
                                //"This app was designed to eliminate environmental triggers involved with phone addiction"+"\n"+
                                "Swipe up from the bottom of the screen to access the Settings page")
                        .setPositiveButton(android.R.string.yes, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });

        // create alert dialog
        // alertDialog = alertDialogBuilder.create();
        // show it
        alertDialogBuilder.show();*/
        new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                .setTitle("Tutorial")
                .setMessage("Hello! Thank you for installing this app"+"\n"+
                        //"This app was designed to eliminate environmental triggers involved with phone addiction"+"\n"+
                        "Click OK for a tutorial about the functions")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

        /*new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                .setTitle("Tutorial")
                .setMessage("Your downloaded apps will be displayed in a grid as shown"+"\n"+
                        //"This app was designed to eliminate environmental triggers involved with phone addiction"+"\n"+
                        "Swipe up from the bottom of the screen to access the Settings page")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();*/
    }

    public void addPanelListener(){
        slidingPaneLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                TextView tx =(TextView)findViewById(R.id.textView);
                if(newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED))
                    tx.setVisibility(View.VISIBLE);
                if(newState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                    tx.setVisibility(View.INVISIBLE);
                    if(switch1.isChecked()){
                        /*new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                                .setTitle("what's going on")
                                .setMessage("shuffle")
                                .setPositiveButton(android.R.string.yes, null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();*/

                        /*new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                                .setTitle("what's going on")
                                .setMessage("shuffle"+appsshuffle.get(0).getName()+"\n"+appsoriginal.get(0).getName())
                                .setPositiveButton(android.R.string.yes, null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();*/
                        if(!state.equals("switchon")) {
                            Collections.shuffle(appsshuffle);
                            //int size = gridView.getChildCount();
                            manager = getPackageManager();

                            Intent m = new Intent(Intent.ACTION_MAIN, null);
                            m.addCategory(Intent.CATEGORY_LAUNCHER);

                            List<ResolveInfo> availableActivities = manager.queryIntentActivities(m, 0);
                            for(int i=0;i<appsshuffle.size();i++){
                                //View gridChild = (View)gridView.getChildAt(i);
                                //GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)(gridChild.getTag());
                                for(int y=0;y<availableActivities.size();y++) {
                                    if (appsshuffle.get(i).getName().equals(availableActivities.get(y).loadLabel(manager))){
                                        appsshuffle.get(i).setLabel(availableActivities.get(y).activityInfo.packageName);
                                        appsshuffle.get(i).setIcon(availableActivities.get(y).loadIcon(manager));
                                    }
                                }
                            }
                            state = "switchon";
                        }
                        //updateApps();
                        gridViewAdpter=new GridViewAdapter(getApplicationContext(), appsshuffle, 5);
                        gridView.setAdapter(gridViewAdpter);
                    }
                    else{
                        state="switchoff";
                        /*new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                                .setTitle("what's going on")
                                .setMessage("unshuffle"+appsshuffle.get(0).getName()+"\n"+appsoriginal.get(0).getName())
                                .setPositiveButton(android.R.string.yes, null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();*/
                        //apps=appsoriginal;
                        //updateApps();
                        gridViewAdpter=new GridViewAdapter(getApplicationContext(), appsoriginal, 5);
                        gridView.setAdapter(gridViewAdpter);
                    }
                }
            }
        });
    }

    public void addListeners(){
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
                    startActivityForResult(n,1);
                }
                else{
                    n = manager.getLaunchIntentForPackage(appsshuffle.get(position).getLabel());
                    startActivity(n);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                quote=data.getStringExtra("quote");
                new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                        .setTitle("Inspirational Quote")
                        .setMessage(""+quote)
                        .setPositiveButton(android.R.string.yes, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }else
                quote="";
        }
    }

    private void loadSpinner(){
        Item noneapp = new Item();
        noneapp.name = "None";
        apps.add(0,noneapp);

        sp = (Spinner)findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_layout, R.id.spinnerText, (ArrayList)apps);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = sp.getSelectedItemPosition();
                selectedAppName = apps.get(position).getName();
                //GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)view.getTag();
                //selectedAppName = hol.getAppName();
            }

            public void onNothingSelected(AdapterView<?> parent){
                selectedAppName = "None";
            }
        });
        //apps.remove(noneapp);
    }

    private void loadApps() {
        manager = getPackageManager();
        //apps = new ArrayList<>();

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
            appsshuffle.add(app);
            apps.add(app);
            app.position = appsshuffle.indexOf(app);
            app.position = apps.indexOf(app);
        }
        //appsoriginal=apps;
        /*if(state.equals("switchon")){
            Collections.shuffle(apps);
        }
        else
            apps=appsoriginal;*/
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
            appsshuffle.get(i).setName(hol.getAppName());
            for(int y=0;y<availableActivities.size();y++) {
                if (hol.getAppName().equals(availableActivities.get(y).loadLabel(manager))){
                    appsshuffle.get(i).setLabel(availableActivities.get(y).activityInfo.packageName);

                }
            }
        }
    }

}
