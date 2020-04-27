package com.example.movie2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Review implements Parcelable {
    private int id;
    private int page;
    private SingleReview[] results;
    private int total_pages;
    private int total_results;

    protected Review(Parcel in) {
        id = in.readInt();
        page = in.readInt();
        total_pages = in.readInt();
        total_results = in.readInt();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(page);
        dest.writeInt(total_pages);
        dest.writeInt(total_results);
    }

    public Review() {
    }

    public Review(int id, int page, SingleReview[] results, int total_pages, int total_results) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public SingleReview[] getResults() {
        return results;
    }

    public void setResults(SingleReview[] results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", page=" + page +
                ", results=" + Arrays.toString(results) +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
