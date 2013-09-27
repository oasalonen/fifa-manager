package com.futurice.fifaman.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Olli on 26/09/13.
 */
public class Ladder {

    private final List<Match> mMatches;

    public Ladder(List<Team> teams) {
        mMatches = new ArrayList<Match>();
    }

    private void generateMatches(List<Team> teams) {
        mMatches.clear();

        int count = teams.size();
        for (int i = 0; i < count - 1; i++) {
            Team team = teams.get(i);
            for (int j = i + 1; j < count; j++) {
                Team matchTeam = teams.get(j);
                Match match = new Match(team, matchTeam);
                mMatches.add(match);
            }
        }
    }

}
