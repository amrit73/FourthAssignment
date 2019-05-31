package com.example.fourthassignment.Repository;

import com.example.fourthassignment.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRepo {

    @POST("/addUser")
    Call<Void> addUser(@Body UserModel userCUDModel);

    @POST("/getUser")
    Call<List<UserModel>> getUser(@Body UserModel userCUDModel);
}
