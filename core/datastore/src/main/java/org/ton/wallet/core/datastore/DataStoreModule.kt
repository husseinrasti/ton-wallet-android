package org.ton.wallet.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import org.ton.wallet.core.dagger.hilt.scope.ApplicationCoroutineIoScope
import javax.inject.Singleton


private const val SHARED_PREF_NAME = "TonWalletAndroid"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context,
        @ApplicationCoroutineIoScope coroutineScope: CoroutineScope,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile(SHARED_PREF_NAME)
            },
            scope = coroutineScope
        )
    }

}