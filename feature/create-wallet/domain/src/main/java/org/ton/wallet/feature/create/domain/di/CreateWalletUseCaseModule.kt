package org.ton.wallet.feature.create.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.ton.wallet.feature.create.domain.usecase.FilterPhrasesUseCase
import org.ton.wallet.feature.create.domain.usecase.FilterPhrasesUseCaseImpl
import org.ton.wallet.feature.create.domain.usecase.GeneratePhrasesUseCase
import org.ton.wallet.feature.create.domain.usecase.GeneratePhrasesUseCaseImpl
import org.ton.wallet.feature.create.domain.usecase.GetPhrasesUseCase
import org.ton.wallet.feature.create.domain.usecase.GetPhrasesUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface CreateWalletUseCaseModule {

    @Binds
    @ViewModelScoped
    fun provideFilterPhrasesUseCase(
        filterPhrasesUseCaseImpl: FilterPhrasesUseCaseImpl
    ): FilterPhrasesUseCase

    @Binds
    @ViewModelScoped
    fun provideGeneratePhrasesUseCase(
        generatePhrasesUseCaseImpl: GeneratePhrasesUseCaseImpl
    ): GeneratePhrasesUseCase

    @Binds
    @ViewModelScoped
    fun provideGetPhrasesUseCase(
        getPhrasesUseCaseImpl: GetPhrasesUseCaseImpl
    ): GetPhrasesUseCase

}