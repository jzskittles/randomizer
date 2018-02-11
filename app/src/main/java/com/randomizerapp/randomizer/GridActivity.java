package com.randomizerapp.randomizer;

/**
 * Created by jenny on 1/25/2018.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.askerov.dynamicgrid.DynamicGridView;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridActivity extends AppCompatActivity {

    private static final String TAG = GridActivity.class.getSimpleName();
    private DynamicGridView gridView;

    private PackageManager manager;
    private List<Item> apps;
    private List<Item> appsoriginal;
    private List<Item> appsshuffle;
    Switch switch1;

    Spinner sp;
    String quote="";


    String state="";
    String selectedAppName;
    String oldselectedAppName="None";
    String selectedChange="No";
    SlidingUpPanelLayout slidingPaneLayout;
    GridViewAdapter gridViewAdpter;

    Button tutorial;
    private StringRequest request;
    private String URL="http://rehabit.000webhostapp.com/selectApp.php";
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //three arraylists to handle different functions
        //used for spinner list
        apps = new ArrayList<Item>();
        //used to return to original state after randomizing
        appsoriginal = new ArrayList<Item>();
        //used to shuffle and show new randomized page
        appsshuffle= new ArrayList<Item>();
        //launch initial dialog, welcome page
        tutorial();


        switch1 = (Switch)findViewById(R.id.switchon);

        slidingPaneLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        //adds listeners and allows panel to sense swipe/click
        addPanelListener();
        //processes all apps downloaded into arraylists
        loadApps();

        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        //initial apps list
        gridViewAdpter = new GridViewAdapter(getApplicationContext(), appsshuffle, 5);
        gridView.setAdapter(gridViewAdpter);
        //adds listeners to gridview, allows it to respond to clicks and long clicks
        addListeners();

        //sets appsoriginal to current apps loaded
        for(int i=0;i<apps.size();i++){

            Item temp = new Item();
            temp.setIcon(apps.get(i).getIcon());
            temp.setLabel(apps.get(i).getLabel());
            temp.setName(apps.get(i).getName());
            appsoriginal.add(temp);
        }
        //loads spinner for single app selection
        loadSpinner();


    }

    public void tutorial(){
        //welcome statement, gives initial instructions to make sure user knows what to do
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage("Hello! Thank you for installing Rehabit!"+"\n" +
                "Swipe up from (or click on) the bottom of the screen for the Settings page").setPositiveButton(android.R.string.yes, null).show();
        TextView textView = (TextView)dialog.findViewById(android.R.id.message);
        textView.setTextSize(15);

    }

    public void addPanelListener(){
        slidingPaneLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            //handles different states of panels (collapsed and expanded) and changes the graphics based on each case, handles changes in settings
            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                TextView tx =(TextView)findViewById(R.id.textView);
                RelativeLayout mainLayout=(RelativeLayout)findViewById(R.id.mainrelative);

                if(newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                    //changes in formatting
                    tx.setVisibility(View.VISIBLE);
                    mainLayout.setBackgroundColor(Color.WHITE);
                }

                if(newState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                    //changes in formatting
                    tx.setVisibility(View.INVISIBLE);
                    mainLayout.setBackgroundColor(Color.TRANSPARENT);

                    //handles switch, randomizes
                    if(switch1.isChecked()){
                        if(!state.equals("switchon")) {
                            Collections.shuffle(appsshuffle);

                            manager = getPackageManager();

                            Intent m = new Intent(Intent.ACTION_MAIN, null);
                            m.addCategory(Intent.CATEGORY_LAUNCHER);

                            List<ResolveInfo> availableActivities = manager.queryIntentActivities(m, 0);
                            for(int i=0;i<appsshuffle.size();i++){
                                for(int y=0;y<availableActivities.size();y++) {
                                    if (appsshuffle.get(i).getName().equals(availableActivities.get(y).loadLabel(manager))){
                                        appsshuffle.get(i).setLabel(availableActivities.get(y).activityInfo.packageName);
                                        appsshuffle.get(i).setIcon(availableActivities.get(y).loadIcon(manager));
                                    }
                                }
                            }
                            state = "switchon";
                        }
                        //updates gridview
                        gridViewAdpter=new GridViewAdapter(getApplicationContext(), appsshuffle, 5);
                        gridView.setAdapter(gridViewAdpter);
                    }
                    else{
                        state="switchoff";

                        manager = getPackageManager();

                        Intent m = new Intent(Intent.ACTION_MAIN, null);
                        m.addCategory(Intent.CATEGORY_LAUNCHER);

                        //updates gridview
                        gridViewAdpter=new GridViewAdapter(getApplicationContext(), appsoriginal, 5);
                        gridView.setAdapter(gridViewAdpter);
                    }
                }
            }
        });
    }

    public void addListeners(){
        //Active dragging mode when long click at each Gridview item
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                gridView.startEditMode(position);
                new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                        .setMessage(appsshuffle.get(position).getName() + "\n" + appsoriginal.get(position).getName() + "\n" + apps.get(position).getName())
                        .setPositiveButton(android.R.string.yes, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();


                return true;
            }
        });

        //Handling click event of each Gridview item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)view.getTag();
                Intent n;
                if(hol.getAppName().equals(selectedAppName)) {
                    n = new Intent(getApplicationContext(), FolderActivity.class);

                    n.putExtra("appSelected",selectedAppName);
                    startActivityForResult(n,1);
                }
                else{
                    if(state.equals("switchon"))
                        n = manager.getLaunchIntentForPackage(appsshuffle.get(position).getLabel());
                    else
                        n = manager.getLaunchIntentForPackage(appsoriginal.get(position).getLabel());
                    startActivity(n);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //handles information from folderactivity, gets inspirational quote
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                quote=data.getStringExtra("quote");
                new android.support.v7.app.AlertDialog.Builder(GridActivity.this)
                        .setTitle("Inspirational Quote")
                        .setMessage(""+quote)
                        .setPositiveButton(android.R.string.yes, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }else{
                quote="";
            }
        }
    }

    private void loadSpinner(){
        //adds option to not use the single app selection function
        Item noneapp = new Item();
        noneapp.name = "None";
        apps.add(0,noneapp);

        //deploys specific layout for spinner, image + text
        sp = (Spinner)findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinner_layout, R.id.spinnerText, (ArrayList)apps);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = sp.getSelectedItemPosition();
                selectedAppName = apps.get(position).getName();
                if(!oldselectedAppName.equals(selectedAppName)){
                    //sets state so that app name is only sent to database if it is a new app
                    selectedChange="Change";
                    //adds feedback system to see trends in apps that people choose for single-app selection
                    requestQueue = Volley.newRequestQueue(getApplicationContext());

                    request = new StringRequest(Request.Method.POST, "http://rehabit.000webhostapp.com/selectApp.php", new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError{

                            Map<String, String> hashMap = new HashMap<String, String>();
                            if(!selectedAppName.equals("None")&&selectedChange.equals("Change"))
                                hashMap.put("appSelected",selectedAppName);
                            return hashMap;
                        }

                        @Override
                        public Map<String,String> getHeaders()throws AuthFailureError{
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("Content-Type","application/x-www-form-urlencoded");
                            return params;
                        }
                    };
                    if(!selectedAppName.equals("None")&&selectedChange.equals("Change"))
                        requestQueue.add(request);
                }

                else
                    selectedChange = "No change";
                oldselectedAppName=selectedAppName;
            }

            public void onNothingSelected(AdapterView<?> parent){
                selectedAppName = "None";
            }
        });
    }

    private void loadApps() {
        manager = getPackageManager();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            Item app = new Item();

            app.name = ri.loadLabel(manager); //get app name
            app.icon = ri.loadIcon(manager); //get app icon
            if(!app.name.equals(selectedAppName)){
                app.label = ri.activityInfo.packageName;}

            //adds apps to shuffled list and normal app list for initialization
            appsshuffle.add(app);
            appsoriginal.add(app);
            apps.add(app);
            app.position = appsshuffle.indexOf(app);
            app.position = apps.indexOf(app);
        }
    }

    @Override
    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
            //sets back button to update apps for drag and drop, different methods to solve original bug
            if(!switch1.isChecked())
                updateApps();
            else updateShuffleApps();
        } else {
            super.onBackPressed();
        }
    }

    public void updateShuffleApps(){

        int size = gridView.getChildCount();
        manager = getPackageManager();

        Intent m = new Intent(Intent.ACTION_MAIN, null);
        m.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(m, 0);
        //goes through each item and makes sure apps are updated and match
        for(int i=0;i<size;i++){
            View gridChild = (View)gridView.getChildAt(i);
            GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)(gridChild.getTag());
            appsshuffle.get(i).setName(hol.getAppName());
            for(int y=0;y<availableActivities.size();y++) {
                if (hol.getAppName().equals(availableActivities.get(y).loadLabel(manager))){
                    appsshuffle.get(i).setLabel(availableActivities.get(y).activityInfo.packageName);
                    appsshuffle.get(i).setIcon(availableActivities.get(y).loadIcon(manager));
                }
            }
        }

    }

    public void updateApps(){

        int size = gridView.getChildCount();
        manager = getPackageManager();

        Intent m = new Intent(Intent.ACTION_MAIN, null);
        m.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(m, 0);
        //goes through each item and makes sure apps are updated and match
        for(int i=0;i<size;i++){
            View gridChild = (View)gridView.getChildAt(i);
            GridViewAdapter.ViewHolder hol = (GridViewAdapter.ViewHolder)(gridChild.getTag());
            appsoriginal.get(i).setName(hol.getAppName());
            for(int y=0;y<availableActivities.size();y++) {
                if (hol.getAppName().equals(availableActivities.get(y).loadLabel(manager))){
                    appsoriginal.get(i).setLabel(availableActivities.get(y).activityInfo.packageName);
                    appsoriginal.get(i).setIcon(availableActivities.get(y).loadIcon(manager));
                }
            }
        }

    }

}
