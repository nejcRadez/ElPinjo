package com.example.nejc.elpinjo.API;


import com.example.nejc.elpinjo.models.Auth;
import com.example.nejc.elpinjo.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

public interface UsersAPI {
    @GET("/api/v1/users/{id}")
    void getUsers(@Path("id") int id, Callback<List<User>> response);

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/api/v1/users")
    void createUser(@Field("username") String username, @Field("email") String email, @Field("password") String password, Callback<User> cb);

    @FormUrlEncoded
    @POST("/api/v1/users/signin")
    void signInUser(@Field("email") String email, @Field("password") String password, Callback<Auth> cb);
}
