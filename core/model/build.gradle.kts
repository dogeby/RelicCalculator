plugins {
    id("kotlin")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.java.annotations)
}
