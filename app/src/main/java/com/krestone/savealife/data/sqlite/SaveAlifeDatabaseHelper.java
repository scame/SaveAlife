package com.krestone.savealife.data.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class SaveAlifeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "saveAlifeDatabase";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONTACTS = "contacts";

    private static final String KEY_CONTACT_ID = "id";
    private static final String KEY_CONTACT_NAME = "name";
    private static final String KEY_CONTACT_NUMBER = "number";
    private static final String KEY_PROFILE_IMAGE_URI = "thumbnail";
    private static final String KEY_IS_IN_APP = "isinapp";
    private static final String KEY_IS_IN_EMERGENCY = "isinemergency";

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
                KEY_IS_IN_EMERGENCY + " INTEGER," +
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

    public void addContact(ContactModel contact) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_CONTACT_NAME, contact.getName());
            contentValues.put(KEY_CONTACT_NUMBER, contact.getMobileNumber());
            contentValues.put(KEY_PROFILE_IMAGE_URI, contact.getThumbnailUri());
            contentValues.put(KEY_IS_IN_EMERGENCY, contact.isInEmergencyList() ? 1 : 0);
            contentValues.put(KEY_IS_IN_APP, contact.isInApp() ? 1 : 0);

            db.insertOrThrow(TABLE_CONTACTS, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.i("onxAddContactErr", e.getLocalizedMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void addContacts(List<ContactModel> contacts) {
        String insertStatement = "INSERT INTO " + TABLE_CONTACTS + " VALUES (?, ?, ?, ?, ?, ?);";
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement sqLiteStatement = db.compileStatement(insertStatement);

        db.beginTransaction();
        try {
            for (ContactModel contact : contacts) {
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(2, contact.getName());
                sqLiteStatement.bindString(3, contact.getMobileNumber());
                if (contact.getThumbnailUri() != null) { // default bind value is null, so it's fine to just skip this one
                    sqLiteStatement.bindString(4, contact.getThumbnailUri());
                }
                sqLiteStatement.bindLong(5, contact.isInEmergencyList() ? 1 : 0);
                sqLiteStatement.bindLong(6, contact.isInApp() ? 1 : 0);

                sqLiteStatement.execute();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public List<ContactModel> getAllEmergencyContacts() {
        List<ContactModel> contacts = new ArrayList<>();

        String CONTACTS_SELECT_QUERY = String.format("SELECT * FROM %S", TABLE_CONTACTS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CONTACTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
              do {
                  ContactModel contactModel = new ContactModel();
                  contactModel.setName(cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NAME)));
                  contactModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NUMBER)));
                  contactModel.setThumbnailUri(cursor.getString(cursor.getColumnIndex(KEY_PROFILE_IMAGE_URI)));
                  contactModel.setInEmergencyList(cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_EMERGENCY)) == 1);
                  contactModel.setInApp(cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_APP)) == 1);
                  contacts.add(contactModel);
              } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return contacts;
    }

    public void deleteAllContacts() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_CONTACTS, null, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
