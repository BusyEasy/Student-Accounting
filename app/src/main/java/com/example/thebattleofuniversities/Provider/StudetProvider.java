package com.example.thebattleofuniversities.Provider;

import android.app.Notification;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.thebattleofuniversities.DbHelper.Contract;
import com.example.thebattleofuniversities.DbHelper.DbWar;

/**
 * Created by Олжас on 22.11.2017.
 */

public class StudetProvider extends ContentProvider{

    DbWar dbWar;
    private static final int STUDENT = 100;
    private static final int STUDENT_ID = 101;
    private static final UriMatcher  sUriMathcer = new UriMatcher(UriMatcher.NO_MATCH);
    

    static {

        sUriMathcer.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_STUDENT, STUDENT);
        sUriMathcer.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_STUDENT + "/#", STUDENT_ID);

    }


    @Override
    public boolean onCreate() {

        dbWar = new DbWar(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = dbWar.getReadableDatabase();
        Cursor cursor;
        int match = sUriMathcer.match(uri);

        switch (match){

            case STUDENT:
                projection = new String[]{Contract.UniversityEntry._ID, Contract.UniversityEntry.COLUMN_NAME,
                                          Contract.UniversityEntry.COLUMN_LASTNAME, Contract.UniversityEntry.COLUMN_UNIVERSITY,
                                          Contract.UniversityEntry.COLUMN_GENGER, Contract.UniversityEntry.COLUMN_NICKNAME
                };
                cursor = database.query(Contract.UniversityEntry.TABLE_NAME, projection, null, null,null,null,null);

                break;

          case STUDENT_ID:

                selection = Contract.UniversityEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(Contract.UniversityEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("НАРМАЛЬНА ДЕЛАЙ ЗАПРАС " +uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMathcer.match(uri);

        switch (match){

            case STUDENT:
                return Contract.UniversityEntry.CONTENT_LIST_TYPE;
            case STUDENT_ID:
                return Contract.UniversityEntry.CONTENT_LIST_ITEM;


            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);

        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int match = sUriMathcer.match(uri);
        switch (match){

            case STUDENT:
                return insertStudent(uri, values);

            default:
                throw new IllegalArgumentException("Не удалось вставить " + uri);

        }


    }

    private Uri insertStudent(Uri uri, ContentValues contentValues){

        SQLiteDatabase database = dbWar.getWritableDatabase();

        String name = contentValues.getAsString(Contract.UniversityEntry.COLUMN_NAME);
        String lastName = contentValues.getAsString(Contract.UniversityEntry.COLUMN_LASTNAME);
        String nickName = contentValues.getAsString(Contract.UniversityEntry.COLUMN_NICKNAME);
        int gender = contentValues.getAsInteger(Contract.UniversityEntry.COLUMN_GENGER);

        if(name==null){
            throw new IllegalArgumentException("Student requires a name");
        }
        else if(lastName==null){
            throw new IllegalArgumentException("Student requires a Lastname");
        }
        else if(nickName==null){
            throw new IllegalArgumentException("Student requires a nickname");
        }
        else if(gender==0){
            throw new IllegalArgumentException("Student requires a gender");
        }
        long insertPeople = database.insert(Contract.UniversityEntry.TABLE_NAME, null, contentValues);

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.withAppendedPath(uri, String.valueOf(insertPeople));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = dbWar.getWritableDatabase();

        int match = sUriMathcer.match(uri);
        int deleteRows;
        switch (match){

            case STUDENT:

                deleteRows = sqLiteDatabase.delete(Contract.UniversityEntry.TABLE_NAME, selection, selectionArgs);
             break;

            case STUDENT_ID:

                selection = Contract.UniversityEntry._ID  + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                deleteRows = sqLiteDatabase.delete(Contract.UniversityEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Delete Student error, problem uri" + uri);

        }

        if(deleteRows!=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return  deleteRows;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int match = sUriMathcer.match(uri);

        switch (match){

            case STUDENT:

                return updateStudent(uri, values, selection, selectionArgs);

            case STUDENT_ID:

                selection = Contract.UniversityEntry._ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                return updateStudent(uri,  values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);


        }


    }

    private int updateStudent(Uri uri, ContentValues values, String selection, String [] selectionArgs){

     SQLiteDatabase database = dbWar.getWritableDatabase();

        if(values.containsKey(Contract.UniversityEntry.COLUMN_NAME)){

            String name = values.getAsString(Contract.UniversityEntry.COLUMN_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Student requires a name");
            }

        }

        if(values.containsKey(Contract.UniversityEntry.COLUMN_NICKNAME)){

            String lastName = values.getAsString(Contract.UniversityEntry.COLUMN_NICKNAME);

            if(lastName==null){
                throw new IllegalArgumentException("Student requires a Nickname");
            }

        }

        if(values.containsKey(Contract.UniversityEntry.COLUMN_UNIVERSITY)){

            String universityName = values.getAsString(Contract.UniversityEntry.COLUMN_UNIVERSITY);

            if(universityName==null){
                throw new IllegalArgumentException("Stuednt requires a UniversityName");
            }

        }

        if (values.size() == 0) {
            return 0;
        }


        int updateRows = database.update(Contract.UniversityEntry.TABLE_NAME, values, selection, selectionArgs);
        if(updateRows!=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateRows;
    }


}
