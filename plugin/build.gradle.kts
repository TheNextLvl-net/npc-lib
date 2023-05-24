plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "net.thenextlvl.npc"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
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
}