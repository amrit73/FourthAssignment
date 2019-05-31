package com.example.fourthassignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fourthassignment.Items;
import com.example.fourthassignment.Model.ItemsModel;
import com.example.fourthassignment.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context context;
    List<ItemsModel> cudModelList;
    private String BASE_URL = "http://10.0.2.2:8080";

    public ItemAdapter(Context context, List<ItemsModel> cudModelList) {
        this.context = context;
        this.cudModelList = cudModelList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final ItemsModel itemsModel = cudModelList.get(i);
        itemViewHolder.itemName.setText(itemsModel.getItemName());
        itemViewHolder.itemPrice.setText(itemsModel.getItemPrice());
        itemViewHolder.itemDesc.setText(itemsModel.getItemDescription());
        Picasso.with(context).load(BASE_URL + "/images/" + itemsModel.getItemImageName()).into(itemViewHolder.itemImage);
        Log.d("image", "onBindViewHolder: " + BASE_URL + "/images/" + itemsModel.getItemImageName());

        itemViewHolder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context vcontext = v.getContext();

                Intent intent = new Intent(context, Items.class);
                intent.putExtra("name", itemsModel.getItemName());
                intent.putExtra("price", itemsModel.getItemPrice());
                intent.putExtra("desc", itemsModel.getItemDescription());
                intent.putExtra("image", BASE_URL + "/images/" + itemsModel.getItemImageName());
                vcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cudModelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice, itemDesc;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemDesc = itemView.findViewById(R.id.itemDescription);
            itemImage = itemView.findViewById(R.id.itemImages);
        }
    }
}
