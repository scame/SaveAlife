package com.krestone.savealife.data.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class EmergencyContactsTable {

    static final String TABLE_CONTACTS = "contacts";

    static final String KEY_CONTACT_ID = "id";
    static final String KEY_CONTACT_NAME = "name";
    static final String KEY_CONTACT_NUMBER = "number";
    static final String KEY_PROFILE_IMAGE_URI = "thumbnail";
    static final String KEY_IS_IN_APP = "isinapp";
    static final String KEY_IS_MODIFIED = "ismodified";

    private SaveAlifeDatabaseHelper helper;

    public EmergencyContactsTable(SaveAlifeDatabaseHelper helper) {
        this.helper = helper;
    }

    public void addContact(ContactModel contact) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_CONTACT_NAME, contact.getName());
            contentValues.put(KEY_CONTACT_NUMBER, contact.getMobileNumber());
            contentValues.put(KEY_PROFILE_IMAGE_URI, contact.getThumbnailUri());
            contentValues.put(KEY_IS_MODIFIED, contact.isModified() ? 1 : 0);
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
        SQLiteDatabase db = helper.getWritableDatabase();
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
                sqLiteStatement.bindLong(5, contact.isModified() ? 1 : 0);
                sqLiteStatement.bindLong(6, contact.isInApp() ? 1 : 0);

                sqLiteStatement.execute();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public List<ContactModel> getEmergencyContacts(boolean onlyModified) {
        String contactsSelectQuery;
        if (onlyModified) {
            contactsSelectQuery = String.format("SELECT * FROM %S WHERE " + KEY_IS_MODIFIED + " = 1", TABLE_CONTACTS);
        } else {
            contactsSelectQuery = String.format("SELECT * FROM %S", TABLE_CONTACTS);
        }
        return getEmergencyContacts(contactsSelectQuery);
    }

    private List<ContactModel> getEmergencyContacts(String selectQuery) {
        List<ContactModel> contacts = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ContactModel contactModel = new ContactModel();
                    contactModel.setName(cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NAME)));
                    contactModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NUMBER)));
                    contactModel.setThumbnailUri(cursor.getString(cursor.getColumnIndex(KEY_PROFILE_IMAGE_URI)));
                    contactModel.setModified(cursor.getInt(cursor.getColumnIndex(KEY_IS_MODIFIED)) == 1);
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
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_CONTACTS, null, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void markAsNotModified(List<ContactModel> contacts) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues(contacts.size());
        contentValues.put(KEY_IS_MODIFIED, 0);

        for (ContactModel contactModel : contacts) {
            db.update(TABLE_CONTACTS, contentValues, KEY_CONTACT_NUMBER + " = ?",
                    new String[]{contactModel.getMobileNumber()});
        }
    }
}
