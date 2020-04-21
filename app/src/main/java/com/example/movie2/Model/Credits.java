package com.example.movie2.Model;

import java.util.Arrays;

public class Credits {
    private int id;
    private Cast[] cast;
    private Crew[] crew;


    public Credits() {
    }


    public Credits(int id, Cast[] cast, Crew[] crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cast[] getCast() {
        return cast;
    }

    public void setCast(Cast[] cast) {
        this.cast = cast;
    }

    public Crew[] getCrew() {
        return crew;
    }

    public void setCrew(Crew[] crew) {
        this.crew = crew;
    }

    @Override
    public String toString() {
        return "Credits{" +
                "id=" + id +
                ", cast=" + Arrays.toString(cast) +
                ", crew=" + Arrays.toString(crew) +
                '}';
    }
}
