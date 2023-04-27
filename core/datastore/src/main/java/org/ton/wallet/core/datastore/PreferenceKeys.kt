package org.ton.wallet.core.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val KEY_PHRASES = stringPreferencesKey("phrases")
    val KEY_BIOMETRIC = booleanPreferencesKey("biometric")
    val KEY_PASSCODE = stringPreferencesKey("passcode")
}