plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
    maven("https://repo.thenextlvl.net/releases")
}

dependencies {
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")

    implementation(project(":api"))

    compileOnly("org.projectlombok:lombok:1.18.26")
    compileOnly("net.thenextlvl.holograms:api:1.0.0")
    compileOnly("net.thenextlvl.core:annotations:1.0.0")

    implementation("net.thenextlvl.core:api:3.1.12")
    implementation("net.thenextlvl.core:utils:1.0.0")

    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    task("reobf") {
        dependsOn(reobfJar, shadowJar)
    }
}