plugins {
    id("build-logic.kotlin-convention")
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

dependencies {
    implementation("kstuencis:core")
    implementation("kstuencis.interface:console")
    implementation("kstuencis.interface:socket")
    implementation("kstuencis.business:counter")
}