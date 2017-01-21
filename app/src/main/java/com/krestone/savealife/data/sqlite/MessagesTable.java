package com.krestone.savealife.data.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.krestone.savealife.data.sqlite.models.HelpIntentModel;
import com.krestone.savealife.data.sqlite.models.SosModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessagesTable {

    public static final int MESSAGE_TYPE_SOS_START = 0;
    public static final int MESSAGE_TYPE_SOS_STOP = 1;
    public static final int MESSAGE_TYPE_INTENT_START = 2;
    public static final int MESSAGE_TYPE_INTENT_STOP = 3;

    static final String TABLE_MESSAGES = "tableMessages";

    /** common fields*/
    static final String KEY_MESSAGE_ID = "messageId";
    static final String KEY_FIRST_NAME = "firstName";
    static final String KEY_LAST_NAME = "lastName";
    static final String KEY_MESSAGE_TYPE = "messageType";
    static final String KEY_PHONE_NUMBER = "phoneNumber";
    static final String KEY_TIME = "time";

    /** sos specific fields*/
    static final String KEY_LATITUDE = "latitude";
    static final String KEY_LONGITUDE = "longitude";
    static final String KEY_MESSAGE_TEXT = "messageText";

    /** help intent specific fields*/
    static final String KEY_DISTANCE = "distance";

    private SQLiteOpenHelper helper;

    public MessagesTable(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public List<Object> getAllRecords() {
        List<Object> messages = Collections.emptyList();

        String selectQuery = String.format("SELECT * FROM %S", TABLE_MESSAGES);

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                messages = parseCursor(cursor);
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return messages;
    }

    private List<Object> parseCursor(Cursor cursor) {
        List<Object> messages = new ArrayList<>();

        int messageTypeIndex = cursor.getColumnIndex(KEY_MESSAGE_TYPE);

        do {
            int messageType = cursor.getInt(messageTypeIndex);

            if (messageType == MESSAGE_TYPE_INTENT_START || messageType == MESSAGE_TYPE_INTENT_STOP) {
                messages.add(parseHelpIntentModel(cursor, messageType));
            } else if (messageType == MESSAGE_TYPE_SOS_START || messageType == MESSAGE_TYPE_SOS_STOP) {
                messages.add(parseSosModel(cursor, messageType));
            }
        } while (cursor.moveToNext());

        return messages;
    }

    private SosModel parseSosModel(Cursor cursor, int messageType) {
        SosModel sosModel = new SosModel();

        sosModel.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
        sosModel.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
        sosModel.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
        sosModel.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
        sosModel.setLatitude(cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)));
        sosModel.setLongitude(cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE)));
        sosModel.setMessage(cursor.getString(cursor.getColumnIndex(KEY_MESSAGE_TEXT)));

        if (messageType == MESSAGE_TYPE_SOS_START) {
            sosModel.setStart(true);
        } else {
            sosModel.setStart(false);
        }
        return sosModel;
    }

    private HelpIntentModel parseHelpIntentModel(Cursor cursor, int messageType) {
        HelpIntentModel helpIntentModel = new HelpIntentModel();

        helpIntentModel.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
        helpIntentModel.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
        helpIntentModel.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
        helpIntentModel.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
        helpIntentModel.setDistance(cursor.getDouble(cursor.getColumnIndex(KEY_DISTANCE)));

        if (messageType == MESSAGE_TYPE_INTENT_START) {
            helpIntentModel.setStart(true);
        } else {
            helpIntentModel.setStart(false);
        }
        return helpIntentModel;
    }

    private void addSosMessages(List<SosModel> sosMessages) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();

            for (SosModel sosModel : sosMessages) {
                contentValues.put(KEY_FIRST_NAME, sosModel.getFirstName());
                contentValues.put(KEY_LAST_NAME, sosModel.getLastName());
                contentValues.put(KEY_MESSAGE_TYPE, sosModel.getGlobalMessageType());
                contentValues.put(KEY_PHONE_NUMBER, sosModel.getPhoneNumber());
                contentValues.put(KEY_TIME, sosModel.getTime());
                contentValues.put(KEY_LATITUDE, sosModel.getLatitude());
                contentValues.put(KEY_LONGITUDE, sosModel.getLongitude());
                contentValues.put(KEY_MESSAGE_TEXT, sosModel.getMessage());

                db.insert(TABLE_MESSAGES, null, contentValues);
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
    }

    private void addHelpIntentMessages(List<HelpIntentModel> helpIntentMessages) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();

            for (HelpIntentModel helpIntentModel : helpIntentMessages) {
                contentValues.put(KEY_FIRST_NAME, helpIntentModel.getFirstName());
                contentValues.put(KEY_LAST_NAME, helpIntentModel.getLastName());
                contentValues.put(KEY_MESSAGE_TYPE, helpIntentModel.getGlobalMessageType());
                contentValues.put(KEY_PHONE_NUMBER, helpIntentModel.getPhoneNumber());
                contentValues.put(KEY_TIME, helpIntentModel.getTime());
                contentValues.put(KEY_DISTANCE, helpIntentModel.getDistance());

                db.insert(TABLE_MESSAGES, null, contentValues);
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
    }
}
