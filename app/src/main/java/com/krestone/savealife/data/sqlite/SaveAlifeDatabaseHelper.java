package com.krestone.savealife.data.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class SaveAlifeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "saveAlifeDatabase";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONTACTS = "contacts";

    private static final String KEY_CONTACT_ID = "id";
    private static final String KEY_CONTACT_NAME = "name";
    private static final String KEY_CONTACT_NUMBER = "number";
    private static final String KEY_PROFILE_IMAGE_URI = "thumbnail";

    private static SaveAlifeDatabaseHelper helper;

    private SaveAlifeDatabaseHelper(Context context) {
        super(context, null, null, 1);
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
                KEY_CONTACT_NUMBER + " INTEGER," +
                KEY_PROFILE_IMAGE_URI + " TEXT" +
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
