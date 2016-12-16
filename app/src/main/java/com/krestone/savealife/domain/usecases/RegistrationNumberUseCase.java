package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.repository.RegistrationRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import okhttp3.ResponseBody;
import rx.Single;

public class RegistrationNumberUseCase extends UseCaseSingle<ResponseBody> {

    private RegistrationRepository registrationRepository;

    private String phoneNumber;

    public RegistrationNumberUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                     RegistrationRepository registrationRepository) {
        super(subscribeOn, observeOn);
        this.registrationRepository = registrationRepository;
    }

    @Override
    protected Single<ResponseBody> getUseCaseSingle() {
        return registrationRepository.sendPhoneNumber(phoneNumber);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
