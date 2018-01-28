package com.randomizerapp.randomizer;

import android.content.Intent;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int rows=5;
    int columns=5;

    GridView grid;
    Switch switch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        switch1 = (Switch)findViewById(R.id.switchon);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), AppsListActivity.class);
                Intent intent = new Intent(getApplicationContext(), GridActivity.class);
                if(switch1.isChecked())
                    intent.putExtra("EXTRA","switchon");
                else
                    intent.putExtra("EXTRA","switchoff");
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        //donothing
    }
}
