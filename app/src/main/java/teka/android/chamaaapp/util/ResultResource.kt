package teka.android.chamaaapp.util

sealed class ResultResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultResource<T>(data)
    class Loading<T>(data: T? = null) : ResultResource<T>(data)
    class Error<T>(message: String, data: T? = null) : ResultResource<T>(data, message)
}