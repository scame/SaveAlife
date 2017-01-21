package com.krestone.savealife.data.sqlite;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.krestone.savealife.data.sqlite.models.HelpIntentMessageModel;
import com.krestone.savealife.data.sqlite.models.SosMessageModel;

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
    public static final String KEY_MESSAGE_ID = "messageId";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_MESSAGE_TYPE = "messageType";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_TIME = "time";

    /** sos specific fields*/
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_MESSAGE_TEXT = "message";

    /** help intent specific fields*/
    public static final String KEY_DISTANCE = "distance";

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

    private SosMessageModel parseSosModel(Cursor cursor, int messageType) {
        SosMessageModel sosMessageModel = new SosMessageModel();

        sosMessageModel.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
        sosMessageModel.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
        sosMessageModel.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
        sosMessageModel.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
        sosMessageModel.setLatitude(cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)));
        sosMessageModel.setLongitude(cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE)));
        sosMessageModel.setMessage(cursor.getString(cursor.getColumnIndex(KEY_MESSAGE_TEXT)));

        if (messageType == MESSAGE_TYPE_SOS_START) {
            sosMessageModel.setStart(true);
        }

        return sosMessageModel;
    }

    private HelpIntentMessageModel parseHelpIntentModel(Cursor cursor, int messageType) {
        HelpIntentMessageModel helpIntentMessageModel = new HelpIntentMessageModel();

        helpIntentMessageModel.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
        helpIntentMessageModel.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
        helpIntentMessageModel.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
        helpIntentMessageModel.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
        helpIntentMessageModel.setDistance(cursor.getDouble(cursor.getColumnIndex(KEY_DISTANCE)));

        if (messageType == MESSAGE_TYPE_INTENT_START) {
            helpIntentMessageModel.setStart(true);
        }

        return helpIntentMessageModel;
    }

    public void addSosMessages(List<SosMessageModel> sosMessages) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();

            for (SosMessageModel sosMessageModel : sosMessages) {
                contentValues.put(KEY_FIRST_NAME, sosMessageModel.getFirstName());
                contentValues.put(KEY_LAST_NAME, sosMessageModel.getLastName());
                contentValues.put(KEY_MESSAGE_TYPE, sosMessageModel.getGlobalMessageType());
                contentValues.put(KEY_PHONE_NUMBER, sosMessageModel.getPhoneNumber());
                contentValues.put(KEY_TIME, sosMessageModel.getTime());
                contentValues.put(KEY_LATITUDE, sosMessageModel.getLatitude());
                contentValues.put(KEY_LONGITUDE, sosMessageModel.getLongitude());
                contentValues.put(KEY_MESSAGE_TEXT, sosMessageModel.getMessage());

                db.insert(TABLE_MESSAGES, null, contentValues);
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
    }

    public void addHelpIntentMessages(List<HelpIntentMessageModel> helpIntentMessages) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();

            for (HelpIntentMessageModel helpIntentMessageModel : helpIntentMessages) {
                contentValues.put(KEY_FIRST_NAME, helpIntentMessageModel.getFirstName());
                contentValues.put(KEY_LAST_NAME, helpIntentMessageModel.getLastName());
                contentValues.put(KEY_MESSAGE_TYPE, helpIntentMessageModel.getGlobalMessageType());
                contentValues.put(KEY_PHONE_NUMBER, helpIntentMessageModel.getPhoneNumber());
                contentValues.put(KEY_TIME, helpIntentMessageModel.getTime());
                contentValues.put(KEY_DISTANCE, helpIntentMessageModel.getDistance());

                db.insert(TABLE_MESSAGES, null, contentValues);
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
    }
}
