package com.example.fourthassignment;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fourthassignment.Model.UserModel;
import com.example.fourthassignment.Repository.UserRepo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {
    UserRepo userRepo;
    Button btnRegister;
    TextInputEditText firstname,lastname, txtemail, txtpassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        btnRegister = view.findViewById(R.id.btnregister);
        firstname = view.findViewById(R.id.firstname);
        lastname=view.findViewById(R.id.lastname);
        txtemail = view.findViewById(R.id.registeremail);
        txtpassword = view.findViewById(R.id.registerpassword);

        //for instance
        createInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userCUDModel = new UserModel(
                        firstname.getText().toString(),
                        lastname.getText().toString(),
                        txtemail.getText().toString(),
                        txtpassword.getText().toString());

                Call<Void> call = userRepo.addUser(userCUDModel);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(view.getContext(), "User Added Succesfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Not added", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return view;
    }

    private void createInstance() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userRepo = retrofit.create(UserRepo.class);
    }


}
