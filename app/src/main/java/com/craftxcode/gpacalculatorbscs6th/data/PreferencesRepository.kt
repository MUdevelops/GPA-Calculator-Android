package com.craftxcode.gpacalculatorbscs6th.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.craftxcode.gpacalculatorbscs6th.domain.calculator.GradeCalculator
import com.craftxcode.gpacalculatorbscs6th.domain.model.GradeScale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesRepository(private val context: Context) {
    private val json = Json { ignoreUnknownKeys = true }

    object PreferencesKeys {
        val DARK_MODE = stringPreferencesKey("dark_mode")
        val GRADE_SCALE = stringPreferencesKey("grade_scale")
        val DECIMAL_PRECISION = intPreferencesKey("decimal_precision")
        val ANIMATIONS_ENABLED = booleanPreferencesKey("animations_enabled")
        val HAPTIC_ENABLED = booleanPreferencesKey("haptic_enabled")
    }

    val darkModeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DARK_MODE] ?: "System"
    }

    val gradeScaleFlow: Flow<GradeScale> = context.dataStore.data.map { preferences ->
        val scaleJson = preferences[PreferencesKeys.GRADE_SCALE]
        if (scaleJson != null) {
            try {
                json.decodeFromString<GradeScale>(scaleJson)
            } catch (_: Exception) {
                GradeCalculator.defaultScale
            }
        } else {
            GradeCalculator.defaultScale
        }
    }

    val decimalPrecisionFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DECIMAL_PRECISION] ?: 2
    }

    val animationsEnabledFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ANIMATIONS_ENABLED] ?: true
    }

    val hapticEnabledFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.HAPTIC_ENABLED] ?: true
    }

    suspend fun updateDarkMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = mode
        }
    }

    suspend fun updateGradeScale(scale: GradeScale) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.GRADE_SCALE] = json.encodeToString(scale)
        }
    }

    suspend fun updateDecimalPrecision(precision: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DECIMAL_PRECISION] = precision
        }
    }

    suspend fun updateAnimationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ANIMATIONS_ENABLED] = enabled
        }
    }

    suspend fun updateHapticEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.HAPTIC_ENABLED] = enabled
        }
    }
}
