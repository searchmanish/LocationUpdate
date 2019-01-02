package com.softcodeinfotech.locationupdate;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {


    @Multipart
    @POST("property/easypick/attan.php")
    Call<LocationResponse> saveAttandance(
            @Part("email") RequestBody email,
            @Part("deviceid") RequestBody deviceid,
            @Part("lati") RequestBody lati,
            @Part("longi") RequestBody longi,
            @Part("timestamp") RequestBody timestamp
    );

    @Multipart
    @POST("property/easypick/employee_signin.php")
    Call<SigninResponse> siginReq(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );
}
