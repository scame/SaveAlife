package com.krestone.savealife.domain.usecases.entry;


import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import okhttp3.ResponseBody;
import rx.Single;

public class VerificationUseCase extends UseCaseSingle<ResponseBody> {

    private EntryRepository entryRepository;

    private String phoneNumber;

    private String verifCode;

    public VerificationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                               EntryRepository entryRepository) {
        super(subscribeOn, observeOn);
        this.entryRepository = entryRepository;
    }

    @Override
    protected Single<ResponseBody> getUseCaseSingle() {
        return entryRepository.sendVerificationCode(phoneNumber, verifCode);
    }

    public void setData(String phoneNumber, String verifCode) {
        this.phoneNumber = phoneNumber;
        this.verifCode = verifCode;
    }
}
