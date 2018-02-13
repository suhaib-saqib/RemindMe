package com.example.nngo1.remindme.Relation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nngo1 on 14-Oct-17.
 */

public class Relation implements Parcelable {
    private String name;
    private String phone;
    private String address;
    private String relationship;
    private String id;

    public Relation() {}

    public Relation(String name, String phone, String address, String relationship) {
        this(name, phone, address, relationship, "-1");
    }

    public Relation(String name, String phone, String address, String relationship, String id) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.relationship = relationship;
        this.id = String.valueOf(hashCode());
    }

    protected Relation(Parcel in) {
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        relationship = in.readString();
        id = in.readString();
    }

    @Override
    public int hashCode() {
        int hash = 1;

        hash *= 17 + name.hashCode();
        hash *= 19 + phone.hashCode();
        hash *= 23 + address.hashCode();
        hash *= 29 + relationship.hashCode();


        return hash;
    }

    public static final Creator<Relation> CREATOR = new Creator<Relation>() {
        @Override
        public Relation createFromParcel(Parcel in) {
            return new Relation(in);
        }

        @Override
        public Relation[] newArray(int size) {
            return new Relation[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(relationship);
        parcel.writeString(id);
    }
}
