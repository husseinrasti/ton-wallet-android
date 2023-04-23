package org.ton.wallet.core.navigation

interface NavigationEvent {
    object Idle : NavigationEvent
}

object NavigateUp : NavigationEvent

object NavigateCreateWallet : NavigationEvent

object NavigateImportWallet : NavigationEvent