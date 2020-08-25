plugins {
    kotlin("jvm")
    kotlin("kapt")
    application
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("com.github.ben-manes.versions") version "0.29.0"
}

version = "0.1.0"
group = "tech.guiyom"

repositories {
    mavenCentral()
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    val kotlinVersion: String by project
    implementation(platform(kotlin("bom", kotlinVersion)))
    implementation(kotlin("stdlib-jdk8"))
    //implementation(kotlin("reflect"))

    implementation("com.badlogicgames.ashley:ashley:1.7.4-SNAPSHOT")

    implementation("org.slf4j:slf4j-api:1.7.30")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")
}

application {
    mainClassName = "tech.guiyom.collinsa.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

kapt {

}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
            javaParameters = true
            freeCompilerArgs = listOf("-Xemit-jvm-type-annotations")
        }
    }

    withType(JavaCompile::class).configureEach {
        options.encoding = "UTF-8"
    }

    shadowJar {
        mergeServiceFiles()
    }

    dependencyUpdates {
        resolutionStrategy {
            componentSelection {
                all {
                    if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                        reject("Release candidate")
                    }
                }
            }
        }
        checkConstraints = true
        gradleReleaseChannel = "current"
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
