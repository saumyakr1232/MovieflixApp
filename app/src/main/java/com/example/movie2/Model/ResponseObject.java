package com.example.movie2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ResponseObject implements Parcelable {
    private int page;
    private ArrayList<MovieItem> results;

    public ResponseObject(int page, ArrayList<MovieItem> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<MovieItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieItem> results) {
        this.results = results;
    }

    protected ResponseObject(Parcel in) {
        page = in.readInt();
    }

    public static final Creator<ResponseObject> CREATOR = new Creator<ResponseObject>() {
        @Override
        public ResponseObject createFromParcel(Parcel in) {
            return new ResponseObject(in);
        }

        @Override
        public ResponseObject[] newArray(int size) {
            return new ResponseObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "page=" + page +
                ", results=" + results +
                '}';
    }
}
