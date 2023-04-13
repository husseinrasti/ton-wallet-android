/*
 * Copyright (C) 2022  The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.husseinrasti.convention.base


object BuildModules {
    const val APP = ":app"

    object Features {
        object CreateWallet {
            const val UI = ":feature:create-wallet:ui"
            const val DATA = ":feature:create-wallet:data"
            const val DOMAIN = ":feature:create-wallet:domain"
        }
    }

    object Core {
        const val UI = ":core:ui"
        const val NAVIGATION = ":core:navigation"
        const val MODEL = ":core:model"
        const val NETWORK = ":core:network"
    }

}