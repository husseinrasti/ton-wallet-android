package org.ton.wallet.core.dagger.hilt.scope

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationDefaultScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationIoScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineDefaultScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineIoScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineMainScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineMainImmediateScope