package com.example.nngo1.remindme.Task;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nngo1 on 26-Nov-17.
 */

public class Task implements Parcelable {
    private String description;
    private boolean isComplete;
    private String id;
    private String dateAdded;
    private static SimpleDateFormat formatter;

    static {
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    }

    public Task() {}
    public Task(String description, boolean isComplete) {
        this(description, "", isComplete, "-1");
    }

    public Task(String description, boolean isComplete, String id) {
        this(description, new Date().toString(), isComplete, id);
    }

    public Task(String description, String date, boolean isComplete, String id) {
        this.description = description;
        this.isComplete = isComplete;
        //this.dateAdded = dateAdded;


        if (date.equals("")) {
            this.dateAdded = new Date().toString();
        } else {
            this.dateAdded = date;
        }

        this.id = String.valueOf(hashCode());
    }

    protected Task(Parcel in) {
        id = in.readString();
        description = in.readString();
        isComplete = in.readByte() != 0;
        dateAdded = in.readString();
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash *= 17 + description.hashCode();
        hash *= 19 + (isComplete ? 0 : 1);
        hash *= 23 + dateAdded.hashCode();

        return hash;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getDescription() { return description; }
    public boolean isComplete() { return isComplete; }
    public void toggleComplete() { isComplete =! isComplete; }
    public String getDateAdded() { return dateAdded; }
    public String getId() { return id; }

    @Override
    public String toString() { return getDescription(); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(description);
        parcel.writeByte((byte) (isComplete ? 1 : 0));
        parcel.writeString(dateAdded);
    }

}
