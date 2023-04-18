package org.ton.wallet.feature.create.data.repository

import org.ton.wallet.core.ton.sdk.mnemonic.Mnemonic
import org.ton.wallet.feature.create.domain.repository.CreateWalletRepository
import javax.inject.Inject

class CreateWalletRepositoryImpl @Inject constructor() : CreateWalletRepository {

    override suspend fun getMnemonic(): Result<List<String>> {
        return try {
            val mnemonic = Mnemonic.generate()
            Result.success(mnemonic)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}