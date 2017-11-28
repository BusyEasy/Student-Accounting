package com.example.thebattleofuniversities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebattleofuniversities.DbHelper.Contract;
import com.example.thebattleofuniversities.DbHelper.DbWar;
import com.example.thebattleofuniversities.StudentAdapter.AdapterStudent;

/**
 * Created by Олжас on 27.10.2017.
 */

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int STUDENT_LOADER = 1;
    AdapterStudent adapterStudent;
    Cursor cursor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogactive);

        FloatingActionButton  floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        adapterStudent  = new AdapterStudent(this, cursor);
        ListView listView = (ListView)findViewById(R.id.listView);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        listView.setAdapter(adapterStudent);

        getLoaderManager().initLoader(STUDENT_LOADER, null, this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //displatBaseInfo();
    }

/*

    private void displatBaseInfo() {




        //TextView textView = (TextView)findViewById(R.id.nameStudent);


        try{

            int idColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_NAME);
            int lastNameColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_LASTNAME);
            int nickNameColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_NICKNAME);
            int genderColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_GENGER);
            int universityColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_UNIVERSITY);



            while (cursor.moveToNext()){

                int id = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String  lastName = cursor.getString(lastNameColumnIndex);
                String nickName = cursor.getString(nickNameColumnIndex);
                int gender = cursor.getInt(genderColumnIndex);
                String  university = cursor.getString(universityColumnIndex);
            }

        }
        finally {


        }


    }*/

    private void insertData(){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.UniversityEntry.COLUMN_NAME, "Olzhas");
        contentValues.put(Contract.UniversityEntry.COLUMN_LASTNAME, "Bekturganov");
        contentValues.put(Contract.UniversityEntry.COLUMN_NICKNAME, "Barathum");
        contentValues.put(Contract.UniversityEntry.COLUMN_UNIVERSITY, "Eurasia Internation University");
        contentValues.put(Contract.UniversityEntry.COLUMN_GENGER, Contract.UniversityEntry.Gender_Man);

        Uri newUri = getContentResolver().insert(Contract.UniversityEntry.CONTENT_URI, contentValues);

        if(newUri==null){
            Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, getString(R.string.succes_message), Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    protected void onStop() {
        super.onStop();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalogmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.delete:
                break;
            case R.id.add:
                insertData();
                //displatBaseInfo();
                break;
        }

        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String projection[] = {Contract.UniversityEntry._ID,
                Contract.UniversityEntry.COLUMN_LASTNAME,
                Contract.UniversityEntry.COLUMN_NAME,
                Contract.UniversityEntry.COLUMN_UNIVERSITY
        };
        return new CursorLoader(this, Contract.UniversityEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapterStudent.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        adapterStudent.swapCursor(null);

    }
}
