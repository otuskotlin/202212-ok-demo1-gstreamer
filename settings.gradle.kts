
rootProject.name = "otus-demo1-202212-gstreamer"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
    }

}

include("player")
