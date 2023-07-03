package com.danbam.mobile.ui.funding.make

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danbam.design_system.component.IndiStrawButton
import com.danbam.design_system.component.IndiStrawColumnBackground
import com.danbam.design_system.component.TitleRegular
import com.danbam.design_system.R
import com.danbam.design_system.component.IndiStrawTextField
import com.danbam.design_system.component.SelectImageButton
import com.danbam.mobile.util.parser.toFile

@Composable
fun WriteIntroduceScreen(
    makeFundingViewModel: MakeFundingViewModel,
    onNext: () -> Unit
) {
    val container = makeFundingViewModel.container
    val state = container.stateFlow.collectAsState().value
    val sideEffect = container.sideEffectFlow

    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var thumbnailUrl: String? by remember { mutableStateOf(null) }
    IndiStrawColumnBackground {
        TitleRegular(
            modifier = Modifier.padding(start = 32.dp, top = 36.dp, bottom = 16.dp),
            text = stringResource(id = R.string.thumbnail)
        )
        SelectImageButton(
            imageUrl = state.thumbnailUrl,
            selectGallery = {
                it?.let {
                    makeFundingViewModel.uploadImage(it.toFile(context)) { thumbnail ->
                        thumbnailUrl = thumbnail
                    }
                }
            })
        TitleRegular(
            modifier = Modifier.padding(start = 32.dp, top = 28.dp, bottom = 16.dp),
            text = stringResource(id = R.string.title)
        )
        IndiStrawTextField(
            hint = stringResource(id = R.string.require_title),
            value = title,
            onValueChange = { title = it })
        TitleRegular(
            modifier = Modifier.padding(start = 32.dp, top = 28.dp, bottom = 16.dp),
            text = stringResource(id = R.string.introduce)
        )
        IndiStrawTextField(
            hint = stringResource(id = R.string.require_introduce),
            value = description,
            onValueChange = { description = it })
        TitleRegular(
            modifier = Modifier.padding(start = 32.dp, top = 28.dp),
            text = stringResource(id = R.string.highlight)
        )
        Spacer(modifier = Modifier.height(36.dp))
        IndiStrawButton(text = stringResource(id = R.string.next)) {
            makeFundingViewModel.saveIntroduce(
                thumbnailUrl = thumbnailUrl,
                title = title,
                description = description,
                imageList = listOf(),
                onNext = onNext
            )
        }
    }
}