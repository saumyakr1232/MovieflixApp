package com.example.movie2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Credits implements Parcelable {
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

    protected Credits(Parcel in) {
        id = in.readInt();
        cast = in.createTypedArray(Cast.CREATOR);
    }

    public static final Creator<Credits> CREATOR = new Creator<Credits>() {
        @Override
        public Credits createFromParcel(Parcel in) {
            return new Credits(in);
        }

        @Override
        public Credits[] newArray(int size) {
            return new Credits[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedArray(cast, flags);
    }
}
