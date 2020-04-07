package com.guahoo.mtgcount.Utils;

import android.content.SharedPreferences;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class CountButtonAction implements View.OnClickListener {
    protected ImageButton imageButton;
    protected ImageButton imageButton2;
    protected SharedPreferences sharedPreferences;
    protected TextView textView;
    protected Vibrator vibrator;
    private String orCount;

    public CountButtonAction(ImageButton imageButton, ImageButton imageButton2, SharedPreferences sharedPreferences, TextView textView, Vibrator vibrator, String orCount) {
        super();
        this.imageButton=imageButton;
        this.imageButton2=imageButton2;
        this.sharedPreferences = sharedPreferences;
        this.textView = textView;
        this.vibrator = vibrator;
        this.orCount = orCount;
    }

    @Override
    public void onClick(View v) {
        int[] oCount = {Integer.decode ( String.valueOf ( sharedPreferences.getInt ( orCount, 0 )))};



        SharedPreferences.Editor editor = sharedPreferences.edit ();
        executeOnClick(oCount);
        editor.putInt ( orCount, oCount [ 0 ] );
        editor.apply ();
        vibrator.vibrate ( 20 );
    }

    protected abstract void executeOnClick(final int[] oCount);

}
