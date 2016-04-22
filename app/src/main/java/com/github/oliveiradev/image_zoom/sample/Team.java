package com.github.oliveiradev.image_zoom.sample;

/**
 * Created by felipe on 22/04/16.
 */
public class Team {

    private String name;
    private int logo;

    private Team(String name, int logo) {
        this.name = name;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }


    public static class Factory {
        public static Team create(String name, int logo){
            return new Team(name,logo);
        }
    }
}
