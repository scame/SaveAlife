package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.domain.usecases.entry.PersonalInfoUseCase;

public class PersonalInfoPresenterImpl<T extends PersonalInfoPresenter.PersonalInfoView> implements PersonalInfoPresenter<T> {

    private PersonalInfoUseCase personalInfoUseCase;

    private T view;

    public PersonalInfoPresenterImpl(PersonalInfoUseCase personalInfoUseCase) {
        this.personalInfoUseCase = personalInfoUseCase;
    }

    @Override
    public void sendPersonalInfo(String firstName, String lastName, String password, String phoneNumber,
                                 String medicalQualification, String verifCode) {
        personalInfoUseCase.setPersonalInfoHolder(new PersonalInfoHolder
                (firstName, lastName, password, medicalQualification, phoneNumber, verifCode));
        personalInfoUseCase.executeCompletable(() -> {
            if (view != null) {
                view.onPersonalInfoSent();
            }
        }, throwable -> {
            Log.i("onxPersInfoErr", throwable.toString());
            if (view != null) {
                view.onPersonalInfoErr(throwable.toString());
            }
        });
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        personalInfoUseCase.unsubscribe();
    }
}
