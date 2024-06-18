plugins {
//    alias(libs.plugins.android.application)
    id("com.android.application")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")


}

android {
    namespace = "com.example.firebasechat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.firebasechat"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    tasks.withType(JavaCompile).configureEach{
//        options.fork = true
//        options.forkOptions.jvmArgs +=[
//            '--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED',
//            '--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED',
//            '--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED']
//    }

    buildFeatures {
        //Enable by adding below line for a module that needs it
        buildConfig = true
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

//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.support.annotations)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
// For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("com.google.firebase:firebase-auth:9.6.1")
    implementation("com.google.firebase:firebase-database:9.6.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.jakewharton:butterknife:10.2.3")
    annotationProcessor("com.jakewharton:butterknife-compiler:10.2.3")



}

