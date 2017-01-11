package com.krestone.savealife.data.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_CONTACT_ID;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_CONTACT_NAME;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_CONTACT_NUMBER;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_IS_CONSISTENT;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_IS_IN_APP;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.KEY_PROFILE_IMAGE_URI;
import static com.krestone.savealife.data.sqlite.EmergencyContactsTable.TABLE_CONTACTS;

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
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                "(" +
                KEY_CONTACT_ID + " INTEGER PRIMARY KEY," +
                KEY_CONTACT_NAME + " TEXT," +
                KEY_CONTACT_NUMBER + " TEXT," +
                KEY_PROFILE_IMAGE_URI + " TEXT," +
                KEY_IS_CONSISTENT + " INTEGER," +
                KEY_IS_IN_APP + " INTEGER" +
                ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }
    }
}
