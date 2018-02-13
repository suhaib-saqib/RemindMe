package com.example.nngo1.remindme.Event;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nngo1 on 26-Nov-17.
 */

public class Event implements Parcelable {
    private String name;
    private String date;
    private String id;
    private String time;

    public Event() {}

    public Event(String name, String date) {
        this(name, date, "-1");
    }

    public Event(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.id = String.valueOf(hashCode());
    }

    @Override
    public int hashCode() {
        int hash = 1;

        hash *= 17 + name.hashCode();
        hash *= 19 + date.hashCode();
        hash *= 23 + time.hashCode();

        return hash;
    }

    protected Event(Parcel in) {
        name = in.readString();
        date = in.readString();
        time = in.readString();
        id = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(id);
    }
}
