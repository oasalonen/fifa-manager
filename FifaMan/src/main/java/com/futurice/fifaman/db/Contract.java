package com.futurice.fifaman.db;

import android.provider.BaseColumns;

/**
 * Created by Olli on 03/10/13.
 */
public final class Contract {

    public static abstract class PlayerTable implements BaseColumns {
        public static final String TABLE_NAME = "player";
        public static final String COLUMN_NAME_PLAYER_NAME = "name";
    }

    public static final String SQL_CREATE_PLAYER_TABLE =
            "CREATE TABLE " + PlayerTable.TABLE_NAME + " (" +
            PlayerTable._ID + " INTEGER PRIMARY KEY," +
            PlayerTable.COLUMN_NAME_PLAYER_NAME + " TEXT)";

    public static final String SQL_DELETE_PLAYER_TABLE =
            "DROP TABLE IF EXISTS " + PlayerTable.TABLE_NAME;
}
