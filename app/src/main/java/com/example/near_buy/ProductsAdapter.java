package com.example.near_buy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.HolderProducts> implements Filterable {
    private Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private ProductsFilterOnShops filter;

    public ProductsAdapter (Context context, ArrayList<ModelProduct> productList){
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProducts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_products, parent, false);
        return new HolderProducts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProducts holder, int position) {
        ModelProduct products = productList.get(position);
        List<ContactsContract.CommonDataKinds.Relation> prods;
        String ProductId = products.getProductId();
        String ProductName = products.getProductName();
        String ProductPrice = products.getProductPrice();
        String ProductQuantity = products.getProductQuantity();
        String TimeStamp = products.gettimeTemp();
        String uid = products.getUid();
        String Description = products.getDescription();
        String Store = products.getStore();



        holder.product_name.setText("Product Name: " + ProductName);
        holder.price.setText("Price: " + ProductPrice);
        holder.description.setText("Description: " + Description);
        holder.onStock.setText("On Stock: " + ProductQuantity);

        if(Integer.parseInt(ProductQuantity) > 0){
            holder.onStock.setBackgroundColor(Color.GREEN);
        }
        else{
            holder.onStock.setBackgroundColor(Color.RED);
        }

        //TODO
        //add product to cart

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, add_product_user_activity.class);
                intent.putExtra("ProductName", ProductName);
                intent.putExtra( "price", ProductPrice);
                intent.putExtra( "quantity", ProductQuantity);
                intent.putExtra( "ProductId", ProductId);
                intent.putExtra( "Description", Description);
                intent.putExtra("Store", Store);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new ProductsFilterOnShops(this,filterList);
        }
        return filter;
    }

    class HolderProducts extends RecyclerView.ViewHolder{
        private ImageView addToCart;
        private TextView product_name;
        private TextView price;
        private TextView description;
        private TextView onStock;
        private TextView discount;

        public HolderProducts(@NonNull View itemView) {
            super(itemView);
            addToCart = (ImageView)itemView.findViewById(R.id.shop_im);
            product_name = (TextView)itemView.findViewById(R.id.productName);
            price = (TextView)itemView.findViewById(R.id.productPrice);
            description = (TextView)itemView.findViewById(R.id.productDescription);
            onStock = (TextView)itemView.findViewById(R.id.stock);
            discount = (TextView)itemView.findViewById(R.id.Discount);
        }
    }
}
