package com.example.nejc.elpinjo.API;

import com.example.nejc.elpinjo.models.Region;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface RegionsAPI {
    @GET("/api/v1/regions")
    void getRegions(Callback<List<Region>> response);
}
