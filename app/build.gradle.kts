plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.pineapple"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.pineapple"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            proguardFiles (getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding  = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("de.hdodenhof:circleimageview:3.1.0") //这条依赖是显示圆形的图片
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0") //这条依赖是下拉刷新
    implementation ("com.github.bumptech.glide:glide:4.11.0")//可以调用glide上传图片
    implementation ("io.github.bmob:android-sdk:3.9.4")//从这里
    implementation ("io.reactivex.rxjava2:rxjava:2.2.8")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("com.squareup.okhttp3:okhttp:4.8.1")
    implementation ("com.squareup.okio:okio:2.2.2")
    //到这里都是bmob的依赖
    implementation ("com.google.code.gson:gson:2.9.0")

    //百度地图的依赖
    implementation(files("libs\\BaiduLBS_Android.jar"))
    implementation ("androidx.navigation:navigation-fragment:2.3.5")
    implementation ("androidx.navigation:navigation-ui:2.3.5")

    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation ("com.google.android.material:material:1.5.0")
    testImplementation ("org.mockito:mockito-core:3.11.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}