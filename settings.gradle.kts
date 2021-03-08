rootProject.name = "kstuencis"

includeBuild("build-logic")

requireNotNull(rootDir.resolve("modules").listFiles()) { "Unable to list files under $rootDir/modules directory" }
    .asSequence()
    .filter(File::isDirectory)
    .forEach { dir ->
        include("${dir.name}")
        val moduleProject = project(":${dir.name}")
        moduleProject.projectDir = dir
        moduleProject.buildFileName = "build.gradle.kts"
    }
