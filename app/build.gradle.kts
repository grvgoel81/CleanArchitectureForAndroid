plugins {
    id("com.android.application")
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.hilt)
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.mitteloupe.whoami"
    compileSdk = 33

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.mitteloupe.whoami"
        minSdk = 22
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.mitteloupe.whoami.di.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

ktlint {
    version.set("0.50.0")
    android.set(true)
}

detekt {
    config.setFrom("$projectDir/../detekt.yml")
}

dependencies {
    implementation(project(":time"))
    implementation(project(":coroutine"))
    implementation(project(":datasource-architecture"))
    implementation(project(":datasource-source"))
    implementation(project(":datasource-implementation"))

    implementation(project(":architecture-ui"))
    implementation(project(":architecture-presentation"))
    implementation(project(":architecture-domain"))

    implementation(project(":home-ui"))
    implementation(project(":home-presentation"))
    implementation(project(":home-domain"))
    implementation(project(":home-data"))

    implementation(project(":history-ui"))
    implementation(project(":history-presentation"))
    implementation(project(":history-domain"))
    implementation(project(":history-data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.interceptor.logging)
    implementation(libs.moshi.kotlin)

    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    kapt(libs.hilt.android.compiler)

    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(libs.test.android.hilt)
    androidTestImplementation(libs.test.android.uiautomator)
    androidTestImplementation(project(":architecture-instrumentation-test"))
    androidTestImplementation(libs.test.android.mockwebserver)
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.test.mockito) {
        exclude("net.bytebuddy")
    }
    androidTestImplementation(libs.test.mockito.kotlin)
    androidTestImplementation(libs.test.mockito.android)

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

val installGitHook = tasks.register<Copy>("installGitHook") {
    from(File(rootProject.rootDir, "automation/git/pre-commit"))
    into(File(rootProject.rootDir, ".git/hooks"))
    fileMode = 0b111111101
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)
