package com.example.projetofinalapril.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface SupabaseAPI {


    String BASE_URL = "https://seu-projeto.supabase.co/rest/v1/";
    String API_KEY = "sua-chave-api";


    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @GET("tabela_exemplo")
    Call<List<Usuario>> obterDados(@Query("select") String colunas);


    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @POST("tabela_exemplo")
    Call<Void> inserirDados(@Body Dados novoDado);
}
