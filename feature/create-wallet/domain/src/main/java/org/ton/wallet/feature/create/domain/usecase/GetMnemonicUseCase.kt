package org.ton.wallet.feature.create.domain.usecase

import org.ton.wallet.feature.create.domain.repository.CreateWalletRepository
import javax.inject.Inject

interface GetMnemonicUseCase {
    suspend operator fun invoke(): Result<List<String>>
}

class GetMnemonicUseCaseImpl @Inject constructor(
    private val repository: CreateWalletRepository
) : GetMnemonicUseCase {
    override suspend fun invoke(): Result<List<String>> {
        return repository.getMnemonic()
    }
}