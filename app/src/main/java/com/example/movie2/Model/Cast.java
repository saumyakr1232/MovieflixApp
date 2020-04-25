package com.example.movie2.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cast implements Parcelable {
    public int cast_id;
    public String character;
    public String credit_id;
    public int gender;
    public String name;
    public int order;
    public String profile_path;

    public Cast(int cast_id, String character, String credit_id, int gender, String name, int order, String profile_path) {
        this.cast_id = cast_id;
        this.character = character;
        this.credit_id = credit_id;
        this.gender = gender;
        this.name = name;
        this.order = order;
        this.profile_path = profile_path;
    }

    protected Cast(Parcel in) {
        cast_id = in.readInt();
        character = in.readString();
        credit_id = in.readString();
        gender = in.readInt();
        name = in.readString();
        order = in.readInt();
        profile_path = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    @Override
    public String toString() {
        return "Cast{" +
                "cast_id=" + cast_id +
                ", character='" + character + '\'' +
                ", credit_id='" + credit_id + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cast_id);
        dest.writeString(character);
        dest.writeString(credit_id);
        dest.writeInt(gender);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeString(profile_path);
    }
}