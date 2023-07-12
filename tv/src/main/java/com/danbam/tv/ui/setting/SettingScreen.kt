package com.danbam.tv.ui.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Border
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import coil.compose.AsyncImage
import com.danbam.design_system.IndiStrawTheme
import com.danbam.design_system.component.DialogMedium
import com.danbam.design_system.component.IndiStrawTvBackground
import com.danbam.design_system.R
import com.danbam.design_system.component.ExampleTextMedium
import com.danbam.design_system.component.TitleRegular

sealed class SettingNavigation(val stringId: Int) {
    companion object {
        fun toList() = listOf(Language, Term, Account, Logout, Withdrawal)
    }

    object Language : SettingNavigation(R.string.tv_setting_language)
    object Term : SettingNavigation(R.string.tv_setting_term)
    object Account : SettingNavigation(R.string.tv_setting_account)
    object Logout : SettingNavigation(R.string.tv_setting_logout)
    object Withdrawal : SettingNavigation(R.string.tv_setting_withdrawal)
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SettingScreen(

) {
    var selectedSettingMenu: SettingNavigation? by remember { mutableStateOf(null) }

    IndiStrawTvBackground {
        Row {
            Column(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.hasFocus) {
                            selectedSettingMenu = null
                        }
                    }
                    .padding(start = 40.dp, top = 60.dp, bottom = 100.dp)
                    .fillMaxWidth(0.23F)
                    .fillMaxHeight()
            ) {
                DialogMedium(text = stringResource(id = R.string.setting), fontSize = 48)
                Spacer(modifier = Modifier.height(60.dp))
                TvLazyColumn {
                    items(SettingNavigation.toList()) {
                        Surface(
                            modifier = Modifier
                                .padding(top = if (it == SettingNavigation.Logout) 50.dp else 20.dp)
                                .fillMaxWidth(),
                            scale = ClickableSurfaceDefaults.scale(
                                focusedScale = 1F
                            ),
                            color = ClickableSurfaceDefaults.color(
                                color = Color.Transparent,
                                focusedColor = Color.Transparent,
                                pressedColor = Color.Transparent,
                                disabledColor = Color.Transparent
                            ),
                            border = ClickableSurfaceDefaults.border(
                                focusedBorder = Border(
                                    border = BorderStroke(3.dp, IndiStrawTheme.colors.main),
                                    shape = IndiStrawTheme.shapes.smallRounded
                                )
                            ),
                            onClick = {
                                selectedSettingMenu = it
                            }
                        ) {
                            TitleRegular(
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 4.dp
                                ),
                                text = stringResource(id = it.stringId),
                                fontSize = 31,
                                color = IndiStrawTheme.colors.gray
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(40.dp))
            when (selectedSettingMenu) {
                is SettingNavigation.Language -> {
                    SettingLanguageScreen()
                }

                is SettingNavigation.Term -> {}
                is SettingNavigation.Account -> {}
                is SettingNavigation.Logout -> {}
                is SettingNavigation.Withdrawal -> {}
                else -> {}
            }
            Spacer(modifier = Modifier.weight(1F))
            Column(
                modifier = Modifier
                    .width(180.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(IndiStrawTheme.shapes.circle),
                    model = "https://media.discordapp.net/attachments/823502916257972235/1111432831089000448/IMG_1218.png?width=1252&height=1670",
                    contentDescription = "profileThumbnail",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(14.dp))
                ExampleTextMedium(text = "홍길동", fontSize = 35)
            }
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}