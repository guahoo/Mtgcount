package com.guahoo.mtgcount.DataBase;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LifeDb extends RealmObject {
    @PrimaryKey
    private Long id;
    public String name;
    public String date;
    public  String numberOfPlayers;


    private RealmList<LifeScoreEntity> lifeNr = new RealmList<LifeScoreEntity>();
    public RealmList <LifeScoreEntity> getLifeScores() { return lifeNr; }



    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


//    private int lifeScore1;
//    private int lifeScore2;
//    private int lifeScore3;
//    private int lifeScore4;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



//    public int getLifeScore2() {
//        return lifeScore2;
//    }
//
//    public void setLifeScore2(int lifeScore2) {
//        this.lifeScore2 = lifeScore2;
//    }
//
//    public int getLifeScore3() {
//        return lifeScore3;
//    }
//
//    public void setLifeScore3(int lifeScore3) {
//        this.lifeScore3 = lifeScore3;
//    }
//
//    public int getLifeScore4() {
//        return lifeScore4;
//    }
//
//    public void setLifeScore4(int lifeScore4) {
//        this.lifeScore4 = lifeScore4;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public int getLifeScore1() {
//        return lifeScore1;
//    }
//
//    public void setLifeScore1(int lifeScore1) {
//        this.lifeScore1=lifeScore1;
//    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }


}
