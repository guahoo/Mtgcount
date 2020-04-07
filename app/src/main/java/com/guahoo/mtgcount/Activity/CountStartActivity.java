package com.guahoo.mtgcount.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.guahoo.mtgcount.DataBase.LifeDb;
import com.guahoo.mtgcount.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;


public class CountStartActivity extends AppCompatActivity {
    Button newGameButton;
    Button savedGameButton;
    Realm realm;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LOCKED );
        requestWindowFeature( Window.FEATURE_NO_TITLE );

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled( false );
        }
        getSupportActionBar().hide();
        setContentView( R.layout.activity_count_start );
        super.onCreate( savedInstanceState );
        Realm.init ( this );
        RealmConfiguration config = new RealmConfiguration.Builder ().deleteRealmIfMigrationNeeded ().build ();
        realm = Realm.getInstance ( config );


        RealmResults <LifeDb> lifeDbs = realm.where ( LifeDb.class )
                .sort ( "date", Sort.ASCENDING )
                .findAll ();

        newGameButton=findViewById( R.id.newGameButton );
        savedGameButton=findViewById( R.id.savedGameButton );
        if (!lifeDbs.isEmpty()){
            savedGameButton.setVisibility ( View.VISIBLE );
        }
        newGameButton.setOnTouchListener ( (v, event) -> {
            newGameButton.setTextColor (Color.parseColor("#000000") );
            return true;
        } );

        newGameButton.setOnClickListener( view -> {


            Intent newGame=new Intent( getApplicationContext(), SettingsActivity.class );
            startActivity( newGame );

        } );
        savedGameButton.setOnClickListener( view -> {

            Intent savedGames=new Intent( getApplicationContext(), SavedCountActivity.class );
            startActivity( savedGames );

        } );

    }
        protected void onResume () {

          
            getSupportActionBar().hide();
            setContentView( R.layout.activity_count_start );
            super.onResume();

            Realm.init ( this );
            RealmConfiguration config = new RealmConfiguration.Builder ().deleteRealmIfMigrationNeeded ().build ();
            realm = Realm.getInstance ( config );


            RealmResults <LifeDb> lifeDbs = realm.where ( LifeDb.class )
                    .sort ( "date", Sort.ASCENDING )
                    .findAll ();

            newGameButton=findViewById( R.id.newGameButton );
            savedGameButton=findViewById( R.id.savedGameButton );
            if (!lifeDbs.isEmpty()){
                savedGameButton.setVisibility ( View.VISIBLE );
            }

            newGameButton.setOnClickListener( view -> {

                Intent newGame=new Intent( getApplicationContext(), SettingsActivity.class );
                startActivity( newGame );

            } );
            savedGameButton.setOnClickListener( view -> {

                Intent savedGames=new Intent( getApplicationContext(), SavedCountActivity.class );
                startActivity( savedGames );

            } );
    }

}
