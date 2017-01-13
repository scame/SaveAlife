package com.krestone.savealife.data.sync.events;


import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

public class SyncEvent implements Parcelable {

    public static final String SYNC_EVENT = "syncEvent";

    private SyncType syncType;
    private SyncStatus syncStatus;

    public SyncEvent(SyncType syncType, SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
        this.syncType = syncType;
    }

    public static void send(@NonNull final SyncType type, @NonNull final SyncStatus status,
                            @NonNull final Context context) {
        Intent intent = new Intent();
        intent.putExtra(SYNC_EVENT, new SyncEvent(type, status));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public SyncType getSyncType() {
        return syncType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.syncType == null ? -1 : this.syncType.ordinal());
        dest.writeInt(this.syncStatus == null ? -1 : this.syncStatus.ordinal());
    }

    protected SyncEvent(Parcel in) {
        int tmpSyncType = in.readInt();
        this.syncType = tmpSyncType == -1 ? null : SyncType.values()[tmpSyncType];
        int tmpSyncStatus = in.readInt();
        this.syncStatus = tmpSyncStatus == -1 ? null : SyncStatus.values()[tmpSyncStatus];
    }

    public static final Creator<SyncEvent> CREATOR = new Creator<SyncEvent>() {
        @Override
        public SyncEvent createFromParcel(Parcel source) {
            return new SyncEvent(source);
        }

        @Override
        public SyncEvent[] newArray(int size) {
            return new SyncEvent[size];
        }
    };
}
