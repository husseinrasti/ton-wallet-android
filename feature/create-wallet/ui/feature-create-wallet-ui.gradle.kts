import com.husseinrasti.convention.base.BuildModules

plugins {
    id("build.logic.android.library.feature")
    id("build.logic.android.library.compose")
}

android {
    namespace = "org.ton.wallet.feature.create.ui"
}

dependencies {
    implementation(project(BuildModules.Features.CreateWallet.DOMAIN))
}