package com.danbam.presentation.ui.certificate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.danbam.design_system.IndiStrawTheme
import com.danbam.design_system.component.FindPasswordMedium
import com.danbam.design_system.component.HeadLineBold
import com.danbam.design_system.component.IndiStrawButton
import com.danbam.design_system.component.IndiStrawHeader
import com.danbam.design_system.component.IndiStrawTextField
import com.danbam.design_system.component.TitleRegular
import com.danbam.design_system.util.indiStrawClickable
import com.danbam.presentation.R
import com.danbam.presentation.ui.login.LoginSideEffect
import com.danbam.presentation.util.AppNavigationItem
import com.danbam.presentation.util.CertificateType
import com.danbam.presentation.util.DeepLinkKey
import com.danbam.presentation.util.SignUpNavigationItem
import com.danbam.presentation.util.observeWithLifecycle
import com.danbam.presentation.util.toPhoneNumber
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalCoroutinesApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CertificateScreen(
    navController: NavController,
    certificateViewModel: CertificateViewModel = hiltViewModel(),
    certificateType: String,
) {
    val container = certificateViewModel.container
    val state = container.stateFlow.collectAsState().value
    val sideEffect = container.sideEffectFlow

    var phoneNumber by remember { mutableStateOf("") }
    var certificateNumber by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }
    var onReTimer by remember { mutableStateOf({}) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val phoneNumberFocusRequester = remember { FocusRequester() }
    val certificateNumberFocusRequester = remember { FocusRequester() }

    val errorList = mapOf(
        CertificateSideEffect.EmptyPhoneNumberException to stringResource(id = R.string.require_phone_number),
        CertificateSideEffect.MatchPhoneNumberException to stringResource(id = R.string.wrong_match_phone_number),
        CertificateSideEffect.EnrollPhoneNumberException to stringResource(id = R.string.wrong_enroll_phone_number),
        CertificateSideEffect.NotEnrollPhoneNumberException to stringResource(id = R.string.wrong_not_enroll_phone_number),
        CertificateSideEffect.EmptyCertificateNumberException to stringResource(id = R.string.require_certificate_number),
        CertificateSideEffect.WrongCertificateNumberException to stringResource(id = R.string.wrong_certificate_number),
        CertificateSideEffect.ExpiredCertificateNumberException to stringResource(id = R.string.wrong_expired_certificate_number),
    )

    sideEffect.observeWithLifecycle {
        when (it) {
            is CertificateSideEffect.EmptyPhoneNumberException, CertificateSideEffect.MatchPhoneNumberException, CertificateSideEffect.EnrollPhoneNumberException, CertificateSideEffect.NotEnrollPhoneNumberException -> {
                keyboardController?.show()
                phoneNumberFocusRequester.requestFocus()
                errorText = errorList[it]!!
            }

            is CertificateSideEffect.EmptyCertificateNumberException, CertificateSideEffect.WrongCertificateNumberException, CertificateSideEffect.ExpiredCertificateNumberException -> {
                keyboardController?.show()
                certificateNumberFocusRequester.requestFocus()
                errorText = errorList[it]!!
            }

            is CertificateSideEffect.SuccessCertificate -> {
                keyboardController?.hide()
                when (certificateType) {
                    CertificateType.SIGN_UP -> navController.navigate(SignUpNavigationItem.SetProfile.route + DeepLinkKey.PHONE_NUMBER + phoneNumber.toPhoneNumber())
                    CertificateType.FIND_ID -> navController.navigate(AppNavigationItem.FindId.route + DeepLinkKey.PHONE_NUMBER + phoneNumber.toPhoneNumber())
                    CertificateType.FIND_PASSWORD -> navController.navigate(AppNavigationItem.FindPassword.route + DeepLinkKey.PHONE_NUMBER + phoneNumber.toPhoneNumber())
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .indiStrawClickable {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
    ) {
        IndiStrawHeader(
            marginTop = 25,
            backStringId = R.string.back,
            pressBackBtn = {
                navController.popBackStack()
            })
        HeadLineBold(
            modifier = Modifier
                .padding(start = 32.dp, top = 16.dp),
            text = stringResource(id = if (state.phoneNumber.isNotEmpty()) R.string.require_certificate_number else R.string.require_phone_number)
        )
        IndiStrawTextField(
            modifier = Modifier
                .padding(top = 96.dp)
                .focusRequester(focusRequester = phoneNumberFocusRequester),
            hint = stringResource(id = R.string.phone_number),
            value = phoneNumber,
            onValueChange = {
                if (errorText.isNotEmpty()) errorText = ""
                phoneNumber = it
            },
            readOnly = state.phoneNumber.isNotEmpty(),
            keyboardType = KeyboardType.Number
        )
        if (state.phoneNumber.isNotEmpty()) {
            IndiStrawTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .focusRequester(focusRequester = certificateNumberFocusRequester),
                hint = stringResource(id = R.string.certificate_number),
                value = certificateNumber,
                onValueChange = {
                    if (errorText.isNotEmpty()) errorText = ""
                    certificateNumber = it
                },
                keyboardType = KeyboardType.Number,
                isTimer = true,
                onReTimer = { onReTimer = it },
                onExpiredTime = { certificateViewModel.expiredCertificateNumber() }
            )
        }
        TitleRegular(
            modifier = Modifier.padding(start = 32.dp, top = 7.dp),
            text = errorText,
            color = IndiStrawTheme.colors.error,
            fontSize = 12
        )
        IndiStrawButton(
            modifier = Modifier.padding(top = (if (state.phoneNumber.isNotEmpty()) 37 else 78).dp),
            text = stringResource(id = if (state.phoneNumber.isNotEmpty()) R.string.check_certificate_number else R.string.certificate_number)
        ) {
            if (state.phoneNumber.isEmpty()) {
                certificateViewModel.checkPhoneNumber(
                    phoneNumber = phoneNumber.toPhoneNumber(),
                    isSignUp = certificateType == CertificateType.SIGN_UP
                )
            } else {
                certificateViewModel.checkCertificateNumber(authCode = certificateNumber)
            }
        }
        if (state.phoneNumber.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .indiStrawClickable(onClick = onReTimer),
                horizontalArrangement = Arrangement.Center
            ) {
                FindPasswordMedium(
                    text = stringResource(id = R.string.already_certificate_number),
                    color = IndiStrawTheme.colors.exampleText
                )
                Spacer(modifier = Modifier.width(2.dp))
                FindPasswordMedium(text = stringResource(id = R.string.re_send))
            }
        }
    }
}