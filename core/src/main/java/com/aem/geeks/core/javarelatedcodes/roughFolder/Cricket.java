package com.aem.geeks.core.javarelatedcodes.roughFolder;

public class Cricket implements Sport{

    String[] players;

    public Cricket(String[] players) {
        this.players = players;
    }

    @Override
    public String infoMethod() {
        return "This Method is from - Cricket Class";
    }

    @Override
    public String[] players() {
        return players;
    }
}
