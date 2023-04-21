package org.ton.wallet.core.dagger.hilt.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.ton.wallet.core.dagger.hilt.scope.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {

    @Singleton
    @Provides
    @ApplicationDefaultScope
    fun providesApplicationDefaultScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @Singleton
    @Provides
    @ApplicationIoScope
    fun providesApplicationIoScope(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)

    @Singleton
    @Provides
    @CoroutineDefaultScope
    fun providesCoroutineDefaultScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(defaultDispatcher)

    @Singleton
    @Provides
    @CoroutineIoScope
    fun providesCoroutineIoScope(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(ioDispatcher)

    @Singleton
    @Provides
    @CoroutineMainScope
    fun providesCoroutineMainScope(
        @MainDispatcher mainDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(mainDispatcher)

    @Singleton
    @Provides
    @CoroutineMainImmediateScope
    fun providesCoroutineMainImmediateScope(
        @MainImmediateDispatcher mainImmediateDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(mainImmediateDispatcher)

}