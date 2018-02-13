package com.example.nngo1.remindme.Relation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.nngo1.remindme.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nngo1 on 15-Oct-17.
 */

public class RelationManager {
    private DatabaseHelper dbHelper;


    public RelationManager(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public List<Relation> getList() {
        /*SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.RELATIONS_TABLE,
                null
        );

        List<Relation> relations = new ArrayList<>();

        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Relation relation = new Relation(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("phone")),
                        cursor.getString(cursor.getColumnIndex("address")),
                        cursor.getString(cursor.getColumnIndex("relationship")),
                        cursor.getLong(cursor.getColumnIndex("_id"))
                );
                relations.add(relation);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return relations;
    */
        return null;
    }

    public void addRelation(Relation relation) {
        ContentValues newRelation = new ContentValues();
        newRelation.put("name", relation.getName());
        newRelation.put("phone", relation.getPhone());
        newRelation.put("address", relation.getAddress());
        newRelation.put("relationship", relation.getRelationship());

        // get a write lock on the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(DatabaseHelper.RELATIONS_TABLE, null, newRelation);
        // don't need to close, when application closes, Android OS will
        // close it for us because singleton, acts as a filelock
        // db.close();

    }

    public void deleteRelation(int index) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.RELATIONS_TABLE, "_id=\"" + index + "\"", null);
    }

    public void updateRelation(Relation relation) {
        ContentValues editRelation = new ContentValues();
        editRelation.put("name", relation.getName());
        editRelation.put("phone", relation.getPhone());
        editRelation.put("address", relation.getAddress());
        editRelation.put("relationship", relation.getRelationship());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = new String[] {
                String.valueOf(relation.getId())
        };
        db.update(DatabaseHelper.RELATIONS_TABLE, editRelation, "_id=?", args);
    }
}
