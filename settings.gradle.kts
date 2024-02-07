pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RelicCalculator"
include(":app")
include(":core:network")
include(":core:model")
include(":rating")
include(":core:database")
include(":core:ui")
include(":core:storage")
include(":core:common")
