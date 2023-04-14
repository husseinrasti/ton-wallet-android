package org.ton.wallet.feature.create.domain.repository

interface CreateWalletRepository {

    suspend fun getMnemonic(): Result<List<String>>

}