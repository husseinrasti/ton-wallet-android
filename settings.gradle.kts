pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "TonWalletAndroid"

include(":app")

include(":core:ui")
include(":core:navigation")
include(":core:ton-sdk")
include(":core:dagger-hilt")

include(":feature:create-wallet:ui")
include(":feature:create-wallet:data")
include(":feature:create-wallet:domain")


fun renameBuildFileName(name: String, project: ProjectDescriptor) {
    if (project.children.isEmpty()) {
        println("$name.gradle.kts")
        project.buildFileName = "$name.gradle.kts"
    } else {
        project.children.forEach { subProject ->
            renameBuildFileName("$name-${subProject.name}", subProject)
        }
    }
}

rootProject.children.forEach { project ->
    renameBuildFileName(project.name, project)
}
