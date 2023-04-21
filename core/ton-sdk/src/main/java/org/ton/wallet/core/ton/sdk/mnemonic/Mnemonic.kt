package org.ton.wallet.core.ton.sdk.mnemonic

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

private val MnemonicGeneratorCoroutineName = CoroutineName("mnemonic-generator")

object Mnemonic {

    private const val DEFAULT_WORD_COUNT: Int = 24

    private fun mnemonicWords(): List<String> = MNEMONIC_WORD_LIST

    suspend fun generate(
        dispatcher: CoroutineContext,
        wordCount: Int = DEFAULT_WORD_COUNT,
        wordlist: List<String> = mnemonicWords(),
        random: Random = SecureRandom
    ): List<String> = withContext(dispatcher + MnemonicGeneratorCoroutineName) {
        val mnemonic = Array(wordCount) { "" }
        val weakRandom = Random(random.nextLong())
        repeat(wordCount) { i ->
            val word = wordlist.random(weakRandom)
            Log.i("TAG", "generate word: $word")
            mnemonic[i] = word
        }
        Log.i("TAG", "generate list: $mnemonic")
        mnemonic.toList()
    }

}