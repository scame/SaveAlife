package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.domain.usecases.messages.GetMessagesUseCase;

public class NotificationsPresenterImpl<T extends NotificationsPresenter.NotificationsView>
        implements NotificationsPresenter<T> {

    private T view;

    private GetMessagesUseCase getMessagesUseCase;

    public NotificationsPresenterImpl(GetMessagesUseCase getMessagesUseCase) {
        this.getMessagesUseCase = getMessagesUseCase;
    }

    @Override
    public void requestMessages() {
        getMessagesUseCase.executeSingle(messages -> {
            if (view != null) view.displayMessages(messages);
        }, throwable -> {
            if (view != null) view.onError(throwable.getLocalizedMessage());
        });
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        getMessagesUseCase.unsubscribe();
    }
}
