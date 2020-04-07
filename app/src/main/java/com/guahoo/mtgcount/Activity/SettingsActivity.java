package com.guahoo.mtgcount.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.guahoo.mtgcount.R;

public class SettingsActivity extends AppCompatActivity {
    Button backButton;
    ImageButton player2Button;
    ImageButton player3Button;
    ImageButton player4Button;



    EditText textLifeScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LOCKED );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled( false );
            getSupportActionBar().hide();
        }
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        backButton = findViewById( R.id.beginGameButton );
        textLifeScore = findViewById( R.id.textLifeScore );
        player2Button=findViewById( R.id.player2Button );
        player3Button=findViewById( R.id.player3Button );
        player4Button=findViewById( R.id.player4Button );


        backButton.setOnClickListener( v -> {
            if (player2Button.isSelected()){
                    Intent intentCountActivity =new Intent( getApplicationContext(), CountActivity.class );
                    intentCountActivity.putExtra("lifeScore",Integer.valueOf( textLifeScore.getText().toString()) );
                    startActivity( intentCountActivity );
                }
            else if (player3Button.isSelected()) {
                    Intent intentCountActivity =new Intent( getApplicationContext(), Count3PlayerActivity.class );
                    intentCountActivity.putExtra("lifeScore",Integer.valueOf( textLifeScore.getText().toString()) );
                    startActivity( intentCountActivity );
                }
            else if (player4Button.isSelected()) {
                Intent intentCountActivity =new Intent( getApplicationContext(), CountPlayer4Activity.class );
                intentCountActivity.putExtra("lifeScore",Integer.valueOf( textLifeScore.getText().toString()) );
                startActivity( intentCountActivity );
            }
        } );


        player2Button.setBackgroundResource ( R.drawable.frame );
        player2Button.setSelected(true);
        PlayerNumber( player2Button,player3Button,player4Button);
        PlayerNumber( player3Button,player2Button,player4Button);
        PlayerNumber( player4Button,player3Button,player2Button);


    }


    void PlayerNumber(final ImageButton player2Button, final ImageButton player3Button,final ImageButton player4Button) {
        player2Button.setOnClickListener( v -> {

            player2Button.setBackgroundResource (R.drawable.frame );
            player2Button.setSelected(true);
            player3Button.setBackgroundResource( 0 );
            player3Button.setSelected( false );
            player4Button.setBackgroundResource( 0 );
            player4Button.setSelected( false );


        } );


    }
}
