package org.ton.wallet.feature.create.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.ton.wallet.core.dagger.hilt.scope.DefaultDispatcher
import org.ton.wallet.feature.create.domain.repository.CreateWalletRepository
import javax.inject.Inject

interface FilterPhrasesUseCase {
    suspend operator fun invoke(params: Params): Result<List<String>>

    @JvmInline
    value class Params(val word: String)
}

class FilterPhrasesUseCaseImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val repository: CreateWalletRepository,
) : FilterPhrasesUseCase {
    override suspend fun invoke(params: FilterPhrasesUseCase.Params): Result<List<String>> {
        return withContext(defaultDispatcher) {
            try {
                Result.success(
                    repository.getMnemonicList().filter {
                        it.contains(params.word)
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
}