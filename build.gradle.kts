import org.jetbrains.kotlin.gradle.dsl.JsModuleKind
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform") version "2.1.20"
}

group = "com.zenmo"
version = "dev"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    compilerOptions {
        // this is the equivalent of setting "kotlin.js.ir.output.granularity=per-file" in gradle.properites
        compilerOptions.freeCompilerArgs.add("-Xir-per-file")
        compilerOptions.freeCompilerArgs.add("-Xir-minimized-member-names=false")
    }
    js(IR) {
        useEsModules()
        browser {
//            commonWebpackConfig {
//            }
//            webpackTask {
//                output.libraryTarget = "module"
//                esModules = true
//            }
        }
        binaries.executable()
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions.target = "es2015"
                compilerOptions.moduleKind = JsModuleKind.MODULE_ES
            }
        }
    }
    sourceSets {
        jsMain {
            languageSettings.optIn("kotlin.js.ExperimentalJsExport")
            dependencies {
                // adds import()
                implementation("org.jetbrains.kotlin-wrappers:kotlin-js:2025.4.11")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
            }
        }
    }
}
