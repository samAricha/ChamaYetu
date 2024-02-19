package teka.android.chamaaapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import teka.android.chamaaapp.features.feature_auth.data.local.AuthPreferences
import teka.android.chamaaapp.features.feature_auth.data.remote.AuthApiService
import teka.android.chamaaapp.features.feature_auth.data.repository.LoginRepositoryImpl
import teka.android.chamaaapp.features.feature_auth.domain.repository.LoginRepository
import teka.android.chamaaapp.features.feature_auth.domain.use_case.AutoLoginUseCase
import teka.android.chamaaapp.features.feature_auth.domain.use_case.LoginUseCase
import teka.android.chamaaapp.features.feature_auth.domain.use_case.LogoutUseCase
import teka.android.chamaaapp.features.feature_auth.repository.DataStoreRepository
import teka.android.chamaaapp.util.AppConstants.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        authApiService: AuthApiService,
        authPreferences: AuthPreferences
    ): LoginRepository {
        return LoginRepositoryImpl(
            authApiService = authApiService,
            authPreferences = authPreferences
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideAutoLoginUseCase(loginRepository: LoginRepository): AutoLoginUseCase {
        return AutoLoginUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(loginRepository: LoginRepository): LogoutUseCase {
        return LogoutUseCase(loginRepository)
    }

}
