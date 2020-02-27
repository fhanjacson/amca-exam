package com.example.android.tp045027;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final Repository mInstance = new Repository();

    public static Repository getInstance() {
        return mInstance;
    }

    private ArrayList<Location> listLocation = new ArrayList<Location>();


    public List<Location> getLocation(SportsAdapter adapter) {
        RepositoryService service = ServiceGenerator.createService(RepositoryService.class);
        final ArrayList<Location> location_temp = new ArrayList<Location>();
        Call<List<Location>> call =service.getLocation();
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("PANTEQ")
                    listLocation.addAll(response.body());


                    location_temp.addAll(response.body());
                    adapter.setData(listLocation);
                    Gson gson = new Gson();
                    Log.d("PANTEQ", "repo: " +  gson.toJson(listLocation) + "");
                }


            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.e("Error fetching repos", t.getMessage());
            }
        });
        return location_temp;
    }

    public ArrayList<Location> getListData() {return listLocation;}
}
