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
    }
}

rootProject.name = "DFMDemo1"
include(":app")
include(":feature:intro:kr")
include(":feature:home:kr")
include(":feature:home:jp")
include(":feature:home:cn")
include(":feature:intro:common")
include(":core:navigation")
include(":feature:home:common")
include(":resource")
include(":feature:setting:common")
include(":core:util")
