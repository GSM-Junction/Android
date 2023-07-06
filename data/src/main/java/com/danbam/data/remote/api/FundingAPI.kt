package com.danbam.data.remote.api

import com.danbam.data.remote.request.FundingCreateRequest
import com.danbam.data.remote.response.FundingPageResponse
import com.danbam.data.remote.response.FundingDetailResponse
import com.danbam.data.remote.response.FundingResponse
import com.danbam.data.remote.util.EndPoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FundingAPI {
    @POST("${EndPoint.FUNDING}")
    suspend fun fundingCreate(
        @Body fundingCreateRequest: FundingCreateRequest
    ): Response<Void?>

    @GET("${EndPoint.FUNDING}/popular/list")
    suspend fun fundingPopularList(): List<FundingResponse>

    @GET("${EndPoint.FUNDING}/{idx}")
    suspend fun fundingDetail(
        @Path("idx") fundingIndex: Long
    ): FundingDetailResponse

    @GET("${EndPoint.FUNDING}/list")
    suspend fun fundingAll(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
    ): FundingPageResponse
}