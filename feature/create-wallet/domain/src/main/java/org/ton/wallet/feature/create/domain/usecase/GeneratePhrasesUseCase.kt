package org.ton.wallet.feature.create.domain.usecase

import org.ton.wallet.feature.create.domain.repository.CreateWalletRepository
import javax.inject.Inject


interface GeneratePhrasesUseCase {
    suspend operator fun invoke(): Result<Boolean>
}


class GeneratePhrasesUseCaseImpl @Inject constructor(
    private val repository: CreateWalletRepository
) : GeneratePhrasesUseCase {
    override suspend fun invoke(): Result<Boolean> {
        return repository.isExistPhrases().fold(
            onSuccess = {
                Result.success(true)
            },
            onFailure = {
                repository.generatePhrases()
            }
        )
    }
}