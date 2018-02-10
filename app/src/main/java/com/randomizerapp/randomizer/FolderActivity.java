package com.randomizerapp.randomizer;

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

/**
 * Created by jenny on 1/27/2018.
 */

public class FolderActivity extends AppCompatActivity{

    List<Item> folderApps;
    DynamicGridView folderGridView;

    String app1;
    int position;

    ArrayList<String> quotes;

    private PackageManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder);

        //keeps apps in arraylist
        folderApps = new ArrayList<Item>();

        //gets apps selected form other activity
        app1 = getIntent().getStringExtra("appSelected");
        if(!app1.equals("")){addFolderApps(app1);}


        folderGridView = (DynamicGridView) findViewById(R.id.folder_grid);
        //set inspirational quotes
        quotes = new ArrayList<String>();
        quotes.add("“Nothing is impossible; the word itself says, ‘I’m possible!’” – Audrey Hepburn");
        quotes.add("“People often say that motivation doesn’t last. Neither does bathing. That’s why we recommend it daily.” – Zig Ziglar");
        quotes.add("“When everything seems to be going against you, remember that the airplane takes off against the wind, not with it.” – Henry Ford");
        quotes.add("“If we are facing in the right direction, all we have to do is keep on walking.” – Zen proverb");
        quotes.add("“Believe you can and you’re halfway there.” – Theodore Roosevelt");
        quotes.add("“Though no one can go back and make a brand new start, anyone can start from now and make a brand new ending.” – Carl Bard");
        quotes.add("“Our greatest glory is not in never failing, but in rising up every time we fail.” – Ralph Waldo Emerson");
        quotes.add("“If you can quit for a day, you can quit for a lifetime.” – Benjamin Alire Sáenz");
        quotes.add("“Whether you think you can or you think you can’t, you’re right.” – Henry Ford");

        //initial folder list
        FolderAdapter folderAdapter = new FolderAdapter(getApplicationContext(),folderApps,3);
        folderGridView.setAdapter(folderAdapter);

        //Handling click event of each Gridview item
        folderGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                FolderAdapter.ViewHolder hol = (FolderAdapter.ViewHolder)view.getTag();

                Intent m;
                if(folderApps.get(position).label!=null){
                    //if you click on the correct app, launch the correct app
                    m = manager.getLaunchIntentForPackage(folderApps.get(position).label.toString());
                    startActivity(m);
                }else{
                    //sends quote info back to gridActivity
                    m=getIntent();
                    Collections.shuffle(quotes);
                    m.putExtra("quote",quotes.get(0));
                    setResult(RESULT_OK,m);
                    finish();
                }
            }
        });

        //Active dragging mode when long click at each Gridview item
        folderGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                folderGridView.startEditMode(position);

                return true;
            }
        });

    }

    private void loadFolderApps() {
        int size = folderApps.size();
        manager = getPackageManager();

        Intent m = new Intent(Intent.ACTION_MAIN, null);
        m.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(m, 0);
        for(int i=0;i<size;i++){
            View gridChild = (View)folderGridView.getChildAt(i);
            FolderAdapter.ViewHolder hol = (FolderAdapter.ViewHolder)(gridChild.getTag());
            folderApps.get(i).setName(hol.getAppName());
            for(int y=0;y<availableActivities.size();y++) {
                if (hol.getAppName().equals(availableActivities.get(y).loadLabel(manager))){
                    folderApps.get(i).setLabel(availableActivities.get(y).activityInfo.packageName); //sets app name
                    folderApps.get(i).icon = availableActivities.get(y).loadIcon(manager); //sets icon
                }
            }
        }
    }

    private void addFolderApps(String name) {
        manager = getPackageManager();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);

        //initializes dummy and correct app, sets icon and name to same
        Item app = new Item();
        Item dummy=new Item();
        app.name = name;
        for(int y=0;y<availableActivities.size();y++) {
            if (name.equals(availableActivities.get(y).loadLabel(manager))) {
                app.label = availableActivities.get(y).activityInfo.packageName;
                app.icon = availableActivities.get(y).loadIcon(manager);
                dummy.name=availableActivities.get(y).loadLabel(manager);
                dummy.icon = availableActivities.get(y).loadIcon(manager);
            }
        }
        //1 correct app
        folderApps.add(app);

        //8 dummy apps
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        //shuffles order of folder apps
        Collections.shuffle(folderApps);
    }

    @Override
    public void onBackPressed() {
        if (folderGridView.isEditMode()) {
            folderGridView.stopEditMode();
            //sets back button to update apps for drag and drop
            updateApps();
        } else {
            super.onBackPressed();
        }
    }

    public void updateApps(){
        int size = folderGridView.getChildCount();
        manager = getPackageManager();

        Intent m = new Intent(Intent.ACTION_MAIN, null);
        m.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(m, 0);
        //goes through each item and makes sure apps are updated and match
        for(int i=0;i<size;i++){
            View gridChild = (View)folderGridView.getChildAt(i);
            FolderAdapter.ViewHolder hol = (FolderAdapter.ViewHolder)(gridChild.getTag());
            folderApps.get(i).setName(hol.getAppName());
            for(int y=0;y<availableActivities.size();y++) {
                if (hol.getAppName().equals(availableActivities.get(y).loadLabel(manager))){
                    folderApps.get(i).setLabel(availableActivities.get(y).activityInfo.packageName);
                    folderApps.get(i).icon = availableActivities.get(y).loadIcon(manager);
                }
            }
        }
    }
}
