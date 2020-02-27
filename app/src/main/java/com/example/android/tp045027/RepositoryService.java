package com.example.android.tp045027;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RepositoryService {
    @GET("location.json")
    Call<List<Location>> getLocation();
}
