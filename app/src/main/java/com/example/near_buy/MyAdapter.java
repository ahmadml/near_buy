 package com.example.near_buy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    String storeNamesHolder[],storeDescHolder[];
    int storeImagesHolder[];
    Context context;

    public MyAdapter(Context ct, String storeNames[], String storeDesc[], int store_images[]){
        context = ct;
        storeNamesHolder = storeNames;
        storeDescHolder= storeDesc;
        storeImagesHolder = store_images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title_text.setText(storeNamesHolder[position]);
        holder.desc_text.setText(storeDescHolder[position]);
        holder.store_image_view.setImageResource(storeImagesHolder[position]);
        holder.rowLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,single_store.class );
                intent.putExtra("data1", storeNamesHolder[position]);
                intent.putExtra("data2", storeDescHolder[position]);
                intent.putExtra("myImage", storeImagesHolder[position]);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return storeImagesHolder.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_text, desc_text;
        ImageView store_image_view;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_text = itemView.findViewById(R.id.title_text);
            desc_text = itemView.findViewById(R.id.desc_text);
            store_image_view = itemView.findViewById(R.id.store_image_view);
            rowLayout = itemView.findViewById(R.id.row_layout);
        }
    }
}
