import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("feature-mobile")
}

android {
    namespace = "com.danbam.mobile"
    defaultConfig {
        applicationId = "com.danbam.indi_straw.mobile"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
        buildConfigField(
            "String",
            "QR_URL",
            gradleLocalProperties(rootDir).getProperty("QR_URL")
        )
        buildConfigField(
            "String",
            "PAY_KEY",
            gradleLocalProperties(rootDir).getProperty("PAY_KEY")
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packagingOptions.resources.excludes += setOf(
        "META-INF/DEPENDENCIES",
        "META-INF/LICENSE",
        "META-INF/LICENSE.txt",
        "META-INF/license.txt",
        "META-INF/NOTICE",
        "META-INF/NOTICE.txt",
        "META-INF/INDEX.LIST",
        "META-INF/notice.txt",
        "META-INF/ASL2.0",
        "META-INF/gradle/incremental.annotation.processors"
    )
}
dependencies {
    implementation(project(":di"))
    implementation(libs.bundles.retrofit)
    implementation(libs.bootpay)
}
