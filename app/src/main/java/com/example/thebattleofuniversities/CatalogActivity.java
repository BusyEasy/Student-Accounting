package com.example.thebattleofuniversities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebattleofuniversities.DbHelper.Contract;
import com.example.thebattleofuniversities.DbHelper.DbWar;

/**
 * Created by Олжас on 27.10.2017.
 */

public class CatalogActivity extends AppCompatActivity{

    DbWar dbwar;


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

        dbwar = new DbWar(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        displatBaseInfo();
    }

    private void displatBaseInfo() {

        SQLiteDatabase database = dbwar.getReadableDatabase();
        String projection[] = {Contract.UniversityEntry._ID,
                Contract.UniversityEntry.COLUMN_LASTNAME,
                Contract.UniversityEntry.COLUMN_NAME,
                Contract.UniversityEntry.COLUMN_GENGER,
                Contract.UniversityEntry.COLUMN_NICKNAME,
                Contract.UniversityEntry.COLUMN_UNIVERSITY
        };
        Cursor cursor = database.query(Contract.UniversityEntry.TABLE_NAME, projection, null, null, null, null, null);
        TextView textView = (TextView)findViewById(R.id.testText);

        try{

            textView.setText("База данных,количество столбцов "+cursor.getCount() + "\n\n");
            textView.append(Contract.UniversityEntry._ID + " - "+ Contract.UniversityEntry.COLUMN_NAME +
            " - "+ Contract.UniversityEntry.COLUMN_LASTNAME + " - " + Contract.UniversityEntry.COLUMN_UNIVERSITY +"\n");

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

                textView.append("\n" + id + " - " + name + " - " + lastName +
                " - " + nickName + " - " + gender + " - " + university);
            }

        }
        finally {
            cursor.close();
        }

    }

    private void insertData(){
        SQLiteDatabase database = dbwar.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.UniversityEntry.COLUMN_NAME, "Olzhas");
        contentValues.put(Contract.UniversityEntry.COLUMN_LASTNAME, "Bekturganov");
        contentValues.put(Contract.UniversityEntry.COLUMN_NICKNAME, "Barathum");
        contentValues.put(Contract.UniversityEntry.COLUMN_UNIVERSITY, "Eurasia Internation University");
        contentValues.put(Contract.UniversityEntry.COLUMN_GENGER, Contract.UniversityEntry.Gender_Man);

       long newRowId = database.insert(Contract.UniversityEntry.TABLE_NAME, null, contentValues);


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
                displatBaseInfo();
                break;


        }

        return true;
    }
}
