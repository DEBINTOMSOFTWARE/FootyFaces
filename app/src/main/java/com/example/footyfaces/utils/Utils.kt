package com.example.footyfaces.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

sealed class Resource<out T> {
    class Initial<T>() : Resource<T>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

@Composable
fun PlayerImage(
    url: String?,
    modifier: Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

fun getFullImageUrl(relativeUrl: String): String {
    if (!relativeUrl.startsWith("http://") && !relativeUrl.startsWith("https://")) {
        return "https:$relativeUrl"
    }
    return relativeUrl
}
