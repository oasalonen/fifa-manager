package com.futurice.fifaman.models;

/**
 * Created by Olli on 26/09/13.
 */
public class Player {

    private String mName;

    public Player() {
        mName = "";
    }

    public Player(String name) {
        assert name != null;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        assert name != null;
        mName = name;
    }

}
