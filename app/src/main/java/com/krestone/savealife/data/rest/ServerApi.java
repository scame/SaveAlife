package com.krestone.savealife.data.rest;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.requests.LocationHolder;
import com.krestone.savealife.data.entities.requests.MapObjectsRequest;
import com.krestone.savealife.data.entities.requests.PasswordMatchingRequest;
import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.entities.requests.PhoneNumberHolder;
import com.krestone.savealife.data.entities.requests.UpdateMyProfileInfoRequest;
import com.krestone.savealife.data.entities.requests.VerificationHolder;
import com.krestone.savealife.data.entities.responses.MapObjectsEntity;
import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.data.entities.responses.PasswordMatchingResponse;
import com.krestone.savealife.data.entities.responses.PhoneNumberResponse;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface ServerApi {


    @POST("http://10.0.1.94:8080/contactsInApp")
    Observable<ContactsNumbersHolder> getContactsInApp(@Body ContactsNumbersHolder numbersHolder,
                                                       @Header("x-auth-token") String tokenHeader);

    @POST("http://10.0.1.94:8080/deleteFromEmergencyList")
    Observable<ResponseBody> deleteContactFromEmergencyList(@Body ContactsNumbersHolder numbersHolder,
                                                            @Header("x-auth-token") String token);

    @GET("http://10.0.1.94:8080/getEmergencyList")
    Observable<ContactsNumbersHolder> getEmergencyContacts(@Header("x-auth-token") String token);

    @POST("http://10.0.1.94:8080/addToEmergencyList")
    Observable<ResponseBody> addToEmergencyList(@Body ContactsNumbersHolder numbersHolder,
                                                @Header("x-auth-token") String token);

    @POST("http://10.0.1.94:8080/signUp")
    Observable<PhoneNumberResponse> sendRegistrationNumber(@Body PhoneNumberHolder phoneNumber);

    @POST("http://10.0.1.94:8080/confirmSignUp")
    Observable<ResponseBody> sendVerificationCode(@Body VerificationHolder verificationHolder);

    @POST("http://10.0.1.94:8080/fillProfile")
    Observable<ResponseBody> sendPersonalInfo(@Body PersonalInfoHolder personalInfoHolder);

    @POST("http://10.0.1.94:8080//updateCoordinates")
    Observable<ResponseBody> sendLocation(@Body LocationHolder locationHolder,
                                          @Header("x-auth-token") String token);

    @POST("http://10.0.1.94:8080//objectsInArea")
    Observable<MapObjectsEntity> sendMapObjectsRequest(@Body MapObjectsRequest mapObjectsRequest,
                                                       @Header("x-auth-token") String token);

    @GET("http://10.0.1.94:8080//signIn")
    Observable<Response<ResponseBody>> getAuthToken(@Header("Authorization") String encodedEntryData);

    @GET("http://10.0.1.94:8080//profileInfo")
    Observable<SomeoneProfileEntity> getSomeoneProfileInfo(@Header("x-auth-token") String token,
                                                           @Header("phoneNumber") String phoneNumber);

    @GET("http://10.0.1.94:8080//myProfileInfo")
    Observable<MyProfileInfoEntity> getMyProfileInfo(@Header("x-auth-token") String token);

    @POST("http://10.0.1.94:8080//updateProfile")
    Observable<ResponseBody> updateMyProfileInfo(@Header("x-auth-token") String token,
                                                 @Body UpdateMyProfileInfoRequest infoBody);

    @POST("http://10.0.1.94:8080//matchPassword")
    Observable<PasswordMatchingResponse> matchPasswords(@Body PasswordMatchingRequest request);
}