package com.aem.geeks.core.javarelatedcodes.roughFolder;

import java.util.*;

public class LeagueTable <Team extends Sport> {

    List<Team> teamsList = new ArrayList<>();

    public void addTeams (Team team) {
        if (!teamsList.contains(team)) {
            teamsList.add(team);
        } else {
            System.out.println("This Team is already in the list");
        }
    }



    public void currentGenClass (Team team) {
        String info = team.infoMethod();
        System.out.println(info);
    }

    public String[] informationTest (Team team) {
        return team.players();
    }

}
