package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.repository.RegistrationRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import okhttp3.ResponseBody;
import rx.Single;

public class PersonalInfoUseCase extends UseCaseSingle<ResponseBody> {

    private RegistrationRepository registrationRepository;

    private PersonalInfoHolder personalInfoHolder;

    public PersonalInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                               RegistrationRepository registrationRepository) {
        super(subscribeOn, observeOn);
        this.registrationRepository = registrationRepository;
    }

    @Override
    protected Single<ResponseBody> getUseCaseSingle() {
        return registrationRepository.sendPersonalInfo(personalInfoHolder);
    }

    public void setPersonalInfoHolder(PersonalInfoHolder personalInfoHolder) {
        this.personalInfoHolder = personalInfoHolder;
    }

    public PersonalInfoHolder getPersonalInfoHolder() {
        return personalInfoHolder;
    }
}
