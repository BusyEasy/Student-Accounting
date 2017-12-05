package com.example.thebattleofuniversities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thebattleofuniversities.DbHelper.Contract;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText EditName, LastNameEdit, NickEdit;
    Button battle;
    Spinner spinnerUniversity, spinnerGender;
    String mGender;
    String universityName;
    Uri uriIntent;
    public static final int EDITOR_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditName = (EditText) findViewById(R.id.EditName);
        LastNameEdit = (EditText) findViewById(R.id.LasnNameEdit);
        NickEdit = (EditText) findViewById(R.id.NickEdit);


        battle = (Button) findViewById(R.id.battle);

        spinnerUniversity = (Spinner) findViewById(R.id.spinnerUniversity);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);


        Intent intent = getIntent();

        uriIntent = intent.getData();

        if (uriIntent == null) {
            setTitle("Add a Student");
        } else {
            setTitle("Edit a Student");
            getLoaderManager().initLoader(EDITOR_LOADER, null, this);
        }
        final String[] gender = {"Мужской", "Женский"};

        String[] universityList = {"Евразийский Национальный университет", "Казгу", "Таргу", "Назарбаев университет"};


        ArrayAdapter<String> getAdapterUniversity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, universityList);
        ArrayAdapter<String> getadaperGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);

        spinnerUniversity.setAdapter(getAdapterUniversity);
        spinnerGender.setAdapter(getadaperGender);


        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String genderchoose = (String) parent.getItemAtPosition(position);

                switch (genderchoose) {

                    case "Мужской":
                        mGender = String.valueOf(Contract.UniversityEntry.Gender_Man);
                        break;
                    case "Женский":
                        mGender = String.valueOf(Contract.UniversityEntry.Gender_Girl);
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

                if (!TextUtils.isEmpty(universchoose)) {

                    switch (universchoose) {
                        case "Евразийский Национальный университет":
                            universityName = spinnerUniversity.getSelectedItem().toString();
                            break;
                        case "Казгу":
                            universityName = spinnerUniversity.getSelectedItem().toString();
                            ;
                            break;
                        case "Таргу":
                            universityName = spinnerUniversity.getSelectedItem().toString();
                            ;
                            break;

                        case "Назарбаев университет":
                            universityName = spinnerUniversity.getSelectedItem().toString();
                            ;
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
                saveStudent();
                finish();
            }
        });


    }


    private void saveStudent() {

        ContentValues contentValues = new ContentValues();

        String nickName = NickEdit.getText().toString().trim();
        String name = EditName.getText().toString().trim();
        String lastName = LastNameEdit.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {

            return;
        } else if (TextUtils.isEmpty(nickName)) {
            return;
        } else if (TextUtils.isEmpty(lastName)) {
            return;
        } else if (TextUtils.isEmpty(universityName)) {
            return;
        } else if (TextUtils.isEmpty(mGender)) {
            return;
        } else {
            contentValues.put(Contract.UniversityEntry.COLUMN_NICKNAME, nickName);
            contentValues.put(Contract.UniversityEntry.COLUMN_NAME, name);
            contentValues.put(Contract.UniversityEntry.COLUMN_GENGER, mGender);
            contentValues.put(Contract.UniversityEntry.COLUMN_UNIVERSITY, universityName);
            contentValues.put(Contract.UniversityEntry.COLUMN_LASTNAME, lastName);

            if (uriIntent == null) {

                Uri uri = getContentResolver().insert(Contract.UniversityEntry.CONTENT_URI, contentValues);
                invalidateOptionsMenu();

                if (uri == null) {
                    Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, getString(R.string.succes_message), Toast.LENGTH_SHORT).show();

                }
            } else {

                int updateLoader = getContentResolver().update(uriIntent, contentValues, null, null);

                if (updateLoader == 0) {
                    Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.succes_message), Toast.LENGTH_SHORT).show();
                }

            }
        }

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String projection[] = {Contract.UniversityEntry._ID, Contract.UniversityEntry.COLUMN_NAME,
                Contract.UniversityEntry.COLUMN_NICKNAME, Contract.UniversityEntry.COLUMN_LASTNAME,
                Contract.UniversityEntry.COLUMN_UNIVERSITY
        };


        return new CursorLoader(getApplicationContext(), uriIntent, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.moveToFirst()) {

            int columnNameIndex = data.getColumnIndex(Contract.UniversityEntry.COLUMN_NAME);
            int columnNickName = data.getColumnIndex(Contract.UniversityEntry.COLUMN_NICKNAME);
            int columnLastName = data.getColumnIndex(Contract.UniversityEntry.COLUMN_LASTNAME);
            int columnUniversityName = data.getColumnIndex(Contract.UniversityEntry.COLUMN_UNIVERSITY);

            String name = data.getString(columnNameIndex);
            String lastName = data.getString(columnLastName);
            String nickName = data.getString(columnNickName);
            String university = data.getString(columnUniversityName);

            EditName.setText(name);
            LastNameEdit.setText(lastName);
            NickEdit.setText(nickName);

        }

    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        EditName.setText("");
        NickEdit.setText("");
        LastNameEdit.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editemenu, menu);

        return true;
    }
// После добавления этого метода кнопка Назад не работает
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.deleteStudent:
              deleteStudent();
                break;


        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
         super.onPrepareOptionsMenu(menu);

        if(uriIntent==null){

            MenuItem menuItem = menu.findItem(R.id.deleteStudent);
            menuItem.setVisible(false);


        }

        return true;
    }

    private void deleteStudent(){

        if(uriIntent!=null) {

            int delete = getContentResolver().delete(uriIntent, null, null);

            if (delete == 0) {
                Toast.makeText(getApplicationContext(), (R.string.error_message), Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), (R.string.succes_message), Toast.LENGTH_SHORT).show();


            }
            finish();
        }

    }


}
