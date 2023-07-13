package com.danbam.domain.repository

import androidx.paging.PagingData
import com.danbam.domain.entity.FundingEntity
import com.danbam.domain.entity.MovieEntity
import com.danbam.domain.entity.RecentSearchEntity
import com.danbam.domain.entity.RelatedSearchEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getRelatedSearch(keyword: String): List<RelatedSearchEntity>
    suspend fun searchMovie(recentSearchEntity: RecentSearchEntity): Flow<PagingData<MovieEntity>>
    suspend fun searchFunding(recentSearchEntity: RecentSearchEntity): Flow<PagingData<FundingEntity>>
    suspend fun getRecentSearch(): List<RecentSearchEntity>
    suspend fun popularTag(): List<String>
}