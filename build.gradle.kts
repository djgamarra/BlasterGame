plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.djgamarra.blaster"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("run") {
    group = "application"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.djgamarra.blaster.MainKt")
}

kotlin {
    jvmToolchain(21)
}
