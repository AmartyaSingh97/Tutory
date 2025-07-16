package com.amartyasingh.tutory.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREF_NAME = "onboarding_prefs"
private val Context.dataStore by preferencesDataStore(name = PREF_NAME)

object OnboardingPreferences {
    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

    fun isOnboardingCompleted(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[ONBOARDING_COMPLETED] == true
        }
    }

    suspend fun setOnboardingCompleted(context: Context) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = true
        }
    }
}
