package com.krestone.savealife.data.rest;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.responses.ContactsStatusEntity;

import retrofit2.http.Body;
import retrofit2.http.GET;
import rx.Observable;

public interface ServerApi {

    @GET("")
    Observable<ContactsStatusEntity> getContactsStatus(@Body ContactsNumbersHolder numbersHolder);
}
