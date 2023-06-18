package com.danbam.data.remote.datasource

import com.danbam.data.remote.api.AccountAPI
import com.danbam.data.remote.request.ChangePasswordRequest
import com.danbam.data.remote.response.FindIdResponse
import com.danbam.data.remote.response.ProfileResponse
import com.danbam.data.remote.util.indiStrawApiCall
import javax.inject.Inject

class AccountRemoteDataSourceImpl @Inject constructor(
    private val accountAPI: AccountAPI,
) : AccountRemoteDataSource {
    override suspend fun findId(phoneNumber: String): FindIdResponse = indiStrawApiCall {
        accountAPI.findId(phoneNumber = phoneNumber)
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest) =
        indiStrawApiCall {
            accountAPI.changePassword(changePasswordRequest = changePasswordRequest)
        }

    override suspend fun getProfile(): ProfileResponse = indiStrawApiCall {
        accountAPI.getProfile()
    }
}