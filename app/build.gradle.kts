import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.gradle.internal.impldep.com.amazonaws.PredefinedClientConfigurations.defaultConfig
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        applicationId = "com.twiceyuan.gradlekotlindsl"
        minSdkVersion(16)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("customBuildType") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("default")

    productFlavors {
        create("customFlavor") {
            setDimension("default")
        }
    }

    applicationVariants.filter { it.buildType.name == "release" }.forEach { applicationVariant ->
        applicationVariant.outputs.map { it as BaseVariantOutputImpl }.forEach { output ->
            output.outputFileName = "renamed_release_package.apk"
        }
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Config.kotlin_version}")
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    // 调用根据 flavor 或者 buildType 相关的 implementation 方法
    // 方式 1
    "devImplementation"("com.android.support:appcompat-v7:28.0.0")
    // 方式 2
    val devImplementation by configurations
    devImplementation("com.android.support:appcompat-v7:28.0.0")
}
