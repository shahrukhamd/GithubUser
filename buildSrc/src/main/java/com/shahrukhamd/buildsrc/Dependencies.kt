object Version {
    // base
    const val kotlinVersion = "1.5.21"
    const val androidGradleVersion = "4.0.1"

    // app
    const val lifecycleVersion = "2.3.1"
    const val ktxVersion = "1.6.0"
    const val activity = "1.3.1"
    const val appCompatVersion = "1.3.1"
    const val fragmentVersion = "1.3.6"

    // database
    const val roomVersion = "2.3.0"

    // ui
    const val recyclerViewVersion = "1.2.1"
    const val materialVersion = "1.4.0"
    const val constraintLayout = "2.1.0"
    const val glideVersion = "4.12.0"
    const val swipeRefreshVersion = "1.1.0"

    // networking
    const val retrofitVersion = "2.9.0"
    const val okhttpVersion = "4.9.1"
    const val moshiConverterVersion = "2.9.0"
    const val moshiKotlinVersion = "1.12.0"

    // di
    const val hiltVersion = "2.38.1"

    // coroutine
    const val coroutineCoreVersion = "1.4.3"
    const val coroutineAndroidVersion = "1.4.3"

    // tools
    const val timberVersion = "4.7.1"

    // pagination
    const val pagingVersion = "3.0.1"

    // navigation
    const val navigationVersion = "2.3.5"

    // testing
    const val mockkVersion = "1.12.0"
    const val truthVersion = "1.1.3"
    const val archTestVersion = "2.1.0"
    const val coroutineTestVersion = "1.4.3"
    const val androidRunnerVersion = "1.0.1"
}

object Base {
    const val minSdkVersion = 23
    const val targetSdkVersion = 28
    const val compileSdkVersion = 30
    const val applicationId = "com.shahrukhamd.githubuser"
    const val versionCode = 1
    const val versionName = "0.1"

    const val androidGradle = "com.android.tools.build:gradle:${Version.androidGradleVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Version.hiltVersion}"
    const val navigationSafeArgPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigationVersion}"

    const val kotlinKtx = "androidx.core:core-ktx:${Version.ktxVersion}"
    const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlinVersion}"

    const val appCompat = "androidx.appcompat:appcompat:${Version.appCompatVersion}"
    const val activity = "androidx.activity:activity-ktx:${Version.activity}"
    const val fragment = "androidx.fragment:fragment-ktx:${Version.fragmentVersion}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleVersion}"
}

object Database {
    const val roomRuntime = "androidx.room:room-runtime:${Version.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Version.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Version.roomVersion}"
}

object Ui {
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerViewVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Version.glideVersion}"
    const val material = "com.google.android.material:material:${Version.materialVersion}"
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefreshVersion}"
}

object Networking {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Version.moshiConverterVersion}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshiKotlinVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttpVersion}"
    const val okhttpIntercepter = "com.squareup.okhttp3:logging-interceptor:${Version.okhttpVersion}"
}

object Di {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.hiltVersion}"
}

object Coroutine {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutineCoreVersion}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutineAndroidVersion}"
}

object Tools {
    const val timber = "com.jakewharton.timber:timber:${Version.timberVersion}"
}

object Pagination {
    const val runtimeKtx = "androidx.paging:paging-runtime-ktx:${Version.pagingVersion}"
}

object Navigation {
    const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Version.navigationVersion}"
    const val uiKtx = "androidx.navigation:navigation-ui-ktx:${Version.navigationVersion}"
}

object Testing {
    const val runner = "com.android.support.test:runner:${Version.androidRunnerVersion}"
    const val mockK = "io.mockk:mockk:${Version.mockkVersion}"
    const val truth = "com.google.truth:truth:${Version.truthVersion}"
    const val archCore = "androidx.arch.core:core-testing:${Version.archTestVersion}"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutineTestVersion}"
    const val kotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlinVersion}"
}