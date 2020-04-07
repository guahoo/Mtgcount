package com.guahoo.mtgcount.DataBase;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmHelper {
    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void save(final LifeDb lifeDb) {
        Number curId = realm.where(LifeDb.class).max("id");
        lifeDb.setId ( curId == null ? 0 : ((Long)curId +1) );
        realm.executeTransaction ( realm -> realm.copyToRealm ( lifeDb ) );
    }




    public RealmResults <LifeDb> retrieve() {
        return realm.where ( LifeDb.class )
                .sort ( "id", Sort.ASCENDING )
                .findAll ();
    }

    public ArrayList <HashMap <String, String>> getName(RealmResults <LifeDb> savedInfo) {
        ArrayList <HashMap <String, String>> names = new ArrayList <HashMap <String, String>> ();

        for (LifeDb lifeDb : savedInfo) {
            HashMap <String, String> map = new HashMap <String, String> ();

            map.put ( "name", lifeDb.getName () );
            map.put ( "numberOfPlayers", lifeDb.getNumberOfPlayers () );
            map.put ( "currentdate", lifeDb.getDate () );
            names.add ( map );
        }
        return names;
    }

}