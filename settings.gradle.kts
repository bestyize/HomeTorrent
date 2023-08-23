pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}
rootProject.name = "HomeTorrent"
include(":app")
include(":torrent")
include(":framework:baseapp")
include(":framework:downloader")
include(":framework:network")
include(":framework:account")
include(":framework:widget")
include(":framework:resources")
