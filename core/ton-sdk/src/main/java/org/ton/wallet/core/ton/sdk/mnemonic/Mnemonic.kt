package org.ton.wallet.core.ton.sdk.mnemonic

import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

private val MnemonicGeneratorCoroutineName = CoroutineName("mnemonic-generator")

object Mnemonic {

    private const val DEFAULT_WORD_COUNT: Int = 24

    private fun mnemonicWords(): List<String> = MNEMONIC_WORD_LIST

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun generate(
        wordCount: Int = DEFAULT_WORD_COUNT,
        wordlist: List<String> = mnemonicWords(),
        random: Random = SecureRandom
    ): List<String> = suspendCancellableCoroutine { continuation ->
        GlobalScope.launch(
            Dispatchers.Default + MnemonicGeneratorCoroutineName
        ) {
            try {
                val mnemonic = Array(wordCount) { "" }
                val weakRandom = Random(random.nextLong())
                while (continuation.isActive) {
                    repeat(wordCount) { i ->
                        mnemonic[i] = wordlist.random(weakRandom)
                    }
                    continuation.resume(mnemonic.toList())
                    break
                }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }
    }

}