package com.keendesigns.ZBDiceLtd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;
import java.util.Random;
import android.media.MediaPlayer;
import android.media.AudioManager;

/**
 * Created by mic on 2016-11-27.
 * adapted from ZombDiceS7w.java
 * modified 2016-12-04
 * all imageviews and textviews variable ready to be updated by routines
 * draw,roll,again and pass worked
 * challenge questions worked
 * sound added but not working
 * icon added
 * all working (no sound) but eratic crashes due to out of memory
 * sound files deleted to save memory
 * all graphics scaled down and no more crashes
 */
/**
 * @author Michael Chan
//dice number notation     G   Y   R
//                  roll:  1   2   3
//                         4   5   6
//                         7   8   9
//                        10  11  12
//                     brain feet blast
//java.util.List<Integer> diceStr = new java.util.ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,2,2,2,2,3,3,3));
//List<Integer> grnStr = new ArrayList<Integer>(Arrays.asList(4,4,4,5,5,6));
//List<Integer> yelStr = new ArrayList<Integer>(Arrays.asList(7,7,8,8,9,9));
//List<Integer> redStr = new ArrayList<Integer>(Arrays.asList(10,11,11,12,12,12));
//	String[] diepic = {"nodice.png","greendice.png","yellowdice.png","reddice.png","greenbrain.png","greenblast1.png","greenfeet.png",
//	"yellowbrain.png","yellowblast1.png","yellowfeet.png","redbrain.png","redblast1.png","redfeet.png"};
die1,die2,die3
stageNo=0 main page
stageNo=1 instruction page
stageNo=2 New game page
turnover=0   draw die colors
turnover=1   roll dice
turnover=2   play again/pass
turnover=3   turnover and pass
 */
public class Game extends Activity {
    ZBDiceLtd ob;
    int robot=1;
    ImageView player1img,player2img,die1img,die2img,die3img;
    ImageView bonus1img,bonus2img;
    ImageView q1img,q2img,q3img,calmimg;
    TextView score1t,score2t;
    TextView mquest1a,mquest1b;
    TextView promptmsgt;
    TextView gbrnt,ybrnt,rbrnt,gblnt,yblnt,rblnt;
    String prompt="";
    String mq1,mq2;
    Double inpnum;

    int stageNo=0;
    int turnover=0;
    int blastn,brainn,gameover;
    int player=0;
    int score1,score2;
    int die1,die2,die3;
    int yblastn,ybrainn,gblastn,gbrainn,rbrainn,rblastn;

    java.util.List <Integer>randStr;
    java.util.List <Integer>randStrbk;
    int questno=0;
    int qmsgon=0;
    //int robot=0; //0=2 players; 1=computer opponent
    //challenge question parameter
    int nr,ng,ny;
    float probnogrn[];
    int drawnr,drawng,drawny;
    float probcolorcombo;
    float probroll;
    float rollo[];
    float probnoblast[];
    int round,bonusadd,bonussub,addflag,subflag;
    float answer;
    int aq[];
    int randq1;
    int diepic[]= {R.drawable.nodice,
            R.drawable.greendice,
            R.drawable.yellowdice,
            R.drawable.reddice,
            R.drawable.greenbrain,
            R.drawable.greenfeet,
            R.drawable.greenblast1,
            R.drawable.yellowbrain,
            R.drawable.yellowfeet,
            R.drawable.yellowblast1,
            R.drawable.redbrain,
            R.drawable.redfeet,
            R.drawable.redblast1
              };
    int bonus[] = {R.drawable.nodice,R.drawable.brain12,R.drawable.brain1,R.drawable.hand};
    int imgid;

    MediaPlayer mp;
    AudioManager audioManager;

    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        audioManager = (AudioManager)  getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,20,0);

        Intent ii = getIntent();
        robot = ii.getIntExtra("robot",-1);
        System.out.println("Game getting robot from main : "+robot);
        stageNo=2;
