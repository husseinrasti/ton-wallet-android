import com.husseinrasti.convention.base.BuildModules

plugins {
    id("build.logic.android.library")
}

android {
    namespace = "org.ton.wallet.core.ton.sdk"
}

dependencies {
    implementation(project(BuildModules.Core.CRYPTO))
}