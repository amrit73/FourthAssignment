package com.example.fourthassignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fourthassignment.Model.ImageModel;
import com.example.fourthassignment.Model.ItemsModel;
import com.example.fourthassignment.Repository.RepoItems;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddItems extends AppCompatActivity {
    private final String BASE_URL = "http://10.0.2.2:8080";

    EditText ItemName, ItemPrice, ItemDescription;
    Button btnSubmitItem,dashboardd;
    ImageView selectImage;
    Uri uri;
    Bitmap bitmap;
    RepoItems repoItems;
    private static final int PICK_IMAGE = 1;
    String ImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        ItemName = findViewById(R.id.additemName);
        ItemPrice = findViewById(R.id.additemPrice);
        ItemDescription = findViewById(R.id.additemDescription);
        btnSubmitItem = findViewById(R.id.submitItem);
        dashboardd=findViewById(R.id.dashboardd);
        selectImage = findViewById(R.id.selectImage);
        createInstance();

        dashboardd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItems.this, Dashboard.class);
                startActivity(intent);
            }
        });
        btnSubmitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage(bitmap);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addItems();
                    }
                }, 1100);
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select a image", Toast.LENGTH_SHORT).show();
            }
            uri = data.getData();
        }

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            selectImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void openGallery() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Choose Image"), PICK_IMAGE);
    }


    //insert image

    private void SaveImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        try {
            File file = new File(this.getCacheDir(), "image.jpg");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

            Call<ImageModel> call = repoItems.uploadImage(body);
            call.enqueue(new Callback<ImageModel>() {
                @Override
                public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {

                    if (response.isSuccessful()) {
                        ImageModel imageModel = response.body();

                        ImageUrl = imageModel.getImage();
                    }

                }

                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {
                    Toast.makeText(AddItems.this, "Image upload Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        repoItems = retrofit.create(RepoItems.class);
    }

    private void addItems() {

        Call<Void> call = repoItems.addItems(new ItemsModel(
                ItemName.getText().toString(),
                ItemPrice.getText().toString(),
                ItemDescription.getText().toString(),
                ImageUrl));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddItems.this, "Items addded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddItems.this, "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
