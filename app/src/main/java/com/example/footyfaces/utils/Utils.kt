package com.example.footyfaces.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import retrofit2.HttpException

sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val error: ErrorEntity) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

sealed class ErrorEntity {
    data object Network : ErrorEntity()
    data object NotFound : ErrorEntity()
    data object AccessDenied : ErrorEntity()
    data object ServiceUnavailable : ErrorEntity()
    data class Unknown(val error: String) : ErrorEntity()
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

@Composable
fun dimenResource(id: Int): Float {
    val context = LocalContext.current
    return context.resources.getDimension(id)
}

fun getFullImageUrl(relativeUrl: String): String {
    if (!relativeUrl.startsWith("http://") && !relativeUrl.startsWith("https://")) {
        return "https:$relativeUrl"
    }
    return relativeUrl
}

fun mapHttpExceptionToDomainError(exception: HttpException): ErrorEntity {
    return when (exception.code()) {
        404 -> ErrorEntity.NotFound
        403 -> ErrorEntity.AccessDenied
        503 -> ErrorEntity.ServiceUnavailable
        else -> ErrorEntity.Unknown(exception.localizedMessage ?: "An error occurred")
    }
}


open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
