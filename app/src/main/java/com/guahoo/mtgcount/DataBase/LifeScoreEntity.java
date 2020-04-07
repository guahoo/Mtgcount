package com.guahoo.mtgcount.DataBase;

import io.realm.RealmObject;

public class LifeScoreEntity extends RealmObject {
        private int lifeNr;

        public LifeScoreEntity (Integer lifeNr) {
            this.lifeNr = lifeNr;
        }

        public LifeScoreEntity() {
        }

        public int getLifeNr() {
            return lifeNr;
        }

        public void setCardNr(int cardNr) {
            this.lifeNr = lifeNr;
        }
}
