plugins {
    id("build-logic.kotlin-convention")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    application
}

application {
    // Define the main class for the application.
    mainClassName = "kstuencis.AppKt"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":common"))
    implementation(project(":console"))
    implementation(project(":socket"))
    implementation(project(":counter"))
}
