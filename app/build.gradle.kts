plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.apollographql.apollo3") version "3.8.2"
    id("kotlin-parcelize")
}
//    ./gradlew :app:downloadApolloSchema --endpoint='https://beta.pokeapi.co/graphql/v1beta' --schema=app/src/main/graphql/schema.graphqls
apollo {
    service("service") {
        packageName.set("com.example")
    }
}

android {
    namespace = "com.example.pokemonapplication"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.pokemonapplication"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
}
