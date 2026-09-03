package com.aem.geeks.core.javarelatedcodes.genericsrltd.genericCodes.exampleOne;



//Parent Abstract Class --> this class have 3 children :SoccerPlayer, FootballPlayer, BaseballPlayer
public abstract class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
