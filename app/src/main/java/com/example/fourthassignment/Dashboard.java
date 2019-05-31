package com.example.fourthassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fourthassignment.Adapter.ItemAdapter;
import com.example.fourthassignment.Model.ItemsModel;
import com.example.fourthassignment.Repository.RepoItems;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    RepoItems repoItems;
    private String BASE_URL = "http://10.0.2.2:8080";
    Button btnadditems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        createInstance();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        btnadditems = findViewById(R.id.btnAddItems);

        btnadditems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AddItems.class);
                startActivity(intent);
            }
        });


        Call<List<ItemsModel>> listCall = repoItems.getItems();
        listCall.enqueue(new Callback<List<ItemsModel>>() {
            @Override
            public void onResponse(Call<List<ItemsModel>> call, Response<List<ItemsModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Dashboard.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

//                to store data from database
                List<ItemsModel> itemsModelList = response.body();
                recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), itemsModelList));

            }

            @Override
            public void onFailure(Call<List<ItemsModel>> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createInstance() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        repoItems = retrofit.create(RepoItems.class);

    }
}


