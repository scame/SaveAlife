package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.messages.GetMessagesUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.NotificationsPresenter;
import com.krestone.savealife.presentation.presenters.NotificationsPresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.krestone.savealife.presentation.presenters.NotificationsPresenter.NotificationsView;

@Module
public class NotificationsModule {

    @Provides
    @PerActivity
    NotificationsPresenter<NotificationsView> provideNotificationsPresenter(GetMessagesUseCase messagesUseCase) {
        return new NotificationsPresenterImpl<>(messagesUseCase);
    }

    @Provides
    @PerActivity
    GetMessagesUseCase provideMessagesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                              MessagesRepository messagesRepository) {
        return new GetMessagesUseCase(subscribeOn, observeOn, messagesRepository);
    }
}
