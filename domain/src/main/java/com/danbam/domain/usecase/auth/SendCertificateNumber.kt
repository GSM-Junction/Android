package com.danbam.domain.usecase.auth

import com.danbam.domain.repository.AuthRepository
import javax.inject.Inject

class SendCertificateNumber @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(phoneNumber: String) = runCatching {
        authRepository.sendCertificateNumber(phoneNumber = phoneNumber)
    }
}