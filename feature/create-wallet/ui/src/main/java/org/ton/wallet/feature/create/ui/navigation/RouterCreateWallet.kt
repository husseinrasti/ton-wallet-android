package org.ton.wallet.feature.create.ui.navigation

import org.ton.wallet.core.navigation.NavigationEvent

sealed interface RouterCreateWallet : NavigationEvent {
    object Start : RouterCreateWallet
    object Congratulations : RouterCreateWallet
}
