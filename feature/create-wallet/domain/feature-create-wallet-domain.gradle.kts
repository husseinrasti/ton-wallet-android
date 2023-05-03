import com.husseinrasti.convention.base.BuildModules

plugins {
    id("build.logic.android.library.feature")
}

android {
    namespace = "org.ton.wallet.feature.create.domain"
}

dependencies {
    implementation(project(BuildModules.Core.DAGGER_HILT))
}