package hb.com.dragonegg;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    ImageView animation;
    Button play;
    TextView enyuksek,skor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        //tekrar oyna
        play=(Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playintent = new Intent(GameOverActivity.this,MainActivity.class);
                GameOverActivity.this.startActivity(playintent);
            }
        });
        //tekrar oyna
        //animasyon
        animation=(ImageView)findViewById(R.id.imageView);
        animation.animate() .translationY(300).setDuration(1000) .setStartDelay(100);
        //animasyon
        //skor kontrol
        enyuksek = (TextView)findViewById(R.id.enyuksek);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int yuksekskor = preferences.getInt("Skorum", 0);
        enyuksek.setText(""+yuksekskor);
        skor = (TextView)findViewById(R.id.skor);
        Bundle bundle = getIntent().getExtras();
        int skorun=bundle.getInt("skor");
        skor.setText(""+skorun);
        //skor kontrol
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Çıkmak istediğine emin misin?") //setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        System.exit(0);
                    }
                }).setNegativeButton("Hayır", null).show();
    }
}
