plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jlleitschuh.ktlint)
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.jlleitschuh.ktlint.get().pluginId)
    }
}

ktlint {
    android = true
    ignoreFailures = false
}
