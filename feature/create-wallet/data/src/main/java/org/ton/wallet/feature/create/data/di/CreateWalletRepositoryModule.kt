package org.ton.wallet.feature.create.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.ton.wallet.feature.create.data.repository.CreateWalletRepositoryImpl
import org.ton.wallet.feature.create.domain.repository.CreateWalletRepository

@Module
@InstallIn(ViewModelComponent::class)
interface CreateWalletRepositoryModule {

    @Binds
    @ViewModelScoped
    fun provideRepository(
        createWalletRepositoryImpl: CreateWalletRepositoryImpl
    ): CreateWalletRepository

}