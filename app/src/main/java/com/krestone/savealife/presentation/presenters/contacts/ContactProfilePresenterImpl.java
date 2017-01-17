package com.krestone.savealife.presentation.presenters.contacts;


import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.domain.usecases.profiles.GetSomeoneProfileInfoUseCase;
import com.krestone.savealife.util.ConnectivityUtil;

public class ContactProfilePresenterImpl<T extends ContactProfilePresenter.ContactProfileView>
        implements ContactProfilePresenter<T> {

    private T view;

    private GetSomeoneProfileInfoUseCase someoneProfileInfoUseCase;

    public ContactProfilePresenterImpl(GetSomeoneProfileInfoUseCase someoneProfileInfoUseCase) {
        this.someoneProfileInfoUseCase = someoneProfileInfoUseCase;
    }

    @Override
    public void requestProfileInfo(String phoneNumber) {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            someoneProfileInfoUseCase.setPhoneNumber(phoneNumber);
            requestProfileInfo();
        } else if (view != null) {
            view.onError(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void requestProfileInfo() {
        someoneProfileInfoUseCase.executeSingle(profileEntity -> {
            if (view != null) view.displayProfileInfo(profileEntity);
        }, throwable -> {
            if (view != null) view.onError(throwable.getLocalizedMessage());
        });
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void progressDialogCancel() {
        someoneProfileInfoUseCase.unsubscribe();
    }

    @Override
    public void destroy() {
        view = null;
        someoneProfileInfoUseCase.unsubscribe();
    }
}
