import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.example.mylibrary"
    compileSdk = 36

    defaultConfig {
        minSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

val versionProps = file("version.properties").reader().use {
    Properties().apply { load(it) }
}
val versionName: String = versionProps.getProperty("versionName")

group = "com.example"
version = versionName


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"]) // Waits until 'release' component exists

                groupId = group as String
                artifactId = "mylibrary"
                version = versionName
            }
        }

        repositories {
            maven {
                name = "jfrog"
                url = uri("${findProperty("artifactory_contextUrl")}/${findProperty("artifactoryRepo")}")
                credentials {
                    username = findProperty("artifactory_user") as String?
                    password = findProperty("artifactory_password") as String?
                }
            }
        }
    }
}


/*afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = group as String
                artifactId = "mylibrary"
                version = versionName
            }
        }
        repositories {
            maven {
                mavenLocal() // Local Maven
            }
        }
    }
}*/


