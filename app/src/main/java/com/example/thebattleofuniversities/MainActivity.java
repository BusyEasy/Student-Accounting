package com.example.thebattleofuniversities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebattleofuniversities.DbHelper.Contract;
import com.example.thebattleofuniversities.DbHelper.DbWar;

public class MainActivity extends AppCompatActivity {

   EditText EditName, LasnNameEdit, NickEdit;
   Button battle;
   Spinner spinnerUniversity, spinnerGender;
   int mGender;
   String universityName;
    DbWar databaseConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditName = (EditText)findViewById(R.id.EditName);
        LasnNameEdit = (EditText)findViewById(R.id.LasnNameEdit);
        NickEdit = (EditText)findViewById(R.id.NickEdit);

        battle = (Button)findViewById(R.id.battle);

        spinnerUniversity = (Spinner)findViewById(R.id.spinnerUniversity);
        spinnerGender = (Spinner)findViewById(R.id.spinnerGender);

        databaseConnect = new DbWar(this);

        final String []gender = {"Мужской", "Женский"};

        String []universityList = {"Евразийский Национальный университет", "Казгу", "Таргу", "Назарбаев университет"};


        ArrayAdapter<String>getAdapterUniversity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, universityList);
        ArrayAdapter<String>getadaperGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);

        spinnerUniversity.setAdapter(getAdapterUniversity);
        spinnerGender.setAdapter(getadaperGender);


        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String genderchoose = (String) parent.getItemAtPosition(position);

                switch (genderchoose){

                    case "Мужской":
                        mGender = Contract.UniversityEntry.Gender_Man;
                        break;
                    case "Женский":
                        mGender = Contract.UniversityEntry.Gender_Girl;
                            break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String universchoose = (String) parent.getItemAtPosition(position);

                if(!TextUtils.isEmpty(universchoose)){

                   switch (universchoose){
                       case "Евразийский Национальный университет":
                           universityName = spinnerUniversity.getSelectedItem().toString();
                           break;
                       case "Казгу":
                           universityName = spinnerUniversity.getSelectedItem().toString();;
                           break;
                       case "Таргу":
                          universityName = spinnerUniversity.getSelectedItem().toString();;
                           break;

                       case "Назарбаев университет":
                           universityName = spinnerUniversity.getSelectedItem().toString();;
                           break;
                       default:
                           break;

                   }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudent();
                finish();
            }
        });
    }

    private void insertStudent(){

        String name = EditName.getText().toString();
        String lastName = LasnNameEdit.getText().toString();
        String nickName = NickEdit.getText().toString();


        SQLiteDatabase sqldb = databaseConnect.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.UniversityEntry.COLUMN_NAME, name);
        contentValues.put(Contract.UniversityEntry.COLUMN_LASTNAME, lastName);
        contentValues.put(Contract.UniversityEntry.COLUMN_NICKNAME, nickName);
        contentValues.put(Contract.UniversityEntry.COLUMN_UNIVERSITY, universityName);
        contentValues.put(Contract.UniversityEntry.COLUMN_GENGER, mGender);


        long addStudentdb = sqldb.insert(Contract.UniversityEntry.TABLE_NAME, null, contentValues);

        if(addStudentdb==-1){
            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
        }


    }

}
