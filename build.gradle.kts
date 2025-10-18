@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinMultiplatform)

}

/*  Which additional multiplatform (ORX) libraries should be added to this project. */
val orxFeatures = setOf<String>(
    "orx-camera",
    "orx-color",
//    "orx-compositor",
    "orx-composition",
    "orx-easing",
//    "orx-fx",
//    "orx-gradient-descent",
    "orx-image-fit",
    "orx-math",
    "orx-mesh-generators",
    "orx-noise",
//    "orx-parameters",
    "orx-shade-styles",
        "orx-shader-phrases",
    "orx-shapes",
    "orx-svg",
//    "orx-quadtree",
)

fun orx(module: String) = "org.openrndr.extra:$module:${libs.versions.orx.get()}"

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    wasmJs {
        outputModuleName.set("wasmjsExamples")
        browser {
            commonWebpackConfig {
                sourceMaps = true
                outputFileName = "wasmjsExamples.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        wasmJsMain.dependencies {

            implementation(libs.openrndr.application)
            implementation(libs.openrndr.dds)
            implementation(libs.openrndr.draw)
            implementation(libs.openrndr.webgl)
            implementation(libs.kotlin.js)
            implementation(libs.kotlin.browser)
            implementation(libs.kotlin.web)
            implementation(libs.kotlinx.serialization.json)


            for (feature in orxFeatures) {
                implementation(orx(feature))
            }
        }
    }
}