package ekremcetinkaya.stopball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ekremcetinkaya.chronoball.R;

public class helpMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);
    }
    @Override
    public void onBackPressed(){
        Intent mainMenu = new Intent(getApplicationContext(),anaSayfa.class);
        startActivity(mainMenu);
        finish();
    }

    public void backButton(View v){
        Intent main = new Intent(getApplicationContext(),anaSayfa.class);
        startActivity(main);
        finish();
    }

    public void playButtonHelp(View v){
        Intent playMenu = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(playMenu);
        finish();
    }


}
