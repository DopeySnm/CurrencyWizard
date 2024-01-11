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
        // AndroidCharts
        jcenter()
        // MPAndroidChart
        maven( "https://jitpack.io")
    }
}


rootProject.name = "CurrencyWizard"
include(":app")
 