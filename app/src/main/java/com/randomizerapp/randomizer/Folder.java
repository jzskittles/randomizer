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
    //List<Item> folderAppsOriginal;

    DynamicGridView folderGridView;

    //String state;
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

    private PackageManager manager;

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

    /*private final class TouchListener implements View.OnTouchListener{
        public boolean onTouch(View view, MotionEvent motionEvent){
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            }else{
                return false;
            }
            /*if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                //Folder folder = new Folder(view)
            }
        }
    }

    public class DragListener implements View.OnDragListener {
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
                    Folder newFolder = new Folder(view, v);
                    //newFolder.addView();
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }*/

    /*public class HoverListener implements View.OnHoverListener{
        @Override
        public boolean onHover(View view, MotionEvent event){
            switch(event.getAction()){
                case MotionEvent.ACTION_HOVER_ENTER:


            }
        }
    }*/
}
