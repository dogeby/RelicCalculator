import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jlleitschuh.ktlint)
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.jlleitschuh.ktlint.get().pluginId)
    }
    ktlint {
        reporters {
            reporter(ReporterType.CHECKSTYLE)
        }
    }
}
