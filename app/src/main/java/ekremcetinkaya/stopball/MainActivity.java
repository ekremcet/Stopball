package ekremcetinkaya.stopball;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ekremcetinkaya.chronoball.R;

public class MainActivity extends AppCompatActivity {
    final static int GOAL       = 00;
    final static int PENALTY    = 99;
    final static int FOUL       = 75;
    final static int YELLOWCARD = 45;
    final static int REDCARD    = 55;
    final static int FINALCOUNT = 3;
    Button butnstart;
    TextView time;
    TextView player1;
    TextView player2;
    TextView durum;
    TextView sira;
    ImageView p1kart;
    ImageView p2kart;
    MediaPlayer golSes;
    MediaPlayer kartSes;
    MediaPlayer kronoSes;

    /* Kronometre kısmı */
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int startPause = 1;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;
    Handler handler = new Handler();
    /*------------------*/
    int activePlayer = 1;
    Player p1 = new Player();
    Player p2 = new Player();
    int goalRange = 1;
    boolean isPenalty = false;
    String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butnstart = (Button)   findViewById(R.id.button);
        time      = (TextView) findViewById(R.id.zaman);
        player1   = (TextView) findViewById(R.id.player1);
        player2   = (TextView) findViewById(R.id.player2);
        durum     = (TextView) findViewById(R.id.durum);
        sira     = (TextView) findViewById(R.id.sira);

        p1kart    = (ImageView) findViewById(R.id.p1kart);
        p2kart    = (ImageView) findViewById(R.id.p2kart);

        p1kart.setImageResource(R.drawable.boskart);
        p2kart.setImageResource(R.drawable.boskart);

        Typeface playerFont = Typeface.createFromAsset(getAssets(),"fonts/zekton.ttf");
        Typeface timeFont = Typeface.createFromAsset(getAssets(),"fonts/ucbes.ttf");
        player1.setTypeface(playerFont);
        player2.setTypeface(playerFont);
        time.setTypeface(timeFont);
        butnstart.setTypeface(playerFont);
        kronoSes = MediaPlayer.create(this,R.raw.krono);
        golSes = MediaPlayer.create(this,R.raw.gol);
        kartSes = MediaPlayer.create(this,R.raw.duduk);

    }

    @Override
    public void onBackPressed(){
        Intent mainMenu = new Intent(getApplicationContext(),anaSayfa.class);
        startActivity(mainMenu);
        finish();
    }

    public void buttonOnClick(View v) {
        kronoSes.start();
        sira.setText("Match is going on");
        if (startPause == 1) {
            butnstart.setText("Shoot");
            starttime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimer, 0);
            startPause = 0;
        } else { /* durdurduk burada*/
            butnstart.setText("Dribble");
            timeSwapBuff += timeInMilliseconds;
            handler.removeCallbacks(updateTimer);
            startPause = 1;
            checkMilisecs(activePlayer);
            if(activePlayer == 1) {
                sira.setText("Player 2 has the ball");
                activePlayer = 2;
            }
            else {
                sira.setText("Player 1 has the ball");
                activePlayer = 1;
            }
        }
    }
    public void checkMilisecs(int p){
        durum.setText("");
        if(isPenalty){
            isPenalty = false;
        }
        else
            goalRange = 1;
        if(p == 1) {
            if (milliseconds < GOAL + goalRange) {
                durum.setText("GOOOAAAL");
                golSes.start();
                p1.score();
                goalRange = 1;
                if(p1.score == FINALCOUNT){
                    gameOverMenu(1);
                }
            }
            else if(milliseconds == YELLOWCARD){
                kartSes.start();
                if(p1.yellowCard < 2) {
                    durum.setText("Yellow card to PLAYER 1 !");
                    p1.showYellow();
                    p1kart.setImageResource(R.drawable.yellow);
                }
                else {
                    durum.setText("Second yellow card..it's a red card to PLAYER 1 !!");
                    p1.showRed();
                    p1kart.setImageResource(R.drawable.yellow1);
                    penalty(1);
                }
            }
            else if(milliseconds == REDCARD){
                durum.setText("Red Card to PLAYER 2 !!!");
                p1.showRed();
                kartSes.start();
                p1kart.setImageResource(R.drawable.red);
                penalty(1);
            }
            else if(milliseconds == PENALTY) {
                penalty(2);
            }
            else if(milliseconds == FOUL){
                foul(2);
            }
        }
        else {
            /* player 2 */
            if (milliseconds < GOAL + goalRange) {
                durum.setText("GOOOAAAL");
                golSes.start();
                p2.score();
                goalRange = 1;
                if(p2.score == FINALCOUNT){
                    gameOverMenu(2);
                }
            }
            else if(milliseconds == YELLOWCARD){
                kartSes.start();
                if(p2.yellowCard < 2) {
                    durum.setText("Yellow card to PLAYER 2 !");
                    p2.showYellow();
                    p2kart.setImageResource(R.drawable.yellow);
                }
                else {
                    durum.setText("Second yellow card..it's a red card to PLAYER 2 !!");
                    p2.showRed();
                    p2kart.setImageResource(R.drawable.yellow1);
                    penalty(2);
                }
            }
            else if(milliseconds == REDCARD){
                durum.setText("Red Card to PLAYER 2 !!!");
                p2.showRed();
                p2kart.setImageResource(R.drawable.red);
                kartSes.start();
                penalty(2);
            }
            else if(milliseconds == PENALTY) {
                penalty(1);
            }
            else if(milliseconds == FOUL){
                foul(1);
            }
        }
        String p1str = "";
        String p2str = "";
        p1str += p1.score;
        p2str += p2.score;
        player1.setText(p1str);
        player2.setText(p2str);
    }

    public void foul(int p){

    }

    public void penalty(int p){
        if(p == 1) {
            durum.setText("PLAYER 2 will take a PENALTY");
            activePlayer = 1;
            if(p2.redCard == 1){
                p2kart.setImageResource(R.drawable.boskart);
                p2.redCard = 0;
            }
        }
        else {
            durum.setText("PLAYER 1 will take a PENALTY");
            activePlayer = 2;
            if(p1.redCard == 1){
                p1kart.setImageResource(R.drawable.boskart);
                p1.redCard = 0;
            }
        }
        goalRange = 31;
        isPenalty = true;
    }

    public void gameOverMenu(int playerWon){
        winner = "Player " + playerWon + " won !";
        durum.setText("Game Over, " + winner);
        butnstart.setText("END MATCH");
        butnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(getApplicationContext(),anaSayfa.class);
                startActivity(mainMenu);
                finish();
            }
        });
    }
    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) ((updatedtime /10) % 100);
            String text = "" + String.format("%02d", mins)+ ":" + String.format("%02d", secs) + ":"
                    + String.format("%02d", milliseconds);
            time.setText(text);
            handler.postDelayed(this, 0);
        }
    };
}
