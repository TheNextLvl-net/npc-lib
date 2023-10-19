plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = rootProject.group
version = "1.2.4"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")

    implementation(project(":api"))
    implementation(project(":v1_19_4", "reobf"))
    implementation(project(":v1_20_1", "reobf"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.shadowJar {
    minimize()
}

bukkit {
    name = "NPC-Lib"
    main = "net.thenextlvl.npc.FakePlayerAPI"
    apiVersion = "1.19"
    website = "https://thenextlvl.net"
    authors = listOf("NonSwag")
    depend = listOf("HologramAPI")
}