/*
        prompt="Press draw button to play";
        promptmsgt = (TextView)  findViewById(R.id.prompt);
        promptmsgt.setText(prompt);
        mq1="";
        mq2="";
        mquest1a = (TextView)  findViewById(R.id.mquest1a);
        mquest1a.setText(mq1);
        mquest1b = (TextView)  findViewById(R.id.mquest1b);
        mquest1b.setText(mq2);
        score1t = (TextView)  findViewById(R.id.score1);
        score1t.setText(""+score1);
        score2t = (TextView)  findViewById(R.id.score2);
        score2t.setText(""+score2);


        bonus1img = (ImageView) findViewById(R.id.bonus1);
        bonus1img.setImageResource(R.drawable.calculator);
        bonus1img.setVisibility(View.INVISIBLE);
        bonus2img = (ImageView) findViewById(R.id.bonus2);
        bonus2img.setImageResource(R.drawable.calculator);
        bonus2img.setVisibility(View.INVISIBLE);
        q1img = (ImageView) findViewById(R.id.q1);
        q1img.setImageResource(R.drawable.calculator);
        q1img.setVisibility(View.INVISIBLE);
        q2img = (ImageView) findViewById(R.id.q2);
        q2img.setImageResource(R.drawable.calculator);
        q2img.setVisibility(View.INVISIBLE);
        q3img = (ImageView) findViewById(R.id.q3);
        q3img.setImageResource(R.drawable.calculator);
        q3img.setVisibility(View.INVISIBLE);
        calmimg = (ImageView) findViewById(R.id.calm);
        calmimg.setImageResource(R.drawable.calculator);
        calmimg .setVisibility(View.INVISIBLE);

        gbrnt = (TextView)  findViewById(R.id.gbrainn);
        gbrnt.setText(""+gbrainn);
        ybrnt = (TextView)  findViewById(R.id.ybrainn);
        ybrnt.setText(""+ybrainn);
        rbrnt = (TextView)  findViewById(R.id.rbrainn);
        rbrnt.setText(""+rbrainn);

        gblnt = (TextView)  findViewById(R.id.gblastn);
        gblnt.setText(""+gblastn);
        yblnt = (TextView)  findViewById(R.id.yblastn);
        yblnt.setText(""+yblastn);
        rblnt = (TextView)  findViewById(R.id.rblastn);
        rblnt.setText(""+rblastn);

        player1img = (ImageView)  findViewById(R.id.player1);
        player1img.setImageResource(R.drawable.zombgirlc);
        imgid = R.drawable.zombrobot;
        if (robot!=1) imgid = R.drawable.zombmanc;
        player2img = (ImageView)  findViewById(R.id.player2);
        player2img.setImageResource(imgid);
        //die1=die2=die3=2;
        die1img = (ImageView) findViewById(R.id.die1);
        die1img.setImageResource(diepic[0]);
        die2img = (ImageView) findViewById(R.id.die2);
        die2img.setImageResource(diepic[0]);
        die3img = (ImageView) findViewById(R.id.die3);
        die3img.setImageResource(diepic[0]);
*/

        initialize();

        // Locate the button in activity_main.xml
        Button menub = (Button) findViewById(R.id.menub);
        // Capture button clicks

        menub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                System.out.println("Back to menu button clicked ! ");
                //managerOfSound(1);
                Intent i = getIntent();
                setResult(RESULT_OK,i);
                finish();
            }
        });
        Button questb = (Button) findViewById(R.id.questb);
        // Capture button clicks
        questb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //upgrade dialog box
                AlertDialog.Builder builder=new AlertDialog.Builder(Game.this);
                builder.setCancelable(true);
                builder.setIcon(R.drawable.upgradedialog);
                builder.setTitle("Incoming Call");
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Accept",new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //robot=1;
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Later",new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //robot=1;
                        dialog.dismiss();
                    }

                });

                AlertDialog alert=builder.create();
                alert.show();

            }//OnclickListener end
        });
        Button drawb = (Button) findViewById(R.id.drawbut);
        // Capture button clicks
        drawb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (turnover == 0 && addflag==0) {
                    System.out.println("Draw button clicked ! ");
                    //managerOfSound(1);
                    draw();
                    die1img.setImageResource(diepic[die1]);
                    die2img.setImageResource(diepic[die2]);
                    die3img.setImageResource(diepic[die3]);
                    //qmsgon=0;
                }//if turnover=0 end
            }
        });

        Button rollb = (Button) findViewById(R.id.rollbut);
        // Capture button clicks
        rollb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (turnover==1 && addflag==0) {
                    System.out.println("Roll button clicked ! ");
                    //managerOfSound(1);
                    roll();
                    die1img.setImageResource(diepic[die1]);
                    die2img.setImageResource(diepic[die2]);
                    die3img.setImageResource(diepic[die3]);

                    prompt = "Pass or Play Again.";
                    System.out.println("message: " + prompt);
                    checkblast();

                    gbrnt.setText("" + gbrainn);
                    ybrnt.setText("" + ybrainn);
                    rbrnt.setText("" + rbrainn);
                    gblnt.setText("" + gblastn);
                    yblnt.setText("" + yblastn);
                    rblnt.setText("" + rblastn);

                   // if (brainn>0 && die1%3!=0 && die2%3!=0 && die3%3!=0) managerOfSound(4);
                   // if (die1%3==0 || die2%3==0 || die3%3==0) managerOfSound(5);
                    if (turnover == 3)
                        prompt = "Turn over, Pass only.";
                    promptmsgt.setText(prompt);
                } //if turnover=1 end

            }
        });//roll end
        Button againb = (Button) findViewById(R.id.againbut);
        // Capture button clicks
        againb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (turnover==2) {
                    System.out.println("Again button clicked ! ");
                    //managerOfSound(1);
                   // SoundClipTest("agogothi.wav");
                    randStr.remove(randStr.size()-1);
                    randStr.remove(randStr.size()-1);
                    randStr.remove(randStr.size()-1);
                    System.out.println("randstr after draw : "+randStr);
                    if (die1==5) randStr.add(1);
                    if (die1==8) randStr.add(2);
                    if (die1==11) randStr.add(3);
                    if (die2==5) randStr.add(1);
                    if (die2==8) randStr.add(2);
                    if (die2==11) randStr.add(3);
                    if (die3==5) randStr.add(1);
                    if (die3==8) randStr.add(2);
                    if (die3==11) randStr.add(3);
                    prompt= "Draw dice to continue.";
                    promptmsgt.setText(prompt);
                    System.out.println("message: "+prompt);
                    //qmsg1=qmsg2="";

                    if (player==1) {
                        if (bonusadd==2) score1=score1+1;
                        if (bonussub==-1 && score2>0) score2=score2-1;
                    }
                    else {
                        if (bonusadd==2) score2=score2+1;
                        if (bonussub==-1 && score1>0) score1=score1-1;			}

                    score1t.setText(""+score1);
                    score2t.setText(""+score2);

                    bonusadd=bonussub=0;
                    turnover=0;
                    aq[0]=aq[1]=aq[2]=0;
                    bonus1img.setVisibility(View.INVISIBLE);
                    bonus2img.setVisibility(View.INVISIBLE);
                    q1img.setVisibility(View.INVISIBLE);
                    q2img.setVisibility(View.INVISIBLE);
                    q3img.setVisibility(View.INVISIBLE);
                    calmimg .setVisibility(View.INVISIBLE);
                    qmsgon=0;
                }//if turnover=2 end
            }
        });//again end
        Button passb = (Button) findViewById(R.id.passbut);
        // Capture button clicks
        passb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (turnover>1) {
                    System.out.println("Pass button clicked ! ");
                    //managerOfSound(1);
                   // SoundClipTest("agogothi.wav");
                    if (gameover==1) stageNo=0;
                    if (player==1) {
                        if (blastn<3) score1=score1+brainn;
                        if (bonusadd==2) score1=score1+1;
                        if (bonussub==-1 && score2>0) score2=score2-1;
                        System.out.println("Player 1 score update: "+score1);
                    }

                    if (player==2) {
                        if (blastn<3) score2=score2+brainn;
                        if (bonusadd==2) score2=score2+1;
                        if (bonussub==-1 && score1>0) score1=score1-1;
                        System.out.println("Player2 score update:  "+score2);
                    }
                    score1t.setText(""+score1);
                    score2t.setText(""+score2);
                    if (score1<5 && score2<5) {
                        resetdata();
                        System.out.println("Turn over and switch player");
                        //prompt= "Press draw button to play.";
                    } //if scores not over 13
                    else {
                        imgid = R.drawable.tombgameover;

                        if (score1>4) {
                            prompt= "Player 1 won. Menu.";
                            player2img.setImageResource(imgid);
                        }
                        if (score2>4) {
                            prompt= "Player 2 won. Menu.";
                            player1img.setImageResource(imgid);

                        }
                        //SoundClipTest("tada.wav");
                        //managerOfSound(6);
                        turnover=9;
                        gameover=1;
                        System.out.println("Game Over !");
                    }
                    promptmsgt.setText(prompt);
                    System.out.println("message: "+prompt);
                    //qmsg1=qmsg2="";
                    qmsgon=0;
                    bonusadd=bonussub=0;
                }
            }
        });

    }
    //  ---------------------------------------------------------------
    private void resetdata() {
        //mclicked=false;
        //checked=false;
        turnover=0;
        die1=die2=die3=0;
        blastn=brainn=0;
        gblastn=yblastn=rblastn=gbrainn=ybrainn=rbrainn=0;
        player= player%2 + 1;
        bonusadd=bonussub=addflag=subflag=0;


        System.out.println("player : "+player);
        System.out.println("turnover "+turnover+" blastn "+blastn+" brainn "+brainn);

        java.util.List<Integer> diceStr = new java.util.ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,2,2,2,2,3,3,3));
        randStr = new java.util.ArrayList<Integer>(diceStr);
        Collections.shuffle(randStr);
        System.out.println("ramdomize dice : "+randStr);
        randStrbk=randStr;
        System.out.println("length is : "+randStr.size());
        //qmsg1=qmsg2="";

        questno=0;
        qmsgon=0;
        bonusadd=bonussub=0;
        aq[0]=aq[1]=aq[2]=0;

        prompt="Press draw button to play";
        promptmsgt = (TextView)  findViewById(R.id.prompt);
        promptmsgt.setText(prompt);
        mq1="";
        mq2="";
        mquest1a = (TextView)  findViewById(R.id.mquest1a);
        mquest1a.setText(mq1);
        mquest1b = (TextView)  findViewById(R.id.mquest1b);
        mquest1b.setText(mq2);
        score1t = (TextView)  findViewById(R.id.score1);
        score1t.setText(""+score1);
        score2t = (TextView)  findViewById(R.id.score2);
        score2t.setText(""+score2);


        bonus1img = (ImageView) findViewById(R.id.bonus1);
        bonus1img.setImageResource(R.drawable.calculator);
        bonus1img.setVisibility(View.INVISIBLE);
        bonus2img = (ImageView) findViewById(R.id.bonus2);
        bonus2img.setImageResource(R.drawable.calculator);
        bonus2img.setVisibility(View.INVISIBLE);
        q1img = (ImageView) findViewById(R.id.q1);
        q1img.setImageResource(R.drawable.calculator);
        q1img.setVisibility(View.INVISIBLE);
        q2img = (ImageView) findViewById(R.id.q2);
        q2img.setImageResource(R.drawable.calculator);
        q2img.setVisibility(View.INVISIBLE);
        q3img = (ImageView) findViewById(R.id.q3);
        q3img.setImageResource(R.drawable.calculator);
        q3img.setVisibility(View.INVISIBLE);
        calmimg = (ImageView) findViewById(R.id.calm);
        calmimg.setImageResource(R.drawable.calculator);
        calmimg .setVisibility(View.INVISIBLE);

        gbrnt = (TextView)  findViewById(R.id.gbrainn);
        gbrnt.setText(""+gbrainn);
        ybrnt = (TextView)  findViewById(R.id.ybrainn);
        ybrnt.setText(""+ybrainn);
        rbrnt = (TextView)  findViewById(R.id.rbrainn);
        rbrnt.setText(""+rbrainn);

        gblnt = (TextView)  findViewById(R.id.gblastn);
        gblnt.setText(""+gblastn);
        yblnt = (TextView)  findViewById(R.id.yblastn);
        yblnt.setText(""+yblastn);
        rblnt = (TextView)  findViewById(R.id.rblastn);
        rblnt.setText(""+rblastn);

        player1img = (ImageView)  findViewById(R.id.player1);
        player1img.setImageResource(R.drawable.zombgirlc);
        imgid = R.drawable.zombrobot;
        if (robot!=1) imgid = R.drawable.zombmanc;
        player2img = (ImageView)  findViewById(R.id.player2);
        player2img.setImageResource(imgid);
        die1=die2=die3=2;
        die1img = (ImageView) findViewById(R.id.die1);
        int die11 = diepic[die1];
        die1img.setImageResource(diepic[die1]);
        die2img = (ImageView) findViewById(R.id.die2);
        die2img.setImageResource(diepic[die2]);
        die3img = (ImageView) findViewById(R.id.die3);
        die3img.setImageResource(diepic[die3]);

        //tmsg= "Press draw button to play.";
        if (player==2 && robot==1) {
            brainn=new Random().nextInt(5)+1;  // [0...3] [min = 0, max = 5]
            if (brainn>3) brainn=0;
            System.out.println("Robot brains count this round : "+brainn);
            gbrainn=brainn;
            gbrnt = (TextView)  findViewById(R.id.gbrainn);
            gbrnt.setText(""+gbrainn);
            prompt = "Pass to add " + gbrainn + " to robot score.";
            promptmsgt = (TextView)  findViewById(R.id.prompt);
            promptmsgt.setText(prompt);
            turnover=3;
        }

    } //resetdata method end

    private void initialize() {
        player = 0;
        score1=score2=0;
        turnover=0;
        gameover=0;
        round=1;
        bonusadd=bonussub=addflag=subflag=0;
        aq = new int[]{0, 0, 0};
        java.util.List<Integer> diceStr = new java.util.ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,2,2,2,2,3,3,3));
        randStr = new java.util.ArrayList<Integer>(diceStr);
        Collections.shuffle(randStr);

        System.out.println("ramdomize dice : "+randStr);
        System.out.println("length is : "+randStr.size());

        resetdata();

    }
    private void draw() {

        mquest1a.setText("");
        mquest1b.setText("");
        qmsgon = 0;
        die1 = randStr.get(randStr.size() - 1);
        die2 = randStr.get(randStr.size() - 2);
        die3 = randStr.get(randStr.size() - 3);
        prompt = "Press Roll button to roll dice.";
        promptmsgt.setText(prompt);
             turnover = 1;

        System.out.println("die #1 : " + die1 + " die#2 : " + die2 + " die#3 : " + die3);

    } //method draw end

    private void roll() {

            java.util.List<Integer> grnlist = new java.util.ArrayList<Integer>(Arrays.asList(4, 4, 4, 5, 6, 6));
            java.util.List<Integer> yellist = new java.util.ArrayList<Integer>(Arrays.asList(7, 7, 8, 8, 9, 9));
            java.util.List<Integer> redlist = new java.util.ArrayList<Integer>(Arrays.asList(10, 11, 11, 11, 12, 12));
            //qmsg1=qmsg2="";

            mquest1a.setText("");
            mquest1b.setText("");
            qmsgon = 0;

            switch (die1) {
                case 1:
                    Collections.shuffle(grnlist);
                    die1 = grnlist.get(0);
                    break;
                case 2:
                    Collections.shuffle(yellist);
                    die1 = yellist.get(0);
                    break;
                case 3:
                    Collections.shuffle(redlist);
                    die1 = redlist.get(0);
                    break;
            }

            switch (die2) {
                case 1:
                    Collections.shuffle(grnlist);
                    die2 = grnlist.get(0);
                    break;
                case 2:
                    Collections.shuffle(yellist);
                    die2 = yellist.get(0);
                    break;
                case 3:
                    Collections.shuffle(redlist);
                    die2 = redlist.get(0);
                    break;
            }
            switch (die3) {
                case 1:
                    Collections.shuffle(grnlist);
                    die3 = grnlist.get(0);
                    break;
                case 2:
                    Collections.shuffle(yellist);
                    die3 = yellist.get(0);
                    break;
                case 3:
                    Collections.shuffle(redlist);
                    die3 = redlist.get(0);
                    break;
            }
            System.out.println("dice rolled : " + die1 + "," + die2 + "," + die3);

    } //method roll end

    private void checkblast() {

        if (die1==4) gbrainn++;
        if (die1==7) ybrainn++;
        if (die1==10) rbrainn++;

        if (die1==6) gblastn++;
        if (die1==9) yblastn++;
        if (die1==12) rblastn++;

        if (die2==4) gbrainn++;
        if (die2==7) ybrainn++;
        if (die2==10) rbrainn++;

        if (die2==6) gblastn++;
        if (die2==9) yblastn++;
        if (die2==12) rblastn++;

        if (die3==4) gbrainn++;
        if (die3==7) ybrainn++;
        if (die3==10) rbrainn++;

        if (die3==6) gblastn++;
        if (die3==9) yblastn++;
        if (die3==12) rblastn++;

        brainn=gbrainn+ybrainn+rbrainn;
        blastn=gblastn+yblastn+rblastn;

        if (blastn>2) {
            turnover=3;   //pass only
            System.out.println("turnover, pass only  brain/blast"+brainn+"/"+blastn);
        }
        else {
            turnover=2;
            System.out.println("OK to play again or pass brain/blast"+brainn+"/"+blastn);
        }
    }//checkblast method end

    //Find probability of drawing 0,1,2,3 green dice before drawing
    public void Q1() {
        nr=ng=ny=0;
        probnogrn = new float[]{0, 0, 0, 0};
        for (int i=0; i<randStr.size(); i++) {
            //System.out.println("randStr element :"+randStr.get(i));
            if (randStr.get(i)==1) ng++;
            if (randStr.get(i)==2) ny++;
            if (randStr.get(i)==3) nr++;
        }//i loop end
        System.out.println("color dice count :"+ng+","+ny+","+nr);
        if (ng>0) {
            probnogrn[0]= (float) ((nr+ny)*(nr+ny-1)*(nr+ny-2)/6)/((nr+ng+ny)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
            probnogrn[1]= (float) (ng*(nr+ny)*(nr+ny-1)/2)/((nr+ng+ny)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
            probnogrn[2]= (float) (ng*(ng-1)*(nr+ny)/2)/((nr+ng+ny)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
            probnogrn[3]= (float) ((ng)*(ng-1)*(ng-2)/6)/((nr+ng+ny)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
            System.out.println("Prob of "+randq1+" green :"+probnogrn[0]+","+probnogrn[1]+","+probnogrn[2]+","+probnogrn[3]);
        }
        answer=probnogrn[randq1];
    }//mehtod q1 end
    //Find probability of drawn color dice combo after drawing
    public void Q2() {
        nr=ng=ny=0;
        for (int i=0; i<randStrbk.size(); i++) {
            //System.out.println("randStr element :"+randStr.get(i));
            if (randStrbk.get(i)==1) ng++;
            if (randStrbk.get(i)==2) ny++;
            if (randStrbk.get(i)==3) nr++;
        }//i loop end

        drawnr=drawng=drawny=0;
        for (int i=0; i<3; i++) {

            switch (randStrbk.get(randStrbk.size()-1-i)) {
                case 1:
                    drawng++;
                    break;
                case 2:
                    drawny++;
                    break;
                case 3:
                    drawnr++;
                    break;
            } //switch end
        } //iloop end
        System.out.println("color dice count :"+ng+","+ny+","+nr);
        System.out.println("color dice combo :"+drawng+","+drawny+","+drawnr);
        int pdg,pdr,pdy;
        pdg=pdr=pdy=1;
        switch (drawng) {
            case 0:
                pdg=1;
                break;
            case 1:
                pdg=ng;
                break;
            case 2:
                pdg=ng*(ng-1)/2;
                break;
            case 3:
                pdg=ng*(ng-1)*(ng-2)/6;
                break;
        }//switch drawng end
        switch (drawny) {
            case 0:
                pdy=1;
                break;
            case 1:
                pdy=ny;
                break;
            case 2:
                pdy=ny*(ny-1)/2;
                break;
            case 3:
                pdy=ny*(ny-1)*(ny-2)/6;
                break;
        }//switch drawny end
        switch (drawnr) {
            case 0:
                pdr=1;
                break;
            case 1:
                pdr=nr;
                break;
            case 2:
                pdr=nr*(nr-1)/2;
                break;
            case 3:
                pdr=nr*(nr-1)*(nr-2)/6;
                break;

        }//switch drawnr end
        probcolorcombo= (float)pdg*pdy*pdr/((nr+ng+ny)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
        System.out.println("prob of drawn color combo :"+probcolorcombo);
        answer= probcolorcombo;
    }//mehtod q2 end

    //before rolling dice probabilities of 0,1,2,3 blasts based on drawn color combo
    public void Q3() {

        float probrblast= (float) 1/2;
        float probyblast= (float) 1/3;
        float probgblast= (float) 1/6;
        probnoblast = new float[]{0, 0, 0, 0};
        nr=ng=ny=0;
        for (int i=0; i<randStrbk.size(); i++) {
            //System.out.println("randStr element :"+randStr.get(i));
            if (randStrbk.get(i)==1) ng++;
            if (randStrbk.get(i)==2) ny++;
            if (randStrbk.get(i)==3) nr++;
        }//i loop end
        drawnr=drawng=drawny=0;
        for (int i=0; i<3; i++) {

            switch (randStrbk.get(randStrbk.size()-1-i)) {
                case 1:
                    drawng++;
                    break;
                case 2:
                    drawny++;
                    break;
                case 3:
                    drawnr++;
                    break;
            } //switch end
        } //iloop end
        float ptemp;
        float Prob1rblast,Prob1yblast,Prob1gblast;
        //System.out.println("color dice count :"+ng+","+ny+","+nr);
        //System.out.println("color dice combo :"+drawng+","+drawny+","+drawnr);
//case of 0 blasts
        ptemp = (float) Math.pow((double) (1-probrblast),(double) drawnr);
        probnoblast[0]= ptemp;
        ptemp = (float) Math.pow((double) (1-probyblast),(double) drawny);
        probnoblast[0] = (float) probnoblast[0]*ptemp;
        ptemp = (float) Math.pow((double) (1-probgblast),(double) drawng);
        probnoblast[0] = (float) probnoblast[0]*ptemp;
        //probnoblast[0] = (float) (Math.pow((double)(1-probrblast),(double)drawnr))*(Math.pow((double)(1-probyblast),(double) drawny))*(Math.pow((double)(1-probgblast),(double) drawng));
        System.out.println("prob of no blast :"+probnoblast[0]);

//case of 1 blast
        //case:  RGY dice
        if (drawny==1 && drawng==1 && drawnr==1) {
            Prob1rblast= (float) (probrblast)*(1-probyblast)*(1-probgblast);
            Prob1yblast= (float) (1-probrblast)*(probyblast)*(1-probgblast);
            Prob1gblast= (float) (1-probrblast)*(1-probyblast)*(probgblast);
            probnoblast[1] = Prob1rblast+Prob1yblast+Prob1gblast;
        } //if rgy end
        //case: RRY dice
        if (drawny==1 && drawnr==2) {
            Prob1rblast= (float) (probrblast)*(1-probrblast)*(1-probyblast)*2;
            Prob1yblast= (float) (1-probrblast)*(1-probrblast)*(probyblast);
            probnoblast[1] = Prob1rblast+Prob1yblast;
        } //if rry end
        //case: RRG dice
        if (drawng==1 && drawnr==2) {
            Prob1rblast= (float) (probrblast)*(1-probrblast)*(1-probgblast)*2;
            Prob1gblast= (float) (1-probrblast)*(1-probrblast)*(probgblast);
            probnoblast[1] = Prob1rblast+Prob1gblast;
        } //if rrg end
        //case: RRR dice
        if (drawnr==3) {
            Prob1rblast= (float) (probrblast)*(1-probrblast)*(1-probrblast)*3;
            probnoblast[1] = Prob1rblast;
        } //if rrr end
        //case: YYG dice
        if (drawny==2 && drawng==1) {
            Prob1yblast= (float) (probyblast)*(1-probyblast)*(1-probgblast)*2;
            Prob1gblast= (float) (1-probyblast)*(1-probyblast)*(probgblast);
            probnoblast[1] = Prob1yblast+Prob1gblast;
        } //if yyg end
        //case: YYR dice
        if (drawny==2 && drawnr==1) {
            Prob1yblast= (float) (probyblast)*(1-probyblast)*(1-probrblast)*2;
            Prob1rblast= (float) (1-probyblast)*(1-probyblast)*(probrblast);
            probnoblast[1] = Prob1rblast+Prob1yblast;
        } //if yyr end
        //case: YYY dice
        if (drawny==3) {
            Prob1yblast= (float) (probyblast)*(1-probyblast)*(1-probyblast)*3;
            probnoblast[1] = Prob1yblast;
        } //if yyy end
        //case: GGR dice
        if (drawng==2 && drawnr==1) {
            Prob1gblast= (float) (probgblast)*(1-probgblast)*(1-probrblast)*2;
            Prob1rblast= (float) (1-probgblast)*(1-probgblast)*(probrblast);
            probnoblast[1] = Prob1rblast+Prob1gblast;
        } //if ggr end
        //case: GGY dice
        if (drawny==1 && drawng==2) {
            Prob1gblast= (float) (probgblast)*(1-probgblast)*(1-probyblast)*2;
            Prob1yblast= (float) (1-probgblast)*(1-probgblast)*(probyblast);
            probnoblast[1] = Prob1yblast+Prob1gblast;
        } //if ggy end
        //case: GGG dice
        if (drawng==3) {
            Prob1gblast= (float) (probgblast)*(1-probgblast)*(1-probgblast)*3;
            probnoblast[1] = Prob1gblast;
        } //if ggg end
//case of 3 blasts
        ptemp = (float) Math.pow((double) probrblast,(double) drawnr);
        probnoblast[3]= ptemp;
        ptemp = (float) Math.pow((double) probyblast,(double) drawny);
        probnoblast[3] = (float) probnoblast[3]*ptemp;
        ptemp = (float) Math.pow((double) probgblast,(double) drawng);
        probnoblast[3] = (float) probnoblast[3]*ptemp;
//case of 2 blasts
        probnoblast[2]= (float) (1-probnoblast[0]-probnoblast[1]-probnoblast[3]);
        System.out.println("Prob of no blast :"+probnoblast[0]+","+probnoblast[1]+","+probnoblast[2]+","+probnoblast[3]);

    }//method q3 end

    //after rolling probability of rolled outcome
    public void Q4() {

        rollo = new float[]{0,0,0,0,0,0,0,0,0,0,0,0,0};

        //rollo[0]=rollo[1]=rollo[2]=rollo[3]=(float) 0.0 ;
        rollo[0]=(float) 0*1;
        rollo[1]=(float) 0*1;
        rollo[2]=(float) 0*1;
        rollo[3]=(float) 0*1;
        rollo[4]=(float) 1/2; //green brain outcome
        rollo[5]=(float) 1/3; //green feet outcome
        rollo[6]=(float) 1/6; //green blast outcome
        rollo[7]=(float) 1/3; //yellow brain outcome
        rollo[8]=(float) 1/3; //yellow feet outcome
        rollo[9]=(float) 1/3; //yellow blast outcome
        rollo[10]=(float) 1/6; //red brain outcome
        rollo[11]=(float) 1/3; //red feet outcome
        rollo[12]=(float) 1/2; //red blast outcome

        Q2();
        //die1=die2=die3=4;
        System.out.println("Rolled outcome combo : "+die1+","+die2+","+die3);
        probroll = (float) probcolorcombo*rollo[die1]*rollo[die2]*rollo[die3];
        System.out.println("prob of rolled outcome combo :"+probroll);
        answer=probroll;
    }//mehtod q4 end
    /*
    protected void managerOfSound(int snd) {
        if (mp !=null) {
           mp.reset();
           mp.release();
        }
        switch (snd) {
            case 1:
                mp = MediaPlayer.create(this, R.raw.agogothi);
                break;
            case 2:
                mp = MediaPlayer.create(this, R.raw.alienbti);
                break;
            case 3:
                mp = MediaPlayer.create(this, R.raw.uhoh);
                break;
            case 4:
                mp = MediaPlayer.create(this, R.raw.zombie);
                break;
            case 5:
                mp = MediaPlayer.create(this, R.raw.gunshot_3);
                break;
            case 6:
                mp = MediaPlayer.create(this, R.raw.tada);
                break;

        }//switch end
    }//sound mehtod end
    */
}