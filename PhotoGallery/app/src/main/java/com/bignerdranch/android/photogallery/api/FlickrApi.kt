package com.bignerdranch.android.photogallery.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=3327404b47eed9850e37d75bc926a6e8" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )

    fun fetchPhotos(): Call<FlickrResponse>
}