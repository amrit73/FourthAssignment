package com.example.fourthassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Items extends AppCompatActivity {
    ImageView imageShow;
    TextView name, price, desc;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        bundle = getIntent().getExtras();
        Init();
    }

    private void Init() {

        imageShow = findViewById(R.id.imagename);
        name = findViewById(R.id.nameitem);
        price = findViewById(R.id.priceitem);
        desc = findViewById(R.id.descitem);

        if (bundle != null) {

            name.setText(bundle.getString("name"));
            price.setText(bundle.getString("price"));
            desc.setText(bundle.getString("desc"));
            String image = bundle.getString("image");

            Picasso.with(this).load(image).into(imageShow);
        }
    }
}
