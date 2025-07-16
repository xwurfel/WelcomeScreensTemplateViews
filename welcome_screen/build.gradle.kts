import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    id("maven-publish")
    id("signing")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { stream ->
        localProperties.load(stream)
    }
}

fun getProperty(name: String): String? {
    return localProperties.getProperty(name)
        ?: project.findProperty(name) as String?
        ?: System.getenv(name.uppercase().replace(".", "_"))
}

android {
    namespace = "com.volpis.welcome_screen"
    compileSdk = 35
    version = "1.0.1"

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "LIBRARY_VERSION", "\"${project.version}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += listOf(
            "-Xjvm-default=all",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

    implementation(libs.glide)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.core)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.volpis"
            artifactId = "welcome-screen"
            version = project.version.toString()

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Welcome Screen Library")
                description.set("A highly customizable welcome screen library for Android applications")

                developers {
                    developer {
                        id.set("volpis-android")
                        name.set("Volpis Android Team")
                        email.set("mykhailo.i@volpis.com")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "Repsy"
            url = uri(getProperty("repsy.url") ?: "")
            credentials {
                username = getProperty("repsy.username")
                password = getProperty("repsy.password")
            }
        }
    }
}