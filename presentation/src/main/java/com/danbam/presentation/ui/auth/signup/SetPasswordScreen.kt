package com.danbam.presentation.ui.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danbam.design_system.IndiStrawTheme
import com.danbam.design_system.component.FindPasswordMedium
import com.danbam.design_system.component.HeadLineBold
import com.danbam.design_system.component.IndiStrawBottomSheetLayout
import com.danbam.design_system.component.IndiStrawButton
import com.danbam.design_system.component.IndiStrawCheckBox
import com.danbam.design_system.component.IndiStrawColumnBackground
import com.danbam.design_system.component.IndiStrawHeader
import com.danbam.design_system.component.IndiStrawTextField
import com.danbam.design_system.component.TitleRegular
import com.danbam.design_system.component.TitleSemiBold
import com.danbam.design_system.util.indiStrawClickable
import com.danbam.design_system.R
import com.danbam.presentation.ui.auth.navigation.AuthNavigationItem
import com.danbam.presentation.ui.main.navigation.MainNavigationItem
import com.danbam.presentation.util.android.observeWithLifecycle
import com.danbam.presentation.util.view.popBackStack
import com.danbam.presentation.util.view.requestFocus
import com.danbam.presentation.util.parser.toDp
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(
    ExperimentalMaterialApi::class, InternalCoroutinesApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SetPasswordScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel,
) {
    val container = signUpViewModel.container
    val state = container.stateFlow.collectAsState().value
    val sideEffect = container.sideEffectFlow

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rePassword by remember { mutableStateOf("") }
    var rePasswordVisible by remember { mutableStateOf(false) }
    var isAllApprove by remember { mutableStateOf(false) }
    var sheetVisible by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }
    val rePasswordFocusRequester = remember { FocusRequester() }

    val errorList = mapOf(
        SignUpSideEffect.EmptyNameException to stringResource(id = R.string.require_password),
        SignUpSideEffect.EmptyRePasswordException to stringResource(id = R.string.require_check_password),
        SignUpSideEffect.DifferentPasswordException to stringResource(id = R.string.wrong_different_password),
        SignUpSideEffect.LengthPasswordException to stringResource(id = R.string.wrong_length_password),
        SignUpSideEffect.MatchPasswordException to stringResource(id = R.string.wrong_match_password),
        SignUpSideEffect.FailSignUp to stringResource(id = R.string.fail_sign_up)
    )

    sideEffect.observeWithLifecycle {
        when (it) {
            is SignUpSideEffect.EmptyPasswordException, SignUpSideEffect.DifferentPasswordException, SignUpSideEffect.LengthPasswordException, SignUpSideEffect.MatchPasswordException -> {
                passwordFocusRequester.requestFocus(keyboardController = keyboardController)
                errorText = errorList[it]!!
            }

            is SignUpSideEffect.EmptyRePasswordException -> {
                rePasswordFocusRequester.requestFocus(keyboardController = keyboardController)
                errorText = errorList[it]!!
            }

            is SignUpSideEffect.SuccessSignUp -> {
                navController.navigate(AuthNavigationItem.Login.route) {
                    popUpTo(MainNavigationItem.Intro.route)
                }
            }

            is SignUpSideEffect.FailSignUp -> {
                errorText = errorList[it]!!
            }

            else -> {

            }
        }
    }

    LaunchedEffect(sheetVisible) {
        if (!sheetVisible && isAllApprove) {
            signUpViewModel.signUp()
        }
    }

    IndiStrawBottomSheetLayout(sheetContent = {
        PersonalInformationSheet(
            isAll = isAllApprove,
            onAll = {
                isAllApprove = !isAllApprove
            },
        )
    }) { sheetState, bottomSheetAction ->
        sheetVisible = sheetState.isVisible
        IndiStrawColumnBackground(
            onClickAction = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        ) {
            IndiStrawHeader(pressBackBtn = {
                navController.popBackStack(keyboardController = keyboardController)
            })
            HeadLineBold(
                modifier = Modifier
                    .padding(start = 32.dp, top = 16.dp),
                text = stringResource(id = R.string.require_password)
            )
            IndiStrawTextField(
                modifier = Modifier
                    .padding(top = 65.dp)
                    .focusRequester(focusRequester = passwordFocusRequester),
                hint = stringResource(id = R.string.password),
                value = password,
                onValueChange = {
                    if (errorText.isNotEmpty()) errorText = ""
                    password = it
                },
                imeAction = ImeAction.Next,
                isToggleVisible = passwordVisible,
                onToggleChange = { passwordVisible = !passwordVisible }
            )
            IndiStrawTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .focusRequester(focusRequester = rePasswordFocusRequester),
                hint = stringResource(id = R.string.check_password),
                value = rePassword,
                onValueChange = {
                    if (errorText.isNotEmpty()) errorText = ""
                    rePassword = it
                },
                isToggleVisible = rePasswordVisible,
                onToggleChange = { rePasswordVisible = !rePasswordVisible }
            )
            TitleRegular(
                modifier = Modifier.padding(start = 32.dp, top = 7.dp),
                text = errorText,
                color = IndiStrawTheme.colors.red,
                fontSize = 12
            )
            IndiStrawButton(
                modifier = Modifier.padding(top = 37.dp),
                text = stringResource(id = R.string.check)
            ) {
                signUpViewModel.setPassword(password = password, rePassword = rePassword) {
                    keyboardController?.hide()
                    bottomSheetAction()
                }
            }
        }
    }
}

@Composable
fun PersonalInformationSheet(
    isAll: Boolean,
    onAll: () -> Unit,
) {
    var context = LocalContext.current
    var size by remember { mutableStateOf(IntSize.Zero) }
    Column {
        Row(
            modifier = Modifier
                .padding(start = 35.dp, top = 42.dp)
                .indiStrawClickable(onClick = onAll),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IndiStrawCheckBox(isCheck = isAll, isBorder = true, onClick = onAll)
            TitleSemiBold(
                modifier = Modifier.padding(start = 13.dp),
                text = stringResource(id = R.string.approve_all),
                fontSize = 18
            )
        }
        Divider(
            modifier = Modifier
                .padding(start = 35.dp, end = 35.dp, top = 17.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    color = IndiStrawTheme.colors.gray,
                    shape = IndiStrawTheme.shapes.smallRounded
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp, top = 23.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Row {
                IndiStrawCheckBox(isCheck = isAll, onClick = { })
                TitleRegular(
                    modifier = Modifier
                        .padding(start = 13.dp)
                        .align(CenterVertically),
                    text = stringResource(id = R.string.require_terms_of_use)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                FindPasswordMedium(
                    modifier = Modifier.onSizeChanged {
                        size = it
                    },
                    text = stringResource(id = R.string.view_detail)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Divider(
                    modifier = Modifier
                        .width(size.width.toDp(context).dp)
                        .height(1.dp)
                        .background(IndiStrawTheme.colors.gray)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp, top = 38.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Row {
                IndiStrawCheckBox(isCheck = isAll, onClick = { })
                TitleRegular(
                    modifier = Modifier
                        .padding(start = 13.dp)
                        .align(CenterVertically),
                    text = stringResource(id = R.string.require_personal_information)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                FindPasswordMedium(
                    modifier = Modifier.onSizeChanged {
                        size = it
                    },
                    text = stringResource(id = R.string.view_detail)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Divider(
                    modifier = Modifier
                        .width(size.width.toDp(context).dp)
                        .height(1.dp)
                        .background(IndiStrawTheme.colors.gray)
                )
            }
        }
        Spacer(modifier = Modifier.height(117.dp))
    }
}