package com.keendesigns.ZBDiceLtd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//Basic layout for ZBDiceLtd instructions page
//programmer: M Chan
//programme: ZBDiceLtd.java
//created: Nov 20, 2016

public class Rules extends Activity  {
    ZBDiceLtd ob;
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

    // Locate the button in activity_main.xml
     Button menub = (Button) findViewById(R.id.buttonmenu);
    // Capture button clicks
    menub.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
        // Start Rules.class
        //Intent myIntent = new Intent(ZBDiceLtd.this, Rules.class);
        //startActivity(myIntent);
        finish();
            }
    });
    }
}