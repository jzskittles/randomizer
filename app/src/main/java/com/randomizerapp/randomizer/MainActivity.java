package com.randomizerapp.randomizer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Switch switch1;

    private PackageManager manager;
    private ArrayList<Item> apps;
    private ArrayList<Item> appsoriginal;

    String selectedAppName;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        switch1 = (Switch)findViewById(R.id.switchon);

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
        Item noneapp = new Item();
        noneapp.name = "None";
        apps.add(noneapp);
        /*new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("what's going on")
                .setMessage("hi")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();*/
        /*appsoriginal=apps;
        if(switch1.isChecked()){
            Collections.shuffle(apps);
        }
        else
            apps=appsoriginal;*/

        sp = (Spinner)findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinnerText, apps);
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








        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), AppsListActivity.class);
                //Intent intent = new Intent(getApplicationContext(), FolderActivity.class);
                Intent intent = new Intent(getApplicationContext(), GridActivity.class);
                /*Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("app_list", apps);
                intent.putExtras(bundle);*/
                intent.putExtra("appSelected",selectedAppName);

                if(switch1.isChecked())
                    intent.putExtra("EXTRA","switchon");
                else
                    intent.putExtra("EXTRA","switchoff");
                startActivity(intent);
            }
        });





    }
    public void clicks(View v){
        Intent n = new Intent(getApplicationContext(), GridActivity.class);
        n.putExtra("appSelected",selectedAppName);
        n.putExtra("fromMain","Main");
        /*new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("what's going on")
                        .setMessage("click!"+ " "+n.getStringExtra("fromMain"))
                        .setPositiveButton(android.R.string.yes, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();*/
        startActivity(n);
    }

    @Override
    public void onBackPressed() {
        //donothing
    }
}
