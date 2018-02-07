package com.randomizerapp.randomizer;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jenny on 1/27/2018.
 */

public class Folder {
    CharSequence name; //app name
    Drawable icon; //app icon
    int position;

    List<Item> folderApps;
    Item dummy;

    public Folder(){

    }

    public Folder(Item selectedApp){
        folderApps.add(selectedApp);
        dummy.setName(selectedApp.name.toString());
        //dummy.setLabel(selectedApp.label.toString());
        dummy.setIcon(selectedApp.icon);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);
        folderApps.add(dummy);

    }

    public String getName(){
        return name.toString();
    }
    public void setName(String appName){
        name = appName;
    }
    public int getPosition(){
        return position;
    }
    public void setPosition(int i){
        position=i;
    }
    public List<Item> getFolderApps(){return folderApps;}
    public void addFolderApp(Item i){folderApps.add(i);}
}
