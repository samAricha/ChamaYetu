package teka.android.chamaaapp.data.remote.networking

import okhttp3.Interceptor

object HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("Accept", "application/vnd.api+json")
            .addHeader("Authorization", "Bearer ")
            .build()
        return chain.proceed(newRequest)
    }
}
