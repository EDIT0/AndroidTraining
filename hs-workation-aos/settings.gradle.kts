pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven("https://jitpack.io")
        maven("https://repository.map.naver.com/archive/maven")
    }
}

rootProject.name = "hsworkation"
include(":app")
include(":feature:splash")
include(":feature:permission")
include(":core:base")
include(":core:util")
include(":core:di")
include(":domain")
include(":data")
include(":core:model")
include(":core:common")
include(":core:component")
include(":feature:login")
include(":feature:home")
