package ekremcetinkaya.stopball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import ekremcetinkaya.chronoball.R;

public class anaSayfa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);

        AdView adView = (AdView) findViewById(R.id.reklam);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
    }


    public void exitButton(View v){
        Intent mainMenu = new Intent(getApplicationContext(),anaSayfa.class);
        finish();
        System.exit(0);
    }

    public void playButton(View v){
        Intent playIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(playIntent);
        finish();
    }

    public void howToButton(View v){
        Intent helpIntent = new Intent(getApplicationContext(),helpMenu.class);
        startActivity(helpIntent);
        finish();
    }
}
