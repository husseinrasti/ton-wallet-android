package org.ton.wallet.feature.create.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import org.ton.wallet.core.dagger.hilt.scope.DefaultDispatcher
import org.ton.wallet.core.ton.sdk.mnemonic.Mnemonic
import org.ton.wallet.feature.create.domain.repository.CreateWalletRepository
import javax.inject.Inject

class CreateWalletRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : CreateWalletRepository {

    override suspend fun getMnemonic(): Result<List<String>> {
        return try {
            val mnemonic = Mnemonic.generate(dispatcher = defaultDispatcher)
            Result.success(mnemonic)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}