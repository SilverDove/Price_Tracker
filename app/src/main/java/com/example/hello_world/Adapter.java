package com.example.hello_world;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<ProductItem> mProductItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onDetailsClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        mListener=listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mProductName, mProductPrice;
        public ImageView mDeleteProduct;
        public ImageView mInfoProduct;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {//item View correspond to whole card
            super(itemView);
            mProductName = itemView.findViewById(R.id.product_name);
            mProductPrice = itemView.findViewById(R.id.price_product);
            mDeleteProduct=itemView.findViewById(R.id.delete_product);
            mInfoProduct=itemView.findViewById(R.id.info_product);

            mDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mInfoProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDetailsClick(position);//go to another activity
                        }
                    }
                }
            });

        }
    }

    public Adapter(ArrayList<ProductItem> productItemList){
        mProductItemList= productItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem currentProductItem = mProductItemList.get(position);

        holder.mProductName.setText(currentProductItem.getProductName());
        holder.mProductPrice.setText(currentProductItem.getProductPrice());
    }

    @Override
    public int getItemCount() {
        return mProductItemList.size();
    }
}
