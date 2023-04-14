import com.husseinrasti.convention.base.BuildModules

plugins {
    id("build.logic.android.application")
}

dependencies {
    implementation(project(BuildModules.Core.UI))
    implementation(project(BuildModules.Core.NAVIGATION))
    implementation(project(BuildModules.Features.CreateWallet.UI))
}