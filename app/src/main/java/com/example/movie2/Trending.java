package com.example.movie2;

import java.util.Arrays;

public class Trending {
    private int page;
    private MovieItems[] results;

    public Trending(int page, MovieItems[] results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MovieItems[] getResults() {
        return results;
    }

    public void setResults(MovieItems[] results) {
        this.results = results;

    }

    @Override
    public String toString() {
        return "Trending{" +
                "page=" + page +
                ", results=" + Arrays.toString(results) +
                '}';
    }
}