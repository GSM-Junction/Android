package com.danbam.design_system.component

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.danbam.design_system.IndiStrawTheme
import com.danbam.design_system.attribute.IndiStrawIcon
import com.danbam.design_system.attribute.IndiStrawIconList
import com.danbam.design_system.util.RemoveOverScrollLazyRow
import com.danbam.design_system.util.indiStrawClickable

@Composable
fun AddImageList(
    modifier: Modifier = Modifier,
    imageList: List<String>,
    onRemove: (Int) -> Unit,
    selectGallery: (Uri?) -> Unit
) {
    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectGallery(result.data?.data)
            }
        }
    val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_PICK
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    IndiStrawTheme.colors.lightBlack,
                    IndiStrawTheme.shapes.circle
                )
                .padding(21.dp)
                .indiStrawClickable {
                    takePhotoFromAlbumLauncher.launch(
                        takePhotoFromAlbumIntent
                    )
                }
        ) {
            IndiStrawIcon(
                modifier = Modifier.align(Alignment.Center),
                icon = IndiStrawIconList.Plus
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        RemoveOverScrollLazyRow {
            itemsIndexed(imageList) { index, item ->
                Box {
                    AsyncImage(
                        modifier = Modifier
                            .padding(top = 7.dp, end = 7.dp)
                            .height(100.dp)
                            .width(160.dp)
                            .clip(IndiStrawTheme.shapes.smallRounded),
                        model = item,
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop
                    )
                    IndiStrawIcon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .indiStrawClickable { onRemove(index) },
                        icon = IndiStrawIconList.Delete
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}