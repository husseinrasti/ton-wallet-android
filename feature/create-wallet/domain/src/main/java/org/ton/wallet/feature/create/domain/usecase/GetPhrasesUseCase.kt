package org.ton.wallet.feature.create.domain.usecase

import org.ton.wallet.feature.create.domain.repository.CreateWalletRepository
import javax.inject.Inject

interface GetPhrasesUseCase {
    suspend operator fun invoke(): Result<List<String>>
}


class GetPhrasesUseCaseImpl @Inject constructor(
    private val repository: CreateWalletRepository
) : GetPhrasesUseCase {
    override suspend fun invoke(): Result<List<String>> {
        return repository.getPhrases()
    }
}