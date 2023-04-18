import com.husseinrasti.convention.base.BuildModules

plugins {
    id("build.logic.android.library.feature")
}

android {
    namespace = "org.ton.wallet.feature.create.data"
}

dependencies {
    implementation(project(BuildModules.Features.CreateWallet.DOMAIN))
    implementation(project(BuildModules.Core.TON_SKD))
}