package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.data.sqlite.models.AbstractMessage;

import java.util.List;

public interface NotificationsPresenter<T> extends Presenter<T> {

    interface NotificationsView {

        void displayMessages(List<AbstractMessage> messages);

        void onError(String error);
    }

    void requestMessages();
}
