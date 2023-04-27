package org.ton.wallet.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TonDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun clear() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    val phrases: Flow<Result<List<String>>> = dataStore.data
        .catch {
            it.printStackTrace()
            emit(preferencesOf())
        }.map { preferences ->
            preferences[PreferenceKeys.KEY_PHRASES]?.takeIf { it.isNotEmpty() }?.let {
                Result.success(Mapper.fromJson(it))
            } ?: Result.failure(IllegalArgumentException())
        }

    suspend fun setPhrases(phrases: List<String>) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.KEY_PHRASES] = Mapper.toJson(phrases)
        }
    }

    val isActiveBiometric: Flow<Result<Boolean>> = dataStore.data
        .catch {
            it.printStackTrace()
            emit(preferencesOf())
        }.map { preferences ->
            preferences[PreferenceKeys.KEY_BIOMETRIC]?.let {
                Result.success(it)
            } ?: Result.failure(IllegalArgumentException())
        }

    suspend fun setActiveBiometric(isActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.KEY_BIOMETRIC] = isActive
        }
    }

    val passcodeBiometric: Flow<Result<String>> = dataStore.data
        .catch {
            it.printStackTrace()
            emit(preferencesOf())
        }.map { preferences ->
            preferences[PreferenceKeys.KEY_PASSCODE]?.let {
                Result.success(it)
            } ?: Result.failure(IllegalArgumentException())
        }

    suspend fun setPasscode(passcodeHash: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.KEY_PASSCODE] = passcodeHash
        }
    }

}