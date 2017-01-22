package com.krestone.savealife.data.sqlite.models;


import android.os.Parcelable;

public abstract class AbstractMessage implements Parcelable {

    public static final int HELP_INTENT_MESSAGE = 0;

    public static final int SOS_MESSAGE = 1;

    public abstract int getMessageType();
}
