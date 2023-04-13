package org.ton.wallet.core.navigation

sealed interface NavigationOnClickEvent

object NavigateCreateWallet : NavigationOnClickEvent

object NavigateImportWallet : NavigationOnClickEvent