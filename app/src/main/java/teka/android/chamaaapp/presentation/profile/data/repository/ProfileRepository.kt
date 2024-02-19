package teka.android.chamaaapp.presentation.profile.data.repository

import kotlinx.coroutines.flow.Flow
import teka.android.chamaaapp.features.feature_auth.data.local.AuthPreferences

class ProfileRepository(private val authPreferences: AuthPreferences) {
    fun getUserProfile(): Flow<String> {
        return authPreferences.getUserData
    }
}