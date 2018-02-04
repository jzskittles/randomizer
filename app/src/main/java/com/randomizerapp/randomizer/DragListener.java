package com.randomizerapp.randomizer;


import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jenny on 1/27/2018.
 */

public class DragListener implements View.OnDragListener {

    @Override
    public boolean onDrag(View v, DragEvent event){
        int action = event.getAction();
        switch(event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                View view = (View)event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                Folder newFolder = new Folder();
                //newFolder.addView(view, owner);
                view.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                 break;
            default:
                break;

        }
        return true;
    }
}
