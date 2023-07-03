package com.danbam.data.remote.api

import com.danbam.data.remote.response.FundingDetailResponse
import com.danbam.data.remote.response.FundingResponse
import com.danbam.data.remote.util.EndPoint
import retrofit2.http.GET
import retrofit2.http.Path

interface FundingAPI {
    @GET("${EndPoint.FUNDING}/popular/list")
    suspend fun fundingPopularList(): List<FundingResponse>

    @GET("${EndPoint.FUNDING}/{idx}")
    suspend fun fundingDetail(
        @Path("idx") fundingIndex: Long
    ): FundingDetailResponse
}