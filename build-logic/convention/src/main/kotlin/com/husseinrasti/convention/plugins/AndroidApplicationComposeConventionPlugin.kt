package com.husseinrasti.convention.plugins

import com.husseinrasti.convention.ext.findLibrary
import com.husseinrasti.convention.ext.kotlinOptions
import com.husseinrasti.convention.ext.library.android
import com.husseinrasti.convention.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("build.logic.android.library")
            composeLibrary()
            dependencies {
                add("implementation", platform(findLibrary("androidx-compose-bom")))
                add("androidTestImplementation", platform(findLibrary("androidx-compose-bom")))
            }
        }
    }
}

fun Project.composeLibrary() {
    android {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs().findVersion("androidxComposeCompiler").get().toString()
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs
        }

    }
}
