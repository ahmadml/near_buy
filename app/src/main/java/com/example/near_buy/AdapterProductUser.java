package com.example.near_buy;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
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


import java.util.ArrayList;

public class AdapterProductUser extends RecyclerView.Adapter<AdapterProductUser.HolderProduct>  {

    private Context context;
    public ArrayList<ModelProduct> productsList, filterList;
    //private FilterProductUser filter;

    public AdapterProductUser(Context context, ArrayList<ModelProduct> productsList) {
        this.context = context;
        this.productsList = productsList;
        this.filterList = filterList;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_products,parent,false);
        return new HolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
        //get data
        final ModelProduct modelProduct = productsList.get(position);
        String productTitle = modelProduct.getProductName();
        String productQuantity = modelProduct.getProductQuantity();
        String productId = modelProduct.getProductId();
        String timestamp = modelProduct.gettimeTemp();

        //set data
        holder.titleTv.setText(productTitle);


        holder.addToCartTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add product to cart
                showQuantityDialog(modelProduct);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show product details

            }
        });

    }

    private double cost =0;
    private double finalCost=0;
    private int quantity =0;
    private void showQuantityDialog(ModelProduct modelProduct) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_quantity, null);
        //init layout views
        ImageView productIv = view.findViewById(R.id.cartImage);
        final TextView titleTv = view.findViewById(R.id.titleTv);
        TextView pQuantityTv = view.findViewById(R.id.pQuantity);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        TextView discountNoteTv = view.findViewById(R.id.discountNoteTv);
        final TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);
        final TextView finalPrice = view.findViewById(R.id.finalPriceTv);
        ImageButton decrementBtn = view.findViewById(R.id.decrementBtn);
        final TextView quantityTv = view.findViewById(R.id.quantityTv);
        ImageButton incrementBtn = view.findViewById(R.id.incrementBtn);
        Button continueBtn = view.findViewById(R.id.continueBtn);

        //get data from model
        final String productId = modelProduct.getProductId();
        String title = modelProduct.getProductName();
        String productQuantity = modelProduct.getProductQuantity();

        final String price;
        price = modelProduct.getProductPrice();
        cost = Double.parseDouble(price.replaceAll("$", ""));
        finalCost= Double.parseDouble(price.replaceAll("$",""));
        quantity=1;

        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);


        titleTv.setText(""+title);
        pQuantityTv.setText(""+productQuantity);
        quantityTv.setText(""+quantity);
        finalPrice.setText("$"+finalCost);

        final AlertDialog dialog = builder.create();
        dialog.show();

        //increase quantity of the product

        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCost = finalCost + cost;
                quantity ++;
                finalPrice.setText("$"+finalCost);
                quantityTv.setText(""+quantity);

            }

        });
        //decrement quantity of product only if quantity>1

        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity>1){
                    finalCost = finalCost-cost;
                    quantity --;
                    finalPrice.setText("$"+finalCost);
                    quantityTv.setText(""+quantity);
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTv.getText().toString().trim();
                String priceEach = price;
                String totalPrice = finalPrice.getText().toString().trim().replace("$","");
                String quantity = quantityTv.getText().toString().trim();

                //add to db (SQLite)
                //addToCart(productId, title, priceEach, totalPrice,quantity);
                dialog.dismiss();
            }
        });
    }

    private int itemId =1;
//    private void addToCart(String productId, String title, String priceEach, String price, String quantity) {
//        itemId ++;
//
//        EasyDB easyDB = EasyDB.init(context,"ITEMS_DB")
//                .setTableName("ITEMS_TABLE")
//                .addColumn("Item_Id", new String[]{"text", "unique"})
//                .addColumn("Item_PID", new String[]{"text", "not null"})
//                .addColumn("Item_Name", new String[]{"text", "not null"})
//                .addColumn("Item_Price_Each", new String[]{"text", "not null"})
//                .addColumn("Item_Price", new String[]{"text","not null"})
//                .addColumn("Item_Quantity", new String[]{"text", "not null"})
//
//                .doneTableColumn();
//        Boolean b = easyDB.addData("ItemId", itemId)
//                .addData("Item_PID", productId)
//                .addData("Item_Name", title)
//                .addData("Item_Price_Each", priceEach)
//                .addData("Item_Price",price)
//                .addData("Item_Quantity",quantity)
//                .doneDataAdding();
//        Toast.makeText(context, "Added to cart..", Toast.LENGTH_SHORT).show();
//
//        //update cartCount
//        //((shopDetailsActivity)context).cartCount();
//
//    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

//    @Override
//    public Filter getFilter() {
//        if(filter==null){
//            filter = new FilterProductUser(this, filterList);
//        }
//        return filter;
//    }

    class HolderProduct extends RecyclerView.ViewHolder{
        //ui views
        private ImageView productIconIv;
        private TextView discountNoteTv, titleTv,descriptionTv,addToCartTv,
                discountPriceTv,originalPriceTv;

        public HolderProduct(@NonNull View itemView) {
            super(itemView);
            //ints views
            productIconIv = itemView.findViewById(R.id.cartImage);
            discountNoteTv = itemView.findViewById(R.id.discountNoteTv);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv= itemView.findViewById(R.id.descriptionTv);
            addToCartTv = itemView.findViewById(R.id.continueBtn);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);

        }
    }
}
