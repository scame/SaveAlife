package com.krestone.savealife.data.rest;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.responses.ContactsStatusEntity;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface ServerApi {


    @POST("http://10.0.1.94:8080/addContacts")
    Observable<ContactsStatusEntity> getContactsStatus(@Body ContactsNumbersHolder numbersHolder,
                                                       @Header("Authorization") String tokenHeader);
}
