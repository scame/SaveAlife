package com.krestone.savealife.data.rest;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.requests.LocationHolder;
import com.krestone.savealife.data.entities.requests.MapObjectsRequest;
import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.entities.requests.PhoneNumberHolder;
import com.krestone.savealife.data.entities.requests.VerificationHolder;
import com.krestone.savealife.data.entities.responses.ContactsStatusEntity;
import com.krestone.savealife.data.entities.responses.MapObjectsEntity;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface ServerApi {


    @POST("http://10.0.1.94:8080/addContacts")
    Observable<ContactsStatusEntity> getContactsStatus(@Body ContactsNumbersHolder numbersHolder,
                                                       @Header("Authorization") String tokenHeader);

    @POST("http://10.0.1.94:8080/signUp")
    Observable<ResponseBody> sendRegistrationNumber(@Body PhoneNumberHolder phoneNumber);

    @POST("http://10.0.1.94:8080/confirmSignUp")
    Observable<ResponseBody> sendVerificationCode(@Body VerificationHolder verificationHolder);

    @POST("http://10.0.1.94:8080/fillProfile")
    Observable<ResponseBody> sendPersonalInfo(@Body PersonalInfoHolder personalInfoHolder);

    @POST("http://10.0.1.94:8080//updateCoordinates")
    Observable<ResponseBody> sendLocation(@Body LocationHolder locationHolder,
                                          @Header("Authorization") String token);

    @POST("http://10.0.1.94:8080//objectsInArea")
    Observable<MapObjectsEntity> sendMapObjectsRequest(@Body MapObjectsRequest mapObjectsRequest,
                                                       @Header("Authorization") String token);

    @GET("http://10.0.1.94:8080//signIn")
    Observable<Response> getAuthToken(@Header("Authorization") String encodedEntryData);
}
