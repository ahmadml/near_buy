package com.example.near_buy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapterSeller extends RecyclerView.Adapter<ProductsAdapterSeller.HolderProductsSeller>  {
    private Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private Activity activity;
    private ProductsFilterOnShops filter;
    FirebaseAuth fAuth;
    String id;

    public ProductsAdapterSeller (Activity context, ArrayList<ModelProduct> productList){
        this.activity = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductsSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_products_for_seller, parent, false);
        return new HolderProductsSeller(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductsSeller holder, int position) {
        ModelProduct products = productList.get(position);
        List<ContactsContract.CommonDataKinds.Relation> prods;
        String ProductId = products.getProductId();
        String ProductName = products.getProductName();
        String ProductPrice = products.getProductPrice();
        String ProductQuantity = products.getProductQuantity();
        String TimeStamp = products.gettimeTemp();
        String uid = products.getUid();
        String Description = products.getDescription();


        holder.product_name.setText("Product Name: " + ProductName);
        holder.price.setText("Price: " + ProductPrice);
        holder.description.setText("Description: " + Description);
        holder.onStock.setText("On my Stock: " + ProductQuantity);

        if(Integer.parseInt(ProductQuantity) > 0){
            holder.onStock.setBackgroundColor(Color.GREEN);
        }
        else{
            holder.onStock.setBackgroundColor(Color.RED);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.MyDialog = new Dialog(activity);
                holder.MyDialog.setContentView(R.layout.delete_dialog);
                holder.MyDialog.setTitle("Delete Product?");
                final DatabaseReference child1 = holder.db.child("Products");
                holder.Yes = holder.MyDialog.findViewById(R.id.btn_yes);
                holder.No = holder.MyDialog.findViewById(R.id.btn_no);
                holder.Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String x=  products.toString();
                        DatabaseReference s = holder.db.child(ProductId);
                        holder.db.child(ProductId).removeValue();
                        holder.MyDialog.cancel();
                    }
                });
                holder.No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.MyDialog.cancel();
                    }
                });
                holder.MyDialog.show();
            }
        });

        //TODO
        //add product to cart

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class HolderProductsSeller extends RecyclerView.ViewHolder{
        private ImageView addToCart;
        private TextView product_name;
        private TextView price;
        private TextView description;
        private TextView onStock;
        private TextView discount;
        public Dialog MyDialog;
        TextView tvDeleteDiaog;
        public Button Yes, No;
        DatabaseReference db;

        public HolderProductsSeller(@NonNull View itemView) {
            super(itemView);
            product_name = (TextView)itemView.findViewById(R.id.productName2);
            price = (TextView)itemView.findViewById(R.id.productPrice2);
            description = (TextView)itemView.findViewById(R.id.productDescription2);
            onStock = (TextView)itemView.findViewById(R.id.stock2);
//            discount = (TextView)itemView.findViewById(R.id.Discount);
            tvDeleteDiaog = (TextView)itemView.findViewById(R.id.txt_dia);
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String currentId = currentUser.getUid();
            db= FirebaseDatabase.getInstance().getReference("users/" + currentId + "/Products");
        }
    }
}
