package com.futurice.fifamanager.models;

/**
 * Created by Olli on 26/09/13.
 */
public class Match {

    private final Team mTeamA;
    private final Team mTeamB;
    private Team mWinner;

    public Match(Team teamA, Team teamB) {
        mTeamA = teamA;
        mTeamB = teamB;
    }

    public Team getWinner() {
        return mWinner;
    }

    public void setWinner(Team winner) {
        mWinner = winner;
    }
}
