package com.guahoo.mtgcount.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.guahoo.mtgcount.DataBase.LifeDb;
import com.guahoo.mtgcount.DataBase.LifeScoreEntity;
import com.guahoo.mtgcount.R;
import com.guahoo.mtgcount.DataBase.RealmHelper;
import com.guahoo.mtgcount.Utils.SwipeListViewTouchListener;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class SavedCountActivity extends AppCompatActivity {
    Realm realm;
    SimpleAdapter adapter;
    ListView lv;

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

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_saved_count );


        Realm.init( this );

        lv=(ListView) findViewById( R.id.lv );
        RealmConfiguration config=new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        realm=Realm.getInstance( config );


        RealmResults <LifeDb> lifeDbs=realm.where( LifeDb.class ).sort( "date", Sort.ASCENDING ).findAll();
        RealmHelper helper=new RealmHelper( realm );

        adapter=new SimpleAdapter( this, helper.getName( lifeDbs ), R.layout.listview_item, new String[]{"name", "currentdate", "numberOfPlayers"}, new int[]{R.id.col1, R.id.col2, R.id.col3} );
        lv.setAdapter( adapter );




        SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener( lv,
                new SwipeListViewTouchListener.OnSwipeCallback () {
                    @Override
                    public void onSwipeLeft(ListView listView, int[] reverseSortedPositions,int position)
                    {
                        AlertDialog.Builder alert=new AlertDialog.Builder( SavedCountActivity.this );
                        alert.setTitle( "Deleting game" );
                        alert.setMessage( "Realy delete game?" );
                        alert.setPositiveButton( android.R.string.yes, (dialog, which) -> {
                            // continue with delete
                            LifeDb lifeDb=lifeDbs.get( position );
                            RealmResults <LifeDb> lifeDbs=realm.where( LifeDb.class ).sort( "date", Sort.ASCENDING ).findAll();


                            assert lifeDb != null;
                            LifeDb lifedb=lifeDbs.where().equalTo( "id", lifeDb.getId() ).findFirst();
                            realm.beginTransaction();
                            Objects.requireNonNull( lifedb ).deleteFromRealm();
                            realm.commitTransaction();
                            adapter=new SimpleAdapter( SavedCountActivity.this, helper.getName( lifeDbs ), R.layout.listview_item, new String[]{"name", "currentdate", "numberOfPlayers"}, new int[]{R.id.col1, R.id.col2, R.id.col3} );
                            lv.setAdapter( adapter );
                        });
                        alert.setNegativeButton( android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // close dialog
                                dialog.cancel();
                            }
                        } );
                        alert.show();
                    }





                    @Override
                    public void onSwipeRight(ListView lv, int[] reverseSortedPositions,int position)
                    {
                        AlertDialog.Builder alert=new AlertDialog.Builder( SavedCountActivity.this );
                        alert.setTitle( "Deleting game" );
                        alert.setMessage( "Realy delete game?" );
                        alert.setPositiveButton( android.R.string.yes, (dialog, which) -> {
                            // continue with delete
                            LifeDb lifeDb=lifeDbs.get( position );
                            RealmResults <LifeDb> lifeDbs=realm.where( LifeDb.class ).sort( "date", Sort.ASCENDING ).findAll();

                            assert lifeDb != null;
                            LifeDb lifedb=lifeDbs.where().equalTo( "id", lifeDb.getId() ).findFirst();
                            realm.beginTransaction();
                            Objects.requireNonNull( lifedb ).deleteFromRealm();
                            realm.commitTransaction();
                            adapter=new SimpleAdapter( SavedCountActivity.this, helper.getName( lifeDbs ), R.layout.listview_item, new String[]{"name", "currentdate", "numberOfPlayers"}, new int[]{R.id.col1, R.id.col2, R.id.col3} );
                            lv.setAdapter( adapter );
                        });
                        alert.setNegativeButton( android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // close dialog
                                dialog.cancel();
                            }
                        } );
                        alert.show();
                    }
                },true, // example : left action = dismiss
                false); // example : right action without dismiss animation
        lv.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        lv.setOnScrollListener(touchListener.makeScrollListener());
        lv.setOnItemLongClickListener( (parent, view, position, id) -> {

            AlertDialog.Builder alert=new AlertDialog.Builder( this );
            alert.setTitle( "Deleting game" );
            alert.setMessage( "Realy delete game?" );
            alert.setPositiveButton( android.R.string.yes, (dialog, which) -> {
                // continue with delete

                LifeDb lifeDb=lifeDbs.get( position );
                final RealmResults <LifeDb> cardsId=realm.where( LifeDb.class ).findAll();
                assert lifeDb != null;
                LifeDb lifedb=cardsId.where().equalTo( "id", lifeDb.getId() ).findFirst();
                realm.beginTransaction();
                Objects.requireNonNull( lifedb ).deleteFromRealm();
                realm.commitTransaction();
                adapter=new SimpleAdapter( this, helper.getName( lifeDbs ), R.layout.listview_item, new String[]{"name", "currentdate", "numberOfPlayers"}, new int[]{R.id.col1, R.id.col2, R.id.col3} );
                lv.setAdapter( adapter );


            } );
            alert.setNegativeButton( android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // close dialog
                    dialog.cancel();
                }
            } );
            alert.show();
            return true;
        } );


        lv.setOnItemClickListener( (parent, view, position, id) -> {
            LifeDb lifeDb=lifeDbs.get( position );
            assert lifeDb != null;

            switch (lifeDb.numberOfPlayers) {
                case "2 Players":
                    startVActivity( CountActivity.class, lifeDb );

                    break;
                case "3 Players":
                    startVActivity( Count3PlayerActivity.class, lifeDb );
                    break;
                case "4 Players":
                    startVActivity( CountPlayer4Activity.class, lifeDb );
                    break;

                default:
                    Toast.makeText( getApplicationContext(), "DONT WORK!!!", Toast.LENGTH_SHORT ).show();

            }

        } );


    }
    void startVActivity(Class clazz, LifeDb lifeDb) {
        RealmList <LifeScoreEntity> lifeEntities=lifeDb.getLifeScores();
        int lifes[]=new int[lifeEntities.size()];
        for (int i=0; i < lifes.length; ++i) {
            lifes[i]=Objects.requireNonNull( lifeEntities.get( i ) ).getLifeNr();
        }
        startActivity( new Intent( getApplicationContext(), clazz ).putExtra( "lifes", lifes ) );
    }
    protected void onStart() {
        super.onStart();
        Toast toast = Toast.makeText( this,"Click to load, Swipe to delete",Toast.LENGTH_LONG );
        toast.show();
    }
}
//старый рабочий лонг клик


//
///**
// * Свайп, просто свайп
// * //        lv.setOnTouchListener(new OnSwipeTouchListener ( SavedCountActivity.this,lv ) {
// * //            public void onSwipeRight() {
// * //
// * //                LifeDb lifeDb= lifeDbs.get ( position );
// * //                final RealmResults <LifeDb> cardsId = realm.where ( LifeDb.class ).findAll ();
// * //                assert lifeDb != null;
// * //                LifeDb lifedb = cardsId.where ().equalTo ( "id", lifeDb.getId ()).findFirst ();
// * //                realm.beginTransaction ();
// * //                Objects.requireNonNull ( lifedb ).deleteFromRealm ();
// * //                realm.commitTransaction ();
// * //                adapter = new SimpleAdapter ( SavedCountActivity.this, helper.getName ( lifeDbs ), R.layout.listview_item,
// * //                        new String[]{"name", "currentdate", "numberOfPlayers"}, new int[]{R.id.col1, R.id.col2, R.id.col3} );
// * //                lv.setAdapter ( adapter );
// * //                }
// * //                });
// * //
//// * <p>
//// * крутой свайп с прозрачностью
//// * <p>
////
//
//