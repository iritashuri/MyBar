package com.example.mybar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Activity_OrderDisplay extends AppCompatActivity implements OrderCallBack{

    private MySPV mySPV;
    Gson gson = new Gson();

    //Define Buttons
    private Button OrderDisplay_BTN_Map;
    private Button OrderDisplay_BTN_Details;

    //Define fragments
    private Fragment_OrderDetails fragment_orderDetails;
    private Fragment_Maps fragment_maps;

    // Define current_order
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__order_display);

        findViews();
        initViews();

        // Init fragments
        fragment_maps = Fragment_Maps.newInstance();
        //fragment_orderDetails = Fragment_OrderDetails.newInstance();


        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.add(R.id.OrderDisplay_LAY_content, fragment_maps);
        t.add(R.id.OrderDisplay_LAY_content, fragment_orderDetails);
        t.commit();

        getSupportFragmentManager().beginTransaction().hide(fragment_maps).commit();

    }

    private void findViews() {
        OrderDisplay_BTN_Details = findViewById(R.id.OrderDisplay_BTN_Details);
        OrderDisplay_BTN_Map = findViewById(R.id.OrderDisplay_BTN_Map);
    }

    private void initViews(){

        OrderDisplay_BTN_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetails();
            }
        });

        OrderDisplay_BTN_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMap();
            }
        });

    }

    private void showDetails() {
        getSupportFragmentManager().beginTransaction().hide(fragment_maps).commit();
        getSupportFragmentManager().beginTransaction().show(fragment_orderDetails).commit();
    }

    private void showMap() {
        getSupportFragmentManager().beginTransaction().hide(fragment_orderDetails).commit();
        getSupportFragmentManager().beginTransaction().show(fragment_maps).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set fragments
        //fragment_list.setTable(tops);
        fragment_maps.setOrder(order);
    }

    // Load current order from SP
    @Override
    public void GetOrderFromSP() {
        String json = mySPV.getString(MySPV.KEYS.CURRENT_ORDER, null);
        order = gson.fromJson(json, Order.class);

        if (order == null){
            //Toast.makeText(Activity_OrdersHistory.this, "order was not found", Toast.LENGTH_SHORT).show();
            Log.d("pttt", "order was not found");
        }
    }
}

/*
*
    private void initFragments() {
        // Init list
        fragment_list = Fragment_List.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.TOPTEN_TBL_TopTen,fragment_list);
        transaction.commit();
        fragment_list.setListCallBack(this);

        // Init map
        fragment_maps = Fragment_Maps.newInstance();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.TOPTEN_Map_TopTen,fragment_maps);
        transaction2.commit();
    }
    }*/