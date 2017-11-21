package com.example.thebattleofuniversities.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Олжас on 23.10.2017.
 */

public class DbWar extends SQLiteOpenHelper{

    public static final String NAMEDB = "university.db";
    public static final int DB_VERSION = 1;

    public static final String SQL_CREATE_DB = "CREATE TABLE " + Contract.UniversityEntry.TABLE_NAME + " (" +
            Contract.UniversityEntry._ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " + Contract.UniversityEntry.COLUMN_NAME + " TEXT, " +
            Contract.UniversityEntry.COLUMN_NICKNAME + " TEXT, " + Contract.UniversityEntry.COLUMN_GENGER + " INTEGER NOT NULL, " +  Contract.UniversityEntry.COLUMN_UNIVERSITY  + " TEXT, "
            + Contract.UniversityEntry.COLUMN_LASTNAME + " TEXT);";




    public DbWar(Context context) {
        super(context, NAMEDB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_DB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
}
