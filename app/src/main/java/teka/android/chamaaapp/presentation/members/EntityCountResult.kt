package teka.android.chamaaapp.presentation.members

sealed class EntityCountResult<out T> {
    data class Success<out T>(val data: T) : EntityCountResult<T>()
    data class Error(val exception: Exception) : EntityCountResult<Nothing>()
}
