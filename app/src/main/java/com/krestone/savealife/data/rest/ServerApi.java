package com.krestone.savealife.data.rest;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.requests.LocationHolder;
import com.krestone.savealife.data.entities.requests.MapObjectsRequest;
import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.entities.requests.PhoneNumberHolder;
import com.krestone.savealife.data.entities.requests.SosEntity;
import com.krestone.savealife.data.entities.requests.UpdateMyProfileInfoRequest;
import com.krestone.savealife.data.entities.requests.VerificationHolder;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.data.entities.responses.PhoneNumberResponse;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.data.entities.responses.map.HelpIntentState;
import com.krestone.savealife.data.entities.responses.map.MapObjectsEntity;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ServerApi {


    @POST("contactsInApp")
    Observable<ContactsHolder> getContactsInApp(@Body ContactsNumbersHolder numbersHolder,
                                                @Header("x-auth-token") String tokenHeader);

    @POST("deleteFromEmergencyList")
    Observable<ResponseBody> deleteContactsFromEmergencyList(@Body ContactsNumbersHolder numbersHolder,
                                                             @Header("x-auth-token") String token);

    @GET("getEmergencyList")
    Observable<ContactsHolder> getEmergencyContacts(@Header("x-auth-token") String token);

    @POST("addToEmergencyList")
    Observable<ResponseBody> addToEmergencyList(@Body ContactsHolder contactsHolder,
                                                @Header("x-auth-token") String token);

    @POST("signUp")
    Observable<PhoneNumberResponse> sendRegistrationNumber(@Body PhoneNumberHolder phoneNumber);

    @POST("confirmSignUp")
    Observable<ResponseBody> sendVerificationCode(@Body VerificationHolder verificationHolder);

    @POST("fillProfile")
    Observable<ResponseBody> sendPersonalInfo(@Body PersonalInfoHolder personalInfoHolder);

    @POST("updateCoordinates")
    Observable<ResponseBody> sendLocation(@Body LocationHolder locationHolder,
                                          @Header("x-auth-token") String token);

    @POST("objectsInArea")
    Observable<MapObjectsEntity> sendMapObjectsRequest(@Body MapObjectsRequest mapObjectsRequest,
                                                       @Header("x-auth-token") String token);

    @GET("signIn")
    Observable<Response<ResponseBody>> getAuthToken(@Header("Authorization") String encodedEntryData,
                                                    @Header("firebaseToken") String firebaseToken);

    @GET("profileInfo")
    Observable<SomeoneProfileEntity> getSomeoneProfileInfo(@Header("x-auth-token") String token,
                                                           @Header("phoneNumber") String phoneNumber);

    @GET("myProfileInfo")
    Observable<MyProfileInfoEntity> getMyProfileInfo(@Header("x-auth-token") String token);

    @POST("updateProfile")
    Observable<ResponseBody> updateMyProfileInfo(@Header("x-auth-token") String token,
                                                 @Body UpdateMyProfileInfoRequest infoBody);

    @GET("sos/acceptHelp")
    Observable<HelpIntentState> helpIntentRequest(@Header("x-auth-token") String token,
                                                  @Header("suffererNumber") String number,
                                                  @Query("isHelp") Boolean isHelp);

    @POST("sos/start")
    Observable<ResponseBody> startSos(@Header("x-auth-token") String token,
                                      @Body SosEntity sosEntity);

    @POST("sos/stop")
    Observable<ResponseBody> stopSos(@Header("x-auth-token") String token,
                                     @Body SosEntity sosEntity);
    @POST("updateFbToken")
    Observable<ResponseBody> updateFirebaseToken(@Header("x-auth-token") String token,
                                                 @Header("firebaseToken") String fbToken);
}