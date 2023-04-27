import com.husseinrasti.convention.base.BuildModules

plugins {
    id("build.logic.android.library")
}

android {
    namespace = "org.ton.wallet.core.datastore"
}

dependencies {
    implementation(project(BuildModules.Core.DAGGER_HILT))

    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.google.gson)
}