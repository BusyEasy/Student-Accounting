package com.example.thebattleofuniversities.DbHelper;

import android.provider.BaseColumns;

/**
 * Created by Олжас on 27.10.2017.
 */

public final class Contract {

    public static class UniversityEntry implements BaseColumns{


        public static final String _ID = BaseColumns._ID;
        public static final String  TABLE_NAME = "Student";
        public static final String  COLUMN_NAME  = "name";
        public static final String  COLUMN_LASTNAME  = "last_name";
        public static final String  COLUMN_NICKNAME  = "nick_name";
        public static final String  COLUMN_GENGER  = "gender";
        public static final String  COLUMN_UNIVERSITY  = "university";


        public static final int Gender_Man = 1;
        public static final int Gender_Girl = 2;





    }



}
