package teka.android.chamaaapp.data.remote.networking


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import teka.android.chamaaapp.features.feature_auth.data.remote.CustomAuthService
import teka.android.chamaaapp.util.AppConstants.BASE_URL

object AuthRetrofitProvider {

    private fun provide(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }//to ignore unkown keys

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkhttpClient(json.asConverterFactory("application/json".toMediaType())))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkhttpClient(
        asConverterFactory: Converter.Factory
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(HeaderInterceptor)
            .build()

    fun createAuthService(): CustomAuthService {
        return provide().create(CustomAuthService::class.java)
    }
}