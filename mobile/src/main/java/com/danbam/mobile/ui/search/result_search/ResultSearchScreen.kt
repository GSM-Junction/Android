package com.danbam.mobile.ui.search.result_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.danbam.design_system.IndiStrawTheme
import com.danbam.design_system.component.IndiStrawColumnBackground
import com.danbam.design_system.component.IndiStrawTab
import com.danbam.design_system.R
import com.danbam.design_system.component.ExampleTextMedium
import com.danbam.design_system.component.FundingItem
import com.danbam.design_system.component.HeadLineBold
import com.danbam.design_system.component.IndiStrawProgress
import com.danbam.design_system.component.SearchTab
import com.danbam.design_system.component.TitleRegular
import com.danbam.design_system.util.RemoveOverScrollLazyColumn
import com.danbam.design_system.util.indiStrawClickable
import com.danbam.domain.entity.FundingEntity
import com.danbam.mobile.ui.movie.navigation.MovieNavigationItem

@Composable
fun ResultSearchScreen(
    navController: NavController,
    resultSearchViewModel: ResultSearchViewModel = hiltViewModel(),
    onClickAction: (() -> Unit),
    keyword: String,
) {
    var currentTab: SearchTab by remember { mutableStateOf(SearchTab.Movie) }

    LaunchedEffect(Unit) {
        resultSearchViewModel.search(keyword = keyword)
    }

    IndiStrawColumnBackground(
        onClickAction = onClickAction
    ) {
        Row(
            modifier = Modifier.padding(start = 25.dp, top = 22.dp)
        ) {
            IndiStrawTab(
                text = stringResource(id = R.string.indi_movie),
                isSelect = currentTab == SearchTab.Movie
            ) {
                currentTab = SearchTab.Movie
            }
            Spacer(modifier = Modifier.width(16.dp))
            IndiStrawTab(
                text = stringResource(id = R.string.crowd_funding),
                isSelect = currentTab == SearchTab.Funding
            ) {
                currentTab = SearchTab.Funding
            }
        }
        when (currentTab) {
            is SearchTab.Movie -> {
                RemoveOverScrollLazyColumn(
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    items(20) {
                        Row {
                            MovieItem(
                                modifier = Modifier.weight(1F)
                            ) {
                                navController.navigate(MovieNavigationItem.MovieDetail.route)
                            }
                            Spacer(modifier = Modifier.width(15.dp))
                            MovieItem(
                                modifier = Modifier.weight(1F)
                            ) {
                                navController.navigate(MovieNavigationItem.MovieDetail.route)
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            is SearchTab.Funding -> {
                Spacer(modifier = Modifier.height(11.dp))
                RemoveOverScrollLazyColumn {
                    items(20) {
                        FundingItem(
                            item = FundingEntity(
                                0,
                                "존윅",
                                "진짜 재밌음",
                                50,
                                "https://media.discordapp.net/attachments/823502916257972235/1111432831089000448/IMG_1218.png?width=1252&height=1670",
                                ""
                            )
                        ) {

                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    onSelect: () -> Unit,
) {
    Column(
        modifier = modifier
            .indiStrawClickable(onClick = onSelect)
            .background(
                color = IndiStrawTheme.colors.darkGray,
                shape = IndiStrawTheme.shapes.smallRounded
            )
            .padding(start = 7.dp, end = 7.dp, top = 7.dp, bottom = 24.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth()
                .clip(IndiStrawTheme.shapes.smallRounded),
            model = "https://media.discordapp.net/attachments/823502916257972235/1111432831089000448/IMG_1218.png?width=1252&height=1670",
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        HeadLineBold(text = "존 윅: 리로드", fontSize = 16)
        Spacer(modifier = Modifier.height(4.dp))
        TitleRegular(
            text = "과거에 진 빛을 갚아야만 하는 존 윅 처단고 싶지 않은 표적을 어...과거에 진 빛을 갚아야만 하는 존 윅 처단고 싶지 않은 표적을 어...과거에 진 빛을 갚아야만 하는 존 윅 처단고 싶지 않은 표적을 어...과거에 진 빛을 갚아야만 하는 존 윅 처단고 싶지 않은 표적을 어...",
            fontSize = 12,
            maxLines = 2
        )
    }
}