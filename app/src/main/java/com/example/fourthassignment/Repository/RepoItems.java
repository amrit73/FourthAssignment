package com.example.fourthassignment.Repository;

import com.example.fourthassignment.Model.ImageModel;
import com.example.fourthassignment.Model.ItemsModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RepoItems {
    @POST("/addItem")
    Call<Void> addItems(@Body ItemsModel itemsModel);

    @GET("/getItem")
    Call<List<ItemsModel>> getItems();

    @Multipart
    @POST("/upload")
    Call<ImageModel> uploadImage(@Part MultipartBody.Part image);
}
