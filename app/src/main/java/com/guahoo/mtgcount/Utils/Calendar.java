package com.guahoo.mtgcount.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Calendar {

    Calendar calendar;

    public String getCalendar() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Date currentTime= java.util.Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        final String dateString=dateFormat.format(currentTime);
        return getCalendar ();
    }


}

