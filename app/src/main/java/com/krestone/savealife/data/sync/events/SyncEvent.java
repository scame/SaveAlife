package com.krestone.savealife.data.sync.events;


import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import rx.Completable;

public class SyncEvent implements Parcelable {

    private SyncType syncType;
    private SyncStatus syncStatus;

    public SyncEvent(SyncType syncType, SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
        this.syncType = syncType;
    }

    public static Completable send(@NonNull final SyncType type, @NonNull final SyncStatus status,
                                   @NonNull final Context context) {
        Intent intent = new Intent(BroadcastsMeta.SYNC_RESPONSE);
        intent.putExtra(BroadcastsMeta.SYNC_RESPONSE_EXTRA, new SyncEvent(type, status));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        return Completable.complete();
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
