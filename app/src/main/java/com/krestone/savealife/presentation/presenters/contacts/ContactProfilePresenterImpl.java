package com.krestone.savealife.presentation.presenters.contacts;


import android.util.Log;

import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.domain.usecases.contacts.DeleteFromEmergencyListUseCase;
import com.krestone.savealife.domain.usecases.profiles.GetSomeoneProfileInfoUseCase;
import com.krestone.savealife.presentation.models.ContactModel;
import com.krestone.savealife.util.ConnectivityUtil;

import java.util.Collections;

public class ContactProfilePresenterImpl<T extends ContactProfilePresenter.ContactProfileView>
        implements ContactProfilePresenter<T> {

    private T view;

    private GetSomeoneProfileInfoUseCase someoneProfileInfoUseCase;

    private DeleteFromEmergencyListUseCase deleteFromEmergencyListUseCase;

    public ContactProfilePresenterImpl(GetSomeoneProfileInfoUseCase someoneProfileInfoUseCase,
                                       DeleteFromEmergencyListUseCase deleteFromEmergencyListUseCase) {
        this.someoneProfileInfoUseCase = someoneProfileInfoUseCase;
        this.deleteFromEmergencyListUseCase = deleteFromEmergencyListUseCase;
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

    @Override
    public void removeContact(String phoneNumber) {
        ContactModel contactModel = new ContactModel();
        contactModel.setPhoneNumber(phoneNumber);

        deleteFromEmergencyListUseCase.setContacts(Collections.singletonList(contactModel));
        deleteFromEmergencyListUseCase.executeCompletable(
                () -> Log.i("onxDeleted", "done"),
                throwable -> Log.i("onxDeletionErr", throwable.toString())
        );
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
