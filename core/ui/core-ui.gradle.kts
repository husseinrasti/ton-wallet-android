import com.husseinrasti.convention.base.BuildModules

plugins {
    id("build.logic.android.library")
    id("build.logic.android.library.compose")
}

android {
    namespace = "org.ton.wallet.core.ui"
}

dependencies {
    implementation(project(BuildModules.Core.NAVIGATION))

    implementation(libs.lottie.compose)
}