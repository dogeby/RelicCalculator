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
include(":core:database")
include(":core:ui")
include(":core:storage")
include(":core:common")
include(":core:data")
include(":core:datastore")
include(":game:resource")
include(":game:rating")
include(":core:domain")
include(":feature:presets")
