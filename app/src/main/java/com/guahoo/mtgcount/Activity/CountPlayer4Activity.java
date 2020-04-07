package com.guahoo.mtgcount.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guahoo.mtgcount.R;

public class CountPlayer4Activity extends BaseCountActivity{
    ImageButton renewButton;
    ImageButton cubicButton;
    Button settingsButton;
    ImageView cubicView1;
    ImageView cubicView2;
    Button saveButton;


    protected void initRes() {
        PlusButtons=new ImageButton[]{findViewById( R.id.plusButton1 ),findViewById ( R.id.plusButton2 ),findViewById ( R.id.plusButton3 ),findViewById ( R.id.plusButton4 )};
        MinusButtons=new ImageButton[]{findViewById( R.id.minusButton1 ),findViewById ( R.id.minusButton2 ),findViewById ( R.id.minusButton3 ),findViewById ( R.id.minusButton4 )};
        NumberCounts=new TextView[]{findViewById( R.id.numberCount1 ),findViewById ( R.id.numberCount2 ),findViewById ( R.id.numberCount3 ),findViewById ( R.id.numberCount4 )};
        prefsCounts = new String[]{ ("lifeScore1"), ( "lifeScore2"),( "lifeScore3"),( "lifeScore4")};
        settingsButton=findViewById( R.id.settingsButton );
        renewButton=findViewById( R.id.renewButton );
        cubicButton=findViewById( R.id.cubickButton );
        cubicView1=findViewById( R.id.cubicView1 );
        cubicView2=findViewById( R.id.cubicView2 );
        saveButton=findViewById( R.id.buttonSave );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        onCreate( R.layout.activity_player4, 4, "4 Players" );
        {
            if (!spref.contains( "greyCount" )){
                setSpref();
            }
        }
    }
}
