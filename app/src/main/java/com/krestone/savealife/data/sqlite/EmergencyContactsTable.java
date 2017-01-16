package com.krestone.savealife.data.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.krestone.savealife.data.sync.states.DataStates;
import com.krestone.savealife.data.sync.states.InAppContact;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

import rx.Completable;

public class EmergencyContactsTable {

    static final String TABLE_CONTACTS = "contacts";

    static final String KEY_CONTACT_ID = "id";
    static final String KEY_CONTACT_NAME = "name";
    static final String KEY_CONTACT_NUMBER = "number";
    static final String KEY_PROFILE_IMAGE_URI = "thumbnail";
    static final String KEY_IS_IN_APP = "isinapp";
    static final String KEY_DATA_STATE = "datastate";

    private SaveAlifeDatabaseHelper helper;

    public EmergencyContactsTable(SaveAlifeDatabaseHelper helper) {
        this.helper = helper;
    }


    public Completable addOrUpdateLocalContacts(List<ContactModel> contacts) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            for (ContactModel contact : contacts) {
                cv.put(KEY_CONTACT_NAME, contact.getName());
                cv.put(KEY_CONTACT_NUMBER, contact.getPhoneNumber());

                if (contact.getThumbnailUri() == null) {
                    cv.put(KEY_PROFILE_IMAGE_URI, "");
                } else {
                    cv.put(KEY_PROFILE_IMAGE_URI, contact.getThumbnailUri());
                }

                if (contact.getInAppState() == null) {
                    cv.put(KEY_IS_IN_APP, InAppContact.UNSPECIFIED.ordinal());
                } else {
                    cv.put(KEY_IS_IN_APP, contact.getInAppState().ordinal());
                }

                // null data state is possible only in case of server's response
                if (contact.getDataState() == null) {
                    cv.put(KEY_DATA_STATE, DataStates.UP_TO_DATE.ordinal());
                } else {
                    cv.put(KEY_DATA_STATE, contact.getDataState().ordinal());
                }

                int rows = db.update(TABLE_CONTACTS, cv,
                        KEY_CONTACT_NUMBER + " = ?", new String[] {contact.getPhoneNumber()});

                if (rows != 1) {
                    db.insert(TABLE_CONTACTS, null, cv);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return Completable.complete();
    }

    public List<ContactModel> getLocalEmergencyContactsByState(DataStates dataState) {
        String contactsSelectQuery;

        contactsSelectQuery = String.format("SELECT * FROM %S WHERE " +
                KEY_DATA_STATE + " = " + dataState.ordinal(), TABLE_CONTACTS);

        return getLocalEmergencyContacts(contactsSelectQuery);
    }

    public List<ContactModel> getLocalEmergencyContacts() {
        String contactsSelectQuery;

        contactsSelectQuery = String.format("SELECT * FROM %S WHERE " +
                KEY_DATA_STATE + " = " + DataStates.UP_TO_DATE.ordinal() + " OR " +
                KEY_DATA_STATE + " = " + DataStates.NEW.ordinal(), TABLE_CONTACTS);

        return getLocalEmergencyContacts(contactsSelectQuery);
    }

    private List<ContactModel> getLocalEmergencyContacts(String selectQuery) {
        List<ContactModel> contacts = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ContactModel contactModel = new ContactModel();
                    contactModel.setName(cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NAME)));
                    contactModel.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NUMBER)));
                    contactModel.setThumbnailUri(cursor.getString(cursor.getColumnIndex(KEY_PROFILE_IMAGE_URI)));
                    contactModel.setDataState(DataStates.fromInteger(cursor.getInt(cursor.getColumnIndex(KEY_DATA_STATE))));
                    contactModel.setInAppState(InAppContact.fromInteger(cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_APP))));
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

    public Completable deleteAllLocalContacts() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_CONTACTS, null, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return Completable.complete();
    }

    public Completable deleteLocalContacts(List<ContactModel> contacts) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        try {
            for (ContactModel contact : contacts) {
                db.delete(TABLE_CONTACTS, KEY_CONTACT_NUMBER + " = ?", new String[]{contact.getPhoneNumber()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return Completable.complete();
    }

    public Completable updateDataState(List<ContactModel> contacts) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues(contacts.size());

        for (ContactModel contactModel : contacts) {
            contentValues.put(KEY_DATA_STATE, contactModel.getDataState().ordinal());
            db.update(TABLE_CONTACTS, contentValues, KEY_CONTACT_NUMBER + " = ?",
                    new String[]{contactModel.getPhoneNumber()});
        }
        return Completable.complete();
    }
}
