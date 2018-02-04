package com.randomizerapp.randomizer;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jenny on 1/27/2018.
 */

public class TouchListener implements View.OnTouchListener{
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
    }

}
