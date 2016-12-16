package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.repository.RegistrationRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import okhttp3.ResponseBody;
import rx.Single;

public class VerificationUseCase extends UseCaseSingle<ResponseBody> {

    private RegistrationRepository registrationRepository;

    private String phoneNumber;

    private String verifCode;

    public VerificationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                               RegistrationRepository registrationRepository) {
        super(subscribeOn, observeOn);
        this.registrationRepository = registrationRepository;
    }

    @Override
    protected Single<ResponseBody> getUseCaseSingle() {
        return registrationRepository.sendVerificationCode(phoneNumber, verifCode);
    }

    public void setData(String phoneNumber, String verifCode) {
        this.phoneNumber = phoneNumber;
        this.verifCode = verifCode;
    }
}
