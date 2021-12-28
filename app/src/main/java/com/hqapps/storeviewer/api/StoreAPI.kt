package com.hqapps.storeviewer.api

import com.hqapps.storeviewer.api.StoreAPIConf.CLIENT_APPLICATION_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreAPI {

    @GET("rest/stores")
    suspend fun getStores(
        @Query("longitude") longitude: Float,
        @Query("latitude") latitude: Float,
        @Query("radius") radius: Int,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("clientApplicationKey") clientApplicationKey: String = CLIENT_APPLICATION_KEY
    ): StoreResponse
}
