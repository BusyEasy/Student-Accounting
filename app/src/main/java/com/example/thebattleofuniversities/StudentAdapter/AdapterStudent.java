package com.example.thebattleofuniversities.StudentAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.thebattleofuniversities.DbHelper.Contract;
import com.example.thebattleofuniversities.R;


/**
 * Created by Олжас on 22.11.2017.
 */

public class AdapterStudent  extends CursorAdapter{

    public AdapterStudent(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView)view.findViewById(R.id.StudentNameAdapter);
        TextView lastName = (TextView)view.findViewById(R.id.StudentLastNameAdapter);
        TextView universityName = (TextView)view.findViewById(R.id.UniversityAdapter);
        TextView nickName = (TextView)view.findViewById(R.id.NickNameAdapter);

        int nameColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_NAME);
        int lastNameColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_LASTNAME);
        int universityColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_UNIVERSITY);
        int nickNameColumnIndex = cursor.getColumnIndex(Contract.UniversityEntry.COLUMN_NICKNAME);

        String nameStudent  = cursor.getString(nameColumnIndex);
        String lastNameStudent = cursor.getString(lastNameColumnIndex);
        String nickNameStudent = cursor.getString(nickNameColumnIndex);
        String universityNameStudent = cursor.getString(universityColumnIndex);

        name.setText(nameStudent);
        lastName.setText(lastNameStudent);
        universityName.setText(universityNameStudent);
        nickName.setText(nickNameStudent);

    }
}
