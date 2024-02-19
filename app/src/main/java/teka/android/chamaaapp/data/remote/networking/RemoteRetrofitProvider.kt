package teka.android.chamaaapp.data.remote.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import teka.android.chamaaapp.data.remote.services.AccountTypeRetrofitService
import teka.android.chamaaapp.data.remote.services.ChamaaAccountRetrofitService
import teka.android.chamaaapp.data.remote.services.ChamaaRetrofitService
import teka.android.chamaaapp.data.remote.services.ContributionRetrofitService
import teka.android.chamaaapp.data.remote.services.MemberRetrofitService
import teka.android.chamaaapp.util.AppConstants.BASE_URL
import teka.android.chamaaapp.util.AppConstants.TEST_URL

object RemoteRetrofitProvider {

    private fun provide(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }//to ignore unkown keys

        return Retrofit.Builder()
            .baseUrl(TEST_URL)
            .client(provideOkhttpClient())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private fun provideOkhttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(HeaderInterceptor)
            .build()


    fun createMemberRetrofitService(): MemberRetrofitService {
        return provide().create(MemberRetrofitService::class.java)
    }

    fun createContributionRetrofitService(): ContributionRetrofitService {
        return provide().create(ContributionRetrofitService::class.java)
    }

    fun createAccountTypesRetrofitService(): AccountTypeRetrofitService {
        return provide().create(AccountTypeRetrofitService::class.java)
    }

    fun createChamaasRetrofitService(): ChamaaRetrofitService {
        return provide().create(ChamaaRetrofitService::class.java)
    }

    fun createChamaaAccountsRetrofitService(): ChamaaAccountRetrofitService {
        return provide().create(ChamaaAccountRetrofitService::class.java)
    }

}