package org.ton.wallet.feature.create.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.ton.wallet.feature.create.domain.usecase.GetMnemonicUseCase
import org.ton.wallet.feature.create.domain.usecase.GetMnemonicUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface CreateWalletUseCaseModule {

    @Binds
    @ViewModelScoped
    fun provideGetMnemonicUseCase(
        getMnemonicUseCaseImpl: GetMnemonicUseCaseImpl
    ): GetMnemonicUseCase

}