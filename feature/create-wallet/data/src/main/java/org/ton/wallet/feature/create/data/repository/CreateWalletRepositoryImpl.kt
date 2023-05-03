package org.ton.wallet.feature.create.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.ton.wallet.core.dagger.hilt.scope.DefaultDispatcher
import org.ton.wallet.core.dagger.hilt.scope.IoDispatcher
import org.ton.wallet.core.datastore.TonPreferenceDataSource
import org.ton.wallet.core.ton.sdk.mnemonic.Mnemonic
import org.ton.wallet.core.ton.sdk.mnemonic.getMnemonicWorldList
import org.ton.wallet.feature.create.domain.repository.CreateWalletRepository
import javax.inject.Inject

class CreateWalletRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: TonPreferenceDataSource,
) : CreateWalletRepository {
    override suspend fun generatePhrases(): Result<Boolean> {
        return withContext(ioDispatcher) {
            try {
                val phrases = Mnemonic.generate(dispatcher = defaultDispatcher)
                dataSource.setPhrases(phrases)
                Result.success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }

    override suspend fun getPhrases(): Result<List<String>> =
        dataSource.phrases()

    override suspend fun isExistPhrases(): Result<Boolean> {
        return withContext(ioDispatcher) {
            getPhrases().fold(
                onSuccess = {
                    Result.success(it.isNotEmpty())
                },
                onFailure = {
                    Result.failure(NullPointerException())
                }
            )
        }
    }

    override suspend fun getMnemonicList(): List<String> = getMnemonicWorldList()

}