package com.futurice.fifamanager.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olli on 26/09/13.
 */
public class ApplicationState {

    private final List<Team> mTeams;
    private final List<Player> mPlayers;
    private Ladder mLadder;

    public ApplicationState() {
        mTeams = new ArrayList<Team>();
        mPlayers = new ArrayList<Player>();
    }

    public void addPlayers(List<Player> players) {
        mPlayers.addAll(players);
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>(mPlayers);
    }

    public void setTeams(List<Team> teams) {
        mTeams.addAll(teams);
    }

    public List<Team> getTeams() {
        return new ArrayList<Team>();
    }

    public Ladder getLadder() {
        return mLadder;
    }

    public void setLadder(Ladder ladder) {
        mLadder = ladder;
    }
}
