package com.example.mybar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Orders extends RecyclerView.Adapter<Adapter_Orders.ViewHolder> {
    private ArrayList<Order> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    public lItemClickListener itemClickListener;

    // data is passed into the constructor
    Adapter_Orders(Context context, ArrayList<Order> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public void setClickListeners(lItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // binds the data to the TextView in each row
        Order order = mData.get(position);
        holder.orderView_TXT_date.setText("" + order.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    Order getOrder(int position) {
        return mData.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderView_TXT_date;
        public ViewHolder(@NonNull View orderView) {
            super(orderView);
            orderView_TXT_date = itemView.findViewById(R.id.orderView_TXT_date);


            orderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.itemClicked(getOrder(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }
    }
    public interface lItemClickListener {
        void itemClicked(Order order, int position);
    }
}
