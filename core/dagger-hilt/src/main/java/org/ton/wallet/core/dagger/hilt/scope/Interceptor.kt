package org.ton.wallet.core.dagger.hilt.scope

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChangeBodyInterceptorOkHttpClient
