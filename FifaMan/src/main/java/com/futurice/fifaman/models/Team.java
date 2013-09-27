package com.futurice.fifamanager.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olli on 26/09/13.
 */
public class Team {

    private final List<Player> mPlayers;

    public Team() {
        mPlayers = new LinkedList<Player>();
    }

    public void addPlayer(Player player) {
        assert !mPlayers.contains(player);
        mPlayers.add(player);
    }

    public List<Player> getPlayers() {
        return new LinkedList<Player>(mPlayers);
    }

}
