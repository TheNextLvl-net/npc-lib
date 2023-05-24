plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.3"
}

group = "net.thenextlvl.npc"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.thenextlvl.net/releases")
}

dependencies {
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.26")
    compileOnly("net.thenextlvl.core:annotations:1.0.0")

    implementation(project(":api"))

    annotationProcessor("org.projectlombok:lombok:1.18.26")
}


tasks {
    task("reobf") {
        dependsOn(reobfJar)
    }
}