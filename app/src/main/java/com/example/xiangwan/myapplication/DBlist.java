package com.example.xiangwan.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBlist extends SQLiteOpenHelper {
    public DBlist(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase dblist) {
        dblist.execSQL("CREATE TABLE user2(" +
                "_id INT DEFAULT 0," +
                "name TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase dblist, int oldVersion, int newVersion) {

    }
}
