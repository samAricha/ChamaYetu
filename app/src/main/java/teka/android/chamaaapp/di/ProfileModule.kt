package teka.android.chamaaapp.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import teka.android.chamaaapp.features.feature_auth.data.local.AuthPreferences
import teka.android.chamaaapp.presentation.profile.data.repository.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileRepository(authPreferences: AuthPreferences): ProfileRepository {
        return ProfileRepository(authPreferences)
    }
}