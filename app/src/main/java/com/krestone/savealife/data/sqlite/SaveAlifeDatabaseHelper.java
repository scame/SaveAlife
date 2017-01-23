package com.krestone.savealife.data.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_CONTACT_ID;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_CONTACT_NAME;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_CONTACT_NUMBER;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_DATA_STATE;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_IS_IN_APP;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_PROFILE_IMAGE_URI;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.TABLE_CONTACTS;

import static com.krestone.savealife.data.sqlite.MessagesTable.*;

public class SaveAlifeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "saveAlifeDatabase";

    private static final int DATABASE_VERSION = 1;

    private static SaveAlifeDatabaseHelper helper;

    private SaveAlifeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SaveAlifeDatabaseHelper getInstance(Context context) {
        if (helper == null) {
            helper = new SaveAlifeDatabaseHelper(context.getApplicationContext());
        }
        return helper;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = getContactsTableCreateStatement();
        String CREATE_MESSAGES_TABLE = getMessagesTableCreateStatement();

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_MESSAGES_TABLE);
    }

    private String getContactsTableCreateStatement() {
        return "CREATE TABLE " + TABLE_CONTACTS +
                "(" +
                KEY_CONTACT_ID + " INTEGER PRIMARY KEY," +
                KEY_CONTACT_NAME + " TEXT," +
                KEY_CONTACT_NUMBER + " TEXT," +
                KEY_PROFILE_IMAGE_URI + " TEXT," +
                KEY_DATA_STATE + " INTEGER," +
                KEY_IS_IN_APP + " INTEGER" +
                ")";
    }

    private String getMessagesTableCreateStatement() {
        return "CREATE TABLE " + TABLE_MESSAGES +
                "(" +
                KEY_MESSAGE_ID + " INTEGER PRIMARY KEY," +
                KEY_FIRST_NAME + " TEXT," +
                KEY_LAST_NAME + " TEXT," +
                KEY_MESSAGE_TYPE + " INTEGER," +
                KEY_PHONE_NUMBER + " TEXT," +
                KEY_TIME + " TEXT," +
                KEY_LATITUDE + " REAL," +
                KEY_LONGITUDE + " REAL," +
                KEY_MESSAGE_TEXT + " TEXT," +
                KEY_DISTANCE + " REAL" +
                ")";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
            onCreate(db);
        }
    }
}
