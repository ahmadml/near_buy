package com.example.near_buy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.HolderShop>{
    private Context context;
    public ArrayList<Manager> shopList;

    public ShopAdapter (Context context, ArrayList<Manager> shopList){
        this.context = context;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shop, parent, false);
        return new HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderShop holder, int position) {
        Manager shop = shopList.get(position);
        final String uid = shop.getUid();
        String Address = shop.getStore_address();
        String email = shop.getEmail();
        String shopName = shop.getStore();
        String phone = String.valueOf(shop.getPhone());
        String open = shop.getIsOpen();
        String name = shop.getName();

        holder.name.setText("seller name: " + name);
        holder.address.setText("Address: " + Address);
        holder.shop_name.setText("shop name: " + shopName);
        holder.shop_phone.setText("shop phone: " + phone);

        if(open.equals("true")){
            holder.isOpen.setText("shop is open");
            holder.isOpen.setBackgroundColor(Color.GREEN);
        }
        else {
            holder.isOpen.setText("shop is closed");
            holder.isOpen.setBackgroundColor(Color.RED);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, shopDetailsActivity.class);
                intent.putExtra("shopUid", uid);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class HolderShop extends RecyclerView.ViewHolder{
        private TextView shop_name;
        private TextView shop_phone;
        private TextView address;
        private TextView isOpen;
        private TextView name;

        public HolderShop(@NonNull View itemView) {
            super(itemView);
            shop_name = (TextView)itemView.findViewById(R.id.shopName);
            shop_phone = (TextView)itemView.findViewById(R.id.shop_phone);
            address = (TextView)itemView.findViewById(R.id.Address);
            isOpen = (TextView)itemView.findViewById(R.id.open);
            name = (TextView)itemView.findViewById(R.id.name);
        }
    }
}
