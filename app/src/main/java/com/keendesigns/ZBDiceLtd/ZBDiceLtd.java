package com.keendesigns.ZBDiceLtd;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;

//Basic layout for ZBDiceLtd starting page
//programmer: M Chan
//programme: ZBDiceLtd.java
//created: Nov 20, 2016
//modified Nov 22, 2016
//modified Dec 4, 2016
//main, rules and game template with passing player parameter to game page
public class ZBDiceLtd extends Activity {
    Button brules,bgame,bexit;
    RadioGroup robotRadioGroup;
    int robot;
    final int retCode=007;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        robotRadioGroup = (RadioGroup) findViewById(R.id.robotRadioGroup);
        RadioButton ckbutton = (RadioButton) findViewById(R.id.radioBrobot);
        ckbutton.setChecked(true);
        /*
        RadioButton selectRadio = (RadioButton) findViewById(robotRadioGroup.getCheckedRadioButtonId());
        if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioBrobot) robot=1;
        if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioB2play) robot=0;
        if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioBrobot)
            System.out.println("checked 1 robot : "+robot);
        if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioB2play)
            System.out.println("checked 0 players : "+robot);
            */
        // Locate the button in activity_main.xml
        brules = (Button) findViewById(R.id.buttonrules);
        // Capture button clicks
        brules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start Rules.class
                Intent myIntent = new Intent(ZBDiceLtd.this, Rules.class);
                startActivity(myIntent);
            }
        });

        // Locate the button in activity_main.xml
        bexit = (Button) findViewById(R.id.buttonexit);
        // Capture button clicks
        bexit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start Rules.class
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
           }
        });

        bgame = (Button) findViewById(R.id.buttongame);
        // Capture button clicks
        bgame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start Rules.class
                //RadioButton selectRadio = (RadioButton) findViewById(robotRadioGroup.getCheckedRadioButtonId());
                if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioBrobot) robot=1;
                if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioB2play) robot=0;
                //if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioBrobot)
                 //   System.out.println("checked 1 robot : "+robot);
                if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioB2play) {
                    System.out.println("checked 2 players option! Invalid. ");
//upgrade dialog box
                    AlertDialog.Builder builder=new AlertDialog.Builder(ZBDiceLtd.this);
                    builder.setCancelable(true);
                    builder.setIcon(R.drawable.upgradedialog);
                    builder.setTitle("Incoming Call");
                    builder.setInverseBackgroundForced(true);
                    builder.setPositiveButton("Accept",new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            robot=1;
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("Later",new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            robot=1;
                            dialog.dismiss();
                        }

                    });

                    AlertDialog alert=builder.create();
                    alert.show();

                    //ckbutton.setChecked(true);
                    RadioButton ckbutton = (RadioButton) findViewById(R.id.radioBrobot);
                    ckbutton.setChecked(true);
                }
                //
                //robot=1;
                if (robot==1) {
                    Intent ii = new Intent(ZBDiceLtd.this, Game.class);
                    Bundle bun = new Bundle();
                    //robot=0;
                    bun.putInt("robot", robot);
                    ii.putExtras(bun);
                    System.out.println("passing play data : " + robot);
                    startActivityForResult(ii, retCode);
                    // startActivity(ii);
                    //finish();
                } //if robot=1 end
            }
        });

    }

}