package com.example.mybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Activity_OrdersHistory extends AppCompatActivity {
    RecyclerView OrdersHistory_LST_dealsList;
    private FirebaseFirestore db;

    private MySPV mySPV;
    Gson json = new Gson();

    User current_user = new User();
    String current_user_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__oreders_history);

        findViews();

        // Set SP
        mySPV = new MySPV(this);
        json = new Gson();

        // Set Firebase
        db = FirebaseFirestore.getInstance();

        getUserFromSP();

        showItems();


    }

    // Get current user from SP
    private void getUserFromSP(){

        String user_json = mySPV.getString(MySPV.KEYS.CURRENT_USER, "No user");
        current_user = json.fromJson(user_json, User.class);
        // get user Id
        current_user_id = mySPV.getString(MySPV.KEYS.CURRENT_USER_ID, "no id");
    }


    // show list of the orders of the current user
    private void showItems() {
        // Get all orders from Firestore
        db.collection("orders").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Make Array List of the orders of the current user
                            ArrayList<Order> orders = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("user_id").equals(current_user_id)){
                                    Order order = new Order((ArrayList<Item>) document.get("item_list"), document.getString("total_price"), document.getString("timestamp"),Double.parseDouble(document.get("lat").toString()), Double.parseDouble(document.get("lon").toString()), current_user_id);
                                    orders.add(order);
                                }

                            }
                            // Display orders
                            Adapter_Orders adapter_orders = new Adapter_Orders(Activity_OrdersHistory.this, orders);
                            adapter_orders.setClickListeners(itemClickListener);
                            OrdersHistory_LST_dealsList.setLayoutManager(new LinearLayoutManager(Activity_OrdersHistory.this));
                            OrdersHistory_LST_dealsList.setAdapter(adapter_orders);
                        } else {
                            Log.w("pttt", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    // clicking Item action
    Adapter_Orders.lItemClickListener itemClickListener = new Adapter_Orders.lItemClickListener() {
        @Override
        public void itemClicked(Order order, int position) {
            // Add Order to SP as current order
            String order_json = json.toJson(order);
            mySPV.putString(MySPV.KEYS.CURRENT_ORDER, order_json);
            // Open dialog
            OpenOrder();
            // Open order display activity
        }
    };

    private void OpenOrder() {
        Intent intent = new Intent(getApplicationContext(), Activity_OrderDisplay.class);
        startActivity(intent);
    }

    private void findViews() {
        OrdersHistory_LST_dealsList = findViewById(R.id.OrdersHistory_LST_dealsList);
    }
}