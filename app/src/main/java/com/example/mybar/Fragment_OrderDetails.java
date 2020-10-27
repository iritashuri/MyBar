package com.example.mybar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class Fragment_OrderDetails extends Fragment {
    private OrderCallBack orderCallBack;

    private View view;
    private RecyclerView Order_Details_RCV_description;
    private TextView Order_Details_RCV_totalPrice;

    public Fragment_OrderDetails() {
        // Required empty public constructor
    }

    public void setListCallBack(OrderCallBack orderCallBack) {
        this.orderCallBack = orderCallBack;
    }

    public static Fragment_OrderDetails newInstance() {
        Fragment_OrderDetails fragment = new Fragment_OrderDetails();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null) {
            view = inflater.inflate(R.layout.fragment__order_details, container, false);
        }
        findViews(view);

        if(orderCallBack != null){
            orderCallBack.GetOrderFromSP();
        }
        return view;
    }

    private void findViews(View view) {
        Order_Details_RCV_description = view.findViewById(R.id.Order_Details_RCV_description);
        Order_Details_RCV_totalPrice = view.findViewById(R.id.Order_Details_RCV_totalPrice);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void setOrderDetails(Order order){
        ArrayList<Item> items = order.getItem_list();
        Adapter_Items adapter_items = new Adapter_Items(getActivity(), items);
        // Display Items
        Order_Details_RCV_description.setLayoutManager(new LinearLayoutManager(getActivity()));
        Order_Details_RCV_description.setAdapter(adapter_items);

        //Display total_price
        Order_Details_RCV_totalPrice.setText("Total price: " + order.getTotal_price() + " Nis");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }
}