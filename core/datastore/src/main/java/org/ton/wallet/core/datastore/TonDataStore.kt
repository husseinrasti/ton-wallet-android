package org.ton.wallet.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.ton.wallet.core.crypto.SecurityUtil
import javax.inject.Inject


class TonDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val security: SecurityUtil,
) {

    private val securityKeyAlias = "data-store"
    private val bytesToStringSeparator = "|"

    suspend fun clear() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    val phrases: Flow<Result<List<String>>> = dataStore.data
        .secureMap { preferences ->
            preferences[PreferenceKeys.KEY_PHRASES].orEmpty()
        }

    suspend fun setPhrases(phrases: List<String>) {
        dataStore.secureEdit(phrases) { preferences, encryptedValue ->
            preferences[PreferenceKeys.KEY_PHRASES] = encryptedValue
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
        .secureMap { preferences ->
            preferences[PreferenceKeys.KEY_PASSCODE].orEmpty()
        }

    suspend fun setPasscode(passcode: String) {
        dataStore.secureEdit(passcode) { preferences, encryptedValue ->
            preferences[PreferenceKeys.KEY_PASSCODE] = encryptedValue
        }
    }

    private inline fun <reified T> Flow<Preferences>.secureMap(
        crossinline fetchValue: (value: Preferences) -> String
    ): Flow<Result<T>> {
        return catch {
            it.printStackTrace()
            emit(preferencesOf())
        }.map { preferences ->
            fetchValue(preferences).takeIf { it.isNotEmpty() }?.let { value ->
                val decryptedValue = security.decryptData(
                    securityKeyAlias,
                    value.split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
                )
                Result.success<T>(Gson().fromJson(decryptedValue, object : TypeToken<T>() {}.type))
            } ?: Result.failure(IllegalArgumentException())
        }
    }

    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline editStore: (MutablePreferences, String) -> Unit
    ) {
        edit { preferences ->
            val encryptedValue = security.encryptData(securityKeyAlias, Gson().toJson(value))
            editStore.invoke(preferences, encryptedValue.joinToString(bytesToStringSeparator))
        }
    }

}