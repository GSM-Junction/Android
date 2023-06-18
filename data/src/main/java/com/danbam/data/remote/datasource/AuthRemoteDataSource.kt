package com.danbam.data.remote.datasource

import com.danbam.data.remote.request.LoginRequest
import com.danbam.data.remote.request.SignUpRequest
import com.danbam.data.remote.response.LoginResponse

interface AuthRemoteDataSource {
    suspend fun signup(signUpRequest: SignUpRequest)
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun refresh(refreshToken: String): LoginResponse
    suspend fun checkPhoneNumber(phoneNumber: String, type: String): Void?
    suspend fun checkId(id: String): Void?
    suspend fun sendCertificateNumber(phoneNumber: String): Void?
    suspend fun checkCertificateNumber(authCode: Int, phoneNumber: String): Void?
}