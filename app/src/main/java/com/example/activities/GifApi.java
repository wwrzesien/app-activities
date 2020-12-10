package com.example.activities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GifApi {
//    https://api.giphy.com/v1/gifs/search?api_key=fuykYlUBxJ0K8jpIaGz5i42Bz5NF7FaI&q=snow&limit=1&offset=0&rating=g&lang=en
//    api.giphy.com/v1/gifs/search
    String BASE_URL = "https://api.giphy.com/v1/";

//    @GET("gifs/search?api_key=fuykYlUBxJ0K8jpIaGz5i42Bz5NF7FaI")
//    Call<Gif> getGif(@Query("q") String clueName,
//                     @Query("limit") String limit,
//                     @Query("offset") String offset,
//                     @Query("rating") String rating,
//                     @Query("lang") String lang);
    @GET("gifs/search?api_key=fuykYlUBxJ0K8jpIaGz5i42Bz5NF7FaI&q=snow&limit=1&offset=0&rating=g&lang=en")
    Call<Gif> getGif();
}
