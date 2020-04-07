package com.guahoo.mtgcount.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guahoo.mtgcount.Utils.ButtonHighlighterOnTouchListener;
import com.guahoo.mtgcount.Utils.CountButtonAction;
import com.guahoo.mtgcount.DataBase.LifeDb;
import com.guahoo.mtgcount.DataBase.LifeScoreEntity;
import com.guahoo.mtgcount.R;
import com.guahoo.mtgcount.DataBase.RealmHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public abstract class BaseCountActivity extends AppCompatActivity {

    ImageButton MinusButtons[];
    ImageButton PlusButtons[];
    TextView NumberCounts[];
    ImageButton cubicButton;
    Button settingsButton;
    SharedPreferences spref;
    Realm realm;
    String prefsCounts[];
    ArrayList <Integer> lifeScores;
    Button saveButton;


    protected void initRes() {

    }


    protected void init(int numberPlayers) {
        Realm.init( this );
        RealmConfiguration config=new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        realm=Realm.getInstance( config );

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LOCKED );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled( false );
            getSupportActionBar().hide();
        }

    }


    void setRenewButton() {
        Vibrator vibrator=(Vibrator) getSystemService( Context.VIBRATOR_SERVICE );
        ImageButton renewButton=findViewById( R.id.renewButton );
        ImageView cubicView1=findViewById( R.id.cubicView1 );
        ImageView cubicView2=findViewById( R.id.cubicView2 );
        settingsButton=findViewById (R.id.settingsButton );
        saveButton=findViewById ( R.id.buttonSave );


        renewButton.setOnLongClickListener( v -> {
            for (int i=0; i < PlusButtons.length; ++i) {

                if (NumberCounts[i].getVisibility() == INVISIBLE) {
                    cubicView1.setVisibility( INVISIBLE );
                    cubicView2.setVisibility( INVISIBLE );
                    MinusButtons[i].setVisibility( VISIBLE );
                    PlusButtons[i].setVisibility( VISIBLE );
                    NumberCounts[i].setVisibility( VISIBLE );
                    saveButton.setVisibility ( VISIBLE );
                    settingsButton.setVisibility ( VISIBLE );
                    renewButton.setImageResource ( R.drawable.renew );
                    vibrator.vibrate( 50 );

                } else {
                    Intent intent=getIntent();
                    int lifeScore;
                    if (intent.hasExtra( "lifeScore" )) {
                        Bundle extras=getIntent().getExtras();
                        assert extras != null;
                        lifeScore=(int) extras.get( "lifeScore" );
                    }else lifeScore=20;
                    spref=BaseCountActivity.this.getApplicationContext().getSharedPreferences( prefsCounts[i], MODE_PRIVATE );
                    SharedPreferences.Editor editor=spref.edit();
                    editor.putInt( prefsCounts[i], (lifeScore) );
                    editor.apply();
                    NumberCounts[i].setText( String.valueOf( lifeScore ) );
                    vibrator.vibrate( 50 );
                }
            }
            return true;
        } );

    }

    void setRenewButtonShortClick() {
        ImageButton renewButton=findViewById( R.id.renewButton );
        renewButton.setOnClickListener( v -> {
            Toast toast=Toast.makeText( getApplicationContext(), "Hold to reset", Toast.LENGTH_SHORT );
            toast.show();
        } );
    }

    void setSaveButton() {

    }


    protected void displayInputDialog(String activityName) {


        final Dialog d=new Dialog( this );


        d.setTitle( "Saving" );
        d.setContentView( R.layout.input_dialog );
        Button saveBtn=d.findViewById( R.id.saveBtn );
        EditText nameeditTxt=d.findViewById( R.id.nameEditText );


        Date currentTime=java.util.Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat( "dd-MM-yyyy", Locale.ENGLISH );
        final String dateString=dateFormat.format( currentTime );


        saveBtn.setOnClickListener( v -> {
            LifeDb s=new LifeDb();
            s.setName( nameeditTxt.getText().toString() );
            lifeScores=new ArrayList <>();
            for (int i=0; i < PlusButtons.length; ++i) {
                spref=getApplicationContext().getSharedPreferences( prefsCounts[i], MODE_PRIVATE );
                lifeScores.add( spref.getInt( prefsCounts[i], 0 ) );
            }
            for (int lifeNr : lifeScores) {
                s.getLifeScores().add( new LifeScoreEntity( lifeNr ) );
            }

            s.setDate( dateString );
            s.setNumberOfPlayers( activityName );
            RealmHelper helper=new RealmHelper( realm );
            helper.save( s );
            d.hide();
            Toast toast=Toast.makeText( getApplicationContext(), "SAVED!", Toast.LENGTH_SHORT );
            toast.show();

        } );
        nameeditTxt.setText( "" );
        d.show();


    }


    void setCubicButton() {
        Vibrator vibrator=(Vibrator) getSystemService( Context.VIBRATOR_SERVICE );
        cubicButton=findViewById( R.id.cubickButton );
        saveButton=findViewById ( R.id.buttonSave );
        settingsButton=findViewById (R.id.settingsButton );
        ImageButton renewButton=findViewById( R.id.renewButton );
        cubicButton.setOnLongClickListener( v -> {
            for (int i=0; i < PlusButtons.length; ++i) {


                ImageView cubicView1=findViewById( R.id.cubicView1 );
                ImageView cubicView2=findViewById( R.id.cubicView2 );
                final int[] cubicIds={R.drawable.n00, R.drawable.n01, R.drawable.n02, R.drawable.n03, R.drawable.n04, R.drawable.n05};

                Random generator=new Random();
                int index1=generator.nextInt( cubicIds.length );
                int index2=generator.nextInt( cubicIds.length );

                cubicView1.setVisibility( VISIBLE );
                cubicView2.setVisibility( VISIBLE );
                cubicView1.setImageResource( cubicIds[index1] );
                cubicView2.setImageResource( cubicIds[index2] );
                MinusButtons[i].setVisibility( INVISIBLE );
                PlusButtons[i].setVisibility( INVISIBLE );
                NumberCounts[i].setVisibility( INVISIBLE );
                settingsButton.setVisibility ( INVISIBLE );
                saveButton.setVisibility ( INVISIBLE );
                renewButton.setImageResource ( R.drawable.back );



                vibrator.vibrate( 50 );


            }
            return true;

        } );
    }

    public void setSpref() {
        for (int i=0; i < PlusButtons.length; ++i) {
            int lifeScore = 20;
            spref=getApplicationContext().getSharedPreferences( prefsCounts[i], MODE_PRIVATE );


            if (!spref.contains( prefsCounts[i] )) {

                SharedPreferences.Editor editor=spref.edit();
                editor.putInt( prefsCounts[i], Integer.parseInt( String.valueOf( lifeScore ) ) );
                editor.apply();
            }
            String count=String.valueOf( spref.getInt( prefsCounts[i], 0 ) );

            if (spref.contains( "greyCount1" )) {
                NumberCounts[i].setText( count );
            }
        }

    }

    public void setCubicButtonClick(ImageButton cubicButton) {
        this.cubicButton=cubicButton;
        cubicButton.setOnClickListener( v -> {
            Toast toast=Toast.makeText( getApplicationContext(), "Hold to reset the dice", Toast.LENGTH_SHORT );
            toast.show();
        } );
    }

    public void setSettingsButtonClick() {
        settingsButton=findViewById( R.id.settingsButton );
        settingsButton.setOnClickListener( v -> {
            Toast toast=Toast.makeText( getApplicationContext(), "Hold to start a new game", Toast.LENGTH_SHORT );
            toast.show();
        } );
    }

    public void setSettingsButton() {

        Button settingsButton=findViewById( R.id.settingsButton );
        settingsButton.setOnLongClickListener( v -> {
            startActivity( new Intent( this, SettingsActivity.class ) );
            return false;
        } );

    }

    public void nullInstanseState() {

            Intent intent=getIntent();
            if (intent.hasExtra( "lifes" )) {
                Bundle extras=getIntent().getExtras();

                assert extras != null;
                int lifeScore[]= extras.getIntArray( "lifes");
                for (int i=0; i < PlusButtons.length; ++i) {
                    spref=getApplicationContext().getSharedPreferences( prefsCounts[i], MODE_PRIVATE );
                    SharedPreferences.Editor editor=spref.edit();
                    assert lifeScore != null;
                    editor.putInt( prefsCounts[i], (lifeScore[i]) );
                    editor.apply();
                    NumberCounts[i].setText( String.valueOf( lifeScore[i] ) );
                }
            }

    }

    public void nullLifeSettingsInstantState() {
        for (int i=0; i < PlusButtons.length; ++i) {
            Intent intent=getIntent();
            if (intent.hasExtra( "lifeScore" )) {
                Bundle extras=getIntent().getExtras();
                assert extras != null;
                int lifeScore=(int) extras.get( "lifeScore" );
                spref=getApplicationContext().getSharedPreferences( prefsCounts[i], MODE_PRIVATE );
                SharedPreferences.Editor editor=spref.edit();
                editor.putInt( prefsCounts[i], (lifeScore) );
                editor.apply();
                NumberCounts[i].setText( String.valueOf( lifeScore ) );
                if (lifeScore>=99){PlusButtons[i].setClickable ( false );}

            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(int contentVidsId, int numberPlayers, String activityName) {
        init( numberPlayers );
        setContentView( contentVidsId );
        initRes();
        setRenewButton();
        setRenewButtonShortClick();
        setSaveButton();
        setCubicButton();
        setCubicButtonClick( cubicButton );
        setSettingsButton();
        setSettingsButtonClick();
        nullInstanseState();
        nullLifeSettingsInstantState();


        final Vibrator vibrator=(Vibrator) getSystemService( Context.VIBRATOR_SERVICE );

        for (int i=0; i < PlusButtons.length; ++i) {
            spref=getApplicationContext().getSharedPreferences( prefsCounts[i], MODE_PRIVATE );
            ImageButton cubicButton=findViewById( R.id.cubickButton );
            ImageButton renewButton=findViewById( R.id.renewButton );

            MinusButtons[i].setOnClickListener( new CountButtonAction ( MinusButtons[ i ], PlusButtons[ i ], spref, NumberCounts[ i ], vibrator, prefsCounts[ i ] ) {
                @Override
                protected void executeOnClick(int[] oCount) {
                    textView.setText ( "" + --oCount[ 0 ] );
                    if (oCount[ 0 ] < 100) {
                        imageButton2.setClickable ( true );
                    }
                    if (oCount[ 0 ] <= 0) {
                        imageButton.setClickable ( false );
                    }
                }
            } );
            PlusButtons[i].setOnClickListener( new CountButtonAction ( PlusButtons[ i ], MinusButtons[ i ], spref, NumberCounts[ i ], vibrator, prefsCounts[ i ] ) {
                @Override
                protected void executeOnClick(int[] oCount) {
                    textView.setText ( "" + ++oCount[ 0 ] );
                    if (oCount[ 0 ] >= 0) {
                        imageButton2.setClickable ( true );
                    }
                    if (oCount[ 0 ] >= 99) {
                        imageButton.setClickable( false );

                    }
                }
            } );


            MinusButtons[i].setOnTouchListener( new ButtonHighlighterOnTouchListener( MinusButtons[i] ) );
            PlusButtons[i].setOnTouchListener( new ButtonHighlighterOnTouchListener( PlusButtons[i] ) );
            cubicButton.setOnTouchListener( new ButtonHighlighterOnTouchListener( cubicButton ) );
            renewButton.setOnTouchListener( new ButtonHighlighterOnTouchListener( renewButton ) );

            Button saveButton=findViewById( R.id.buttonSave );
            saveButton.setOnClickListener( v -> {
                realm.beginTransaction();
                displayInputDialog( activityName );
                realm.commitTransaction();
            } );
        }


    }


}


