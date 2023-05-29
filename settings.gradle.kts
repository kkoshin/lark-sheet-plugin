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
        maven("https://jitpack.io")
    }
    versionCatalogs {
//        libraryLibs {
//            from(files("./gradle/libraryLibs.versions.toml"))
//        }
    }
}
rootProject.name = "lark-sheet-plugin"