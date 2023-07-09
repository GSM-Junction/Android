package com.danbam.mobile.ui.profile.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danbam.domain.usecase.account.GetProfileUseCase
import com.danbam.domain.usecase.crowd_funding.FundingMyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val fundingMyUseCase: FundingMyUseCase
) : ContainerHost<ProfileState, Unit>, ViewModel() {
    override val container = container<ProfileState, Unit>(ProfileState())
    fun getProfile() = intent {
        viewModelScope.launch {
            getProfileUseCase().onSuccess {
                reduce { state.copy(id = it.id, name = "${it.name}님", profileUrl = it.profileUrl) }
            }
        }
    }

    fun getMyFunding() = intent {
        viewModelScope.launch {
            fundingMyUseCase().onSuccess {
                reduce { state.copy(fundingList = it) }
            }
        }
    }
}