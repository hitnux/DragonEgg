package hb.com.dragonegg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    LinearLayout satir1,satir2,satir3,satir4,satir5;
    ImageButton imgbtn[]=new ImageButton[30];
    int a[] = {R.drawable.kahve, R.drawable.mavi, R.drawable.yesil, R.drawable.mor, R.drawable.turkuaz, R.drawable.kirmizi, R.drawable.siyah};
    int skor=0;
    TextView skortext,time;
    CountDownTimer anim,timer;
    private final int GAME_OVER_TİME = 120000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        element();
        randomColor();
        EggChange();
        kontrol();
        setTimetext();
        time();
    }
    void setTimetext(){
        final int[] de = {0};
        timer=new CountDownTimer(GAME_OVER_TİME, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText(millisUntilFinished / 1000 + "");
                if(de[0] %2==0){
                    kontrol();
                }
                de[0]++;
            }

            public void onFinish() {
                time.setText("Oyun Bitti");
            }

        }.start();
    }
    void time(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,GameOverActivity.class);
                mainIntent.putExtra("skor",skor);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
                anim.cancel();
                timer.cancel();
            }
        }, GAME_OVER_TİME);
    }
    //oyun süresi
    //skor kayıt
    void saveScor(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Skorum",skor); //int değer ekleniyor
        editor.apply(); //Kayıt
    }
    //skor kayıt
    void element() {
        skortext = (TextView)findViewById(R.id.skortext);
        skortext.setText("Skor: "+skor);
        time = (TextView)findViewById(R.id.time);
        satir1 = (LinearLayout) findViewById(R.id.line1);
        satir2 = (LinearLayout) findViewById(R.id.line2);
        satir3 = (LinearLayout) findViewById(R.id.line3);
        satir4 = (LinearLayout) findViewById(R.id.line4);
        satir5 = (LinearLayout) findViewById(R.id.line5);
        imgbtn[0]=(ImageButton)findViewById(R.id.imgbtn0);
        imgbtn[0].getBackground().setAlpha(128);
        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) imgbtn[0].getLayoutParams();
        for(int i=1;i<30;i++) {
            imgbtn[i]= new ImageButton(this);
            imgbtn[i].setImageResource(R.drawable.kahve);
            imgbtn[i].setBackgroundResource(R.drawable.back);
            imgbtn[i].getBackground().setAlpha(128);
            imgbtn[i].setScaleType(ImageView.ScaleType.FIT_XY);
            imgbtn[i].setLayoutParams(params);
            if(i<6) {
                satir1.addView(imgbtn[i]);
            }else if(i<12){
                satir2.addView(imgbtn[i]);
            }else if(i<18){
                satir3.addView(imgbtn[i]);
            }else if(i<24){
                satir4.addView(imgbtn[i]);
            }else{
                satir5.addView(imgbtn[i]);
            }
        }
    }
    void randomColor() {
        for (int c = 0; c < imgbtn.length; c++) {
            Random r = new Random();
            int i1 = (r.nextInt(6));
            imgbtn[c].setImageResource(a[i1]);
            imgbtn[c].setTag(a[i1]);
        }
    }
    void EggChange() {
        for(int d=0;d<30;d++) {
            final int finalD = d;
            imgbtn[d].setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
                public void onSwipeTop() {
                    if(finalD>5){
                        eggchange_color(finalD, finalD-6);
                    }
                }
                public void onSwipeRight() {
                    if(finalD<29 && finalD!=5 && finalD!=11 && finalD!=17 && finalD!=23){
                        eggchange_color(finalD, finalD+1);
                    }

                }
                public void onSwipeLeft() {
                    if(finalD>0 && finalD!=6 && finalD!=12 && finalD!=18 && finalD!=24){
                        eggchange_color(finalD, finalD-1);
                    }
                }
                public void onSwipeBottom() {
                    if(finalD<24){
                        eggchange_color(finalD, finalD+6);
                    }
                }
            });
        }

    }
    void eggchange_color(final int matrisno, final int kontrol){
        imgbtn[matrisno].setImageResource((int)imgbtn[kontrol].getTag());
        imgbtn[kontrol].setImageResource((int)imgbtn[matrisno].getTag());
        Object tag=imgbtn[matrisno].getTag();
        imgbtn[matrisno].setTag(imgbtn[kontrol].getTag());
        imgbtn[kontrol].setTag(tag);
        kontrol();
    }

    void kontrol(){
        int var;
        for(var=1;var<29;var++) {
            if (imgbtn[var].getTag().equals(imgbtn[var + 1].getTag()) && imgbtn[var].getTag().equals(imgbtn[var - 1].getTag()) && var != 5 && var != 11 && var != 17 && var != 23 && var != 6 && var != 12 && var != 18 && var != 24) {
                final int finalVar1 = var;
                anim= new CountDownTimer(700, 1) {

                    public void onTick(long millisUntilFinished) {
                            imgbtn[finalVar1].setBackgroundResource(R.drawable.bomb);
                            imgbtn[finalVar1 + 1].setBackgroundResource(R.drawable.bomb);
                            imgbtn[finalVar1 - 1].setBackgroundResource(R.drawable.bomb);
                    }
                    public void onFinish() {
                        skor += 5;
                        Random r = new Random();
                        final int i1 = (r.nextInt(5));
                        imgbtn[finalVar1].setTag(a[i1 + 1]);
                        imgbtn[finalVar1 + 1].setTag(a[i1]);
                        imgbtn[finalVar1 - 1].setTag(a[i1]);
                        imgbtn[finalVar1].setImageResource(a[i1+1]);
                        imgbtn[finalVar1 + 1].setImageResource(a[i1]);
                        imgbtn[finalVar1 - 1].setImageResource(a[i1]);
                        imgbtn[finalVar1].setBackgroundResource(R.drawable.back);
                        imgbtn[finalVar1 +1].setBackgroundResource(R.drawable.back);
                        imgbtn[finalVar1 -1].setBackgroundResource(R.drawable.back);
                        imgbtn[finalVar1].getBackground().setAlpha(128);
                        imgbtn[finalVar1 +1].getBackground().setAlpha(128);
                        imgbtn[finalVar1 -1].getBackground().setAlpha(128);
                    }

                }.start();
            }
            if (var > 5 && var < 24 && imgbtn[var].getTag().equals(imgbtn[var + 6].getTag()) && imgbtn[var].getTag().equals(imgbtn[var - 6].getTag())) {
                final int finalVar = var;
                anim= new CountDownTimer(1000, 1) {

                    public void onTick(long millisUntilFinished) {
                            imgbtn[finalVar - 6].setBackgroundResource(R.drawable.bomb);
                            imgbtn[finalVar + 6].setBackgroundResource(R.drawable.bomb);
                            imgbtn[finalVar].setBackgroundResource(R.drawable.bomb);
                    }

                    public void onFinish() {
                        skor += 5;
                        Random r1 = new Random();
                        final int i2 = (r1.nextInt(5));
                        imgbtn[finalVar].setTag(a[i2 + 1]);
                        imgbtn[finalVar + 6].setTag(a[i2]);
                        imgbtn[finalVar - 6].setTag(a[i2]);
                        imgbtn[finalVar].setImageResource(a[i2+1]);
                        imgbtn[finalVar + 6].setImageResource(a[i2]);
                        imgbtn[finalVar - 6].setImageResource(a[i2]);
                        imgbtn[finalVar -6].setBackgroundResource(R.drawable.back);
                        imgbtn[finalVar +6].setBackgroundResource(R.drawable.back);
                        imgbtn[finalVar].setBackgroundResource(R.drawable.back);
                        imgbtn[finalVar].getBackground().setAlpha(128);
                        imgbtn[finalVar +6].getBackground().setAlpha(128);
                        imgbtn[finalVar -6].getBackground().setAlpha(128);
                    }

                }.start();
            }
            //skor kontrol
            skortext.setText("Skor: " + skor);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int yuksekskor = preferences.getInt("Skorum", 0);
            if (skor > yuksekskor) {
                saveScor();
            }
            //skor kontrol
        }
    }
}
