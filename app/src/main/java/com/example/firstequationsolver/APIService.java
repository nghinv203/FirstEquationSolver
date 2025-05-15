package com.example.firstequationsolver;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface APIService {
    @GET("Customers")
    Call<List<Customer>> getAll();
}
