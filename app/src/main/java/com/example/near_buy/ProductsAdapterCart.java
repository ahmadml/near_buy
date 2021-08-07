package com.example.near_buy;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapterCart extends RecyclerView.Adapter<ProductsAdapterCart.HolderProductsCart>  {
    private Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private ProductsFilterOnShops filter;

    public ProductsAdapterCart (Context context, ArrayList<ModelProduct> productList){
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductsCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_products_for_cart, parent, false);
        return new HolderProductsCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductsCart holder, int position) {
        ModelProduct products = productList.get(position);
        List<ContactsContract.CommonDataKinds.Relation> prods;
        String ProductId = products.getProductId();
        String ProductName = products.getProductName();
        String ProductPrice = products.getProductPrice();
        String ProductQuantity = products.getProductQuantity();
        String timeTemp = products.gettimeTemp();
        String uid = products.getUid();
        String Description = products.getDescription();
        String Store = products.getStore();

        holder.product_name.setText("Product Name: " + ProductName);
        holder.price.setText("Price: " + ProductPrice);
        holder.description.setText("Description: " + Description);
        holder.onStock.setText("On my Stock: " + ProductQuantity);
        holder.store.setText("Store: " + Store);

        if(Integer.parseInt(ProductQuantity) > 0){
            holder.onStock.setBackgroundColor(Color.GREEN);
        }
        else{
            holder.onStock.setBackgroundColor(Color.RED);
        }

        //TODO
        //add product to cart

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.MyDialog = new Dialog(context);
                holder.MyDialog.setContentView(R.layout.delete_dialog);
                holder.MyDialog.setTitle("Delete Product?");
                final DatabaseReference child1 = holder.db.child("Cart");
                holder.Yes = holder.MyDialog.findViewById(R.id.btn_yes);
                holder.No = holder.MyDialog.findViewById(R.id.btn_no);
                holder.Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String x=  products.toString();
                        DatabaseReference s = holder.db.child(timeTemp);
                        holder.db.child(timeTemp).removeValue();
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
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class HolderProductsCart extends RecyclerView.ViewHolder{
        private ImageView addToCart;
        private TextView product_name;
        private TextView price;
        private TextView description;
        private TextView onStock;
        private TextView discount;
        private TextView store;
        public Dialog MyDialog;
        TextView tvDeleteDiaog;
        public Button Yes, No;
        DatabaseReference db;

        public HolderProductsCart(@NonNull View itemView) {
            super(itemView);
            product_name = (TextView)itemView.findViewById(R.id.productName);
            price = (TextView)itemView.findViewById(R.id.productPrice);
            description = (TextView)itemView.findViewById(R.id.productDescription);
            onStock = (TextView)itemView.findViewById(R.id.stock);
            discount = (TextView)itemView.findViewById(R.id.Discount);
            store = (TextView)itemView.findViewById(R.id.store_of_product);
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String currentId = currentUser.getUid();
            db= FirebaseDatabase.getInstance().getReference("users/" + currentId + "/Cart");
        }
    }
}
