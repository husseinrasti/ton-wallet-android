plugins {
    id("build.logic.android.library")
    id("build.logic.android.library.compose")
}

android {
    namespace = "org.ton.wallet.core.ui"
}

dependencies {
    implementation(libs.lottie.compose)
}