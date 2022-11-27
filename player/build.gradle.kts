plugins {
    kotlin("multiplatform")
}

kotlin {
    linuxX64 {
        binaries {
            executable {
                this.entryPoint("ru.otus.otuskotlin.player.main")
                baseName = "otus-player"
            }
        }
        compilations.getByName("main") {
            @Suppress("UNUSED_VARIABLE")
            val gstreamerInterop by cinterops.creating {
                // Def-file describing the native API.
                defFile(project.file("src/nativeInterop/cinterop/gstreamer.def"))
                // Package to place the Kotlin API generated.
                packageName("${project.group}")
            }
        }
    }

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val linuxX64Main by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
    }
}
