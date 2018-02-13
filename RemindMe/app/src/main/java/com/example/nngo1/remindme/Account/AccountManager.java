package com.example.nngo1.remindme.Account;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nngo1.remindme.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nngo1 on 27-Nov-17.
 */

public class AccountManager {
    private DatabaseHelper dbHelper;

    public AccountManager(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public List<Account> getList() {
    /*    SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.ACCOUNTS_TABLE,
                null
        );

        List<Account> accounts = new ArrayList<>();
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Account account = new Account(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("role")),
                        cursor.getLong(cursor.getColumnIndex("_id"))
                );
                accounts.add(account);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return accounts;
    */
    return null;
    }
}
