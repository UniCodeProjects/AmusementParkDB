plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.danilopianini.gradle-java-qa") version "1.6.0"
}

repositories {
    mavenCentral()
}

val javaFXModules = listOf("base", "controls", "fxml", "swing", "graphics")
val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.7.3")

    val junitVersion = "5.9.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    val javaFxVersion = "15"

    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
        }
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        showStandardStreams = true
    }
}

application {
    mainClass.set("org.apdb.Main")
}