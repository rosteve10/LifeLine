package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylifeline.models.PlaceList;
import com.example.mylifeline.models.SinglePlace;
import com.example.mylifeline.recycler.HospitalListRecycler;
import com.example.mylifeline.rest_api.GooglePlacesApi;
import com.example.mylifeline.rest_api.HospitalListClient;
import com.example.mylifeline.utils.LoadingUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalList extends AppCompatActivity {


    public static final String TAG = "list";
    RecyclerView recyclerHospital;
    ArrayList<SinglePlace> itemList;
    FrameLayout fader, listFrame;
    AVLoadingIndicatorView avi;
    TextView tvDisplayResult;
    GooglePlacesApi googlePlacesApi;
    HospitalListClient hospitalListClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerHospital = (RecyclerView) findViewById(R.id.recyclerHospital);
        recyclerHospital.setLayoutManager(new LinearLayoutManager(this));

        fader = (FrameLayout) findViewById(R.id.fader);
        listFrame = (FrameLayout) findViewById(R.id.content_main);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        tvDisplayResult = findViewById(R.id.tvDisplayResult);


        stopLoadingAnimation();
        tvDisplayResult.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            Log.d(TAG, "onCreate: search started");

            setLoadingAnimation();
            String query = intent.getStringExtra(SearchManager.QUERY);

            toolbar.setTitle("Search results for '" + query + "'");

            googlePlacesApi = new GooglePlacesApi(getApplicationContext());
            hospitalListClient = googlePlacesApi.getHospitalListClient();

            HashMap<String, String> params = googlePlacesApi.getQueryParams(HospitalLoacator.curLocation, GooglePlacesApi.TYPE_HOSPITAL, GooglePlacesApi.RANKBY_PROMINENCE);
            params.put("radius", "5000");
            params.put("name", query);

            hospitalListClient.getNearbyHospitals(params).enqueue(new Callback<PlaceList>() {
                @Override
                public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
//                    Log.d(TAG, "onResponse: resp received");
                    PlaceList placeList = response.body();

                    if (placeList != null) {
                        stopLoadingAnimation();
                        itemList = placeList.places;
                        if (itemList.size() == 0)
                            tvDisplayResult.setVisibility(View.VISIBLE);
                        else
                            bindRecyclerView();

                    }

                }

                @Override
                public void onFailure(Call<PlaceList> call, Throwable t) {
//                    Log.d(TAG, "onFailure: cannot access places api");
                    Toast.makeText(getApplicationContext(), "Unable to access server. Please try again later", Toast.LENGTH_SHORT).show();
                    tvDisplayResult.setVisibility(View.VISIBLE);
                }
            });
        } else {
            itemList = Parcels.unwrap(intent.getParcelableExtra("itemList"));
            bindRecyclerView();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    void bindRecyclerView() {
        HospitalListRecycler hospitalListRecycler = new HospitalListRecycler(itemList, this);
        recyclerHospital.setAdapter(hospitalListRecycler);
    }

    void setLoadingAnimation() {
        LoadingUtil.enableDisableView(listFrame, false);
        tvDisplayResult.setVisibility(View.INVISIBLE);
        fader.setVisibility(View.VISIBLE);
        avi.show();
    }

    void stopLoadingAnimation() {
        LoadingUtil.enableDisableView(listFrame, true);
        fader.setVisibility(View.GONE);
        avi.hide();
    }

}


