package com.example.nngo1.remindme.Account;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nngo1 on 27-Nov-17.
 */

public class Account implements Parcelable {
    private String name;
    private String role;
    private boolean signedIn;
    private String id;

    public Account () {}

    public Account(String name, String role) {
        this(name, role, -1);
    }
    public Account(String name, String role, long id) {
        this.name = name;
        this.role = role;
        this.signedIn = false;
        this.id = String.valueOf(hashCode());
    }

    protected Account(Parcel in) {
        name = in.readString();
        role = in.readString();
        id = in.readString();
        signedIn = in.readByte() != 0;
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int hashCode() {
        int hash = 1;
        hash *= 17 + name.hashCode();
        hash *= 19 + role.hashCode();
        return hash;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(role);
        parcel.writeString(id);
        parcel.writeByte((byte) (isSignedIn() ? 1 : 0));
    }
}
