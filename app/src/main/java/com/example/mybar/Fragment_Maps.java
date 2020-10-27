package com.example.mybar;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_Maps extends Fragment implements OnMapReadyCallback {
    // This fragment represent an order location in google map
    Order current_order;
    protected View view;
    private GoogleMap mgoogleMap;
    MapView mMapView;
    Context context;

    private OrderCallBack orderCallBack;

    public void setListCallBack(OrderCallBack orderCallBack) {
        this.orderCallBack = orderCallBack;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Maps() {
        // Required empty public constructor
    }

    public static Fragment_Maps newInstance() {
        Fragment_Maps fragment = new Fragment_Maps();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            view = inflater.inflate(R.layout.fragment_maps, container, false);            // ... rest of body of onCreateView() ...
        } catch (Exception e) {
            Log.e("Erryyy", "onCreateView", e);
            throw e;
        }


        if (orderCallBack != null) {
            orderCallBack.GetOrderFromSP();
        }

        return view;
    }

    // Display order location on the map
    private void showOrderLocationOnMap(GoogleMap googleMap){
        googleMap.addMarker(new MarkerOptions().position(new LatLng(current_order.getLat(), current_order.getLon())).snippet(current_order.getTimestamp()));
        CameraPosition current_pos = CameraPosition.builder().target(new LatLng(current_order.getLat(), current_order.getLon())).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(current_pos));
    }

    // Check permission for map
    private boolean CheckPermission() {
        if (!(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(getContext(), R.string.common_google_play_services_enable_text, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    protected void setOrder(Order order){
        this.current_order = order;
    }
    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView)view.findViewById(R.id.Maps_map_view);
        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mgoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        showOrderLocationOnMap(googleMap);
    }
}