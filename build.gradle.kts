import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.danilopianini.gradle-java-qa") version "1.17.0"
    id("nu.studer.jooq") version "8.2.1"
    id("io.freefair.lombok") version "8.3"
}

repositories {
    mavenCentral()
}

val javaFXModules = listOf("base", "controls", "fxml", "swing", "graphics")
val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    val spotbugsAnnotations = "com.github.spotbugs:spotbugs-annotations:4.7.3"
    compileOnly(spotbugsAnnotations)
    testCompileOnly(spotbugsAnnotations)

    val junitVersion = "5.9.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    val mysqlConnectorVersion = "8.0.33"
    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
    jooqGenerator("mysql:mysql-connector-java:$mysqlConnectorVersion")

    val dotenvVersion = "3.0.0"
    implementation("io.github.cdimascio:dotenv-java:$dotenvVersion")

    val reflectionsVersion = "0.10.2"
    implementation("org.reflections:reflections:$reflectionsVersion")

    val slf4jVersion = "2.0.9"
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    val apacheLang3Version = "3.13.0"
    implementation("org.apache.commons:commons-lang3:$apacheLang3Version")

    val commonsCollectionVersion = "4.4"
    implementation("org.apache.commons:commons-collections4:$commonsCollectionVersion")

    val commonsCodecVersion = "1.16.0"
    implementation("commons-codec:commons-codec:$commonsCodecVersion")

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

jooq {
    version.set("3.18.6")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    loadEnvFromFile(".env")
                    url = System.getProperty("DB_URL")
                    user = System.getProperty("DB_USERNAME")
                    password = System.getProperty("DB_PASSWORD")
                    properties = listOf(
                            Property().apply {
                                key = "PAGE_SIZE"
                                value = "2048"
                            }
                    )
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = "amusement_park"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = false
                        isImmutablePojos = false
                        isFluentSetters = false
                    }
                    target.apply {
                        packageName = "org.apdb4j.db"
                        directory = "src/generated/jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

fun loadEnvFromFile(envFilePath: String) {
    val envFile = File(envFilePath)
    if (envFile.exists()) {
        val lines = envFile.readLines()
        lines.forEach { line ->
            val parts = line.split('=')
            if (parts.size == 2) {
                val key = parts[0].trim()
                val value = parts[1].trim()
                System.setProperty(key, value)
            }
        }
    } else {
        println("Warning: .env file not found at $envFilePath")
    }
}

tasks.withType<Javadoc> {
    source = sourceSets.main.get().allJava
    exclude("**/org/apdb4j/db/**")
}

spotbugs {
    excludeFilter.set(file("config/spotbugs-excludes.xml"))
}

pmd {
    ruleSetFiles = files("config/pmd_config.xml")
}

javaQA {
    checkstyle {
        additionalSuppressions.set("config/checkstyle_suppressions.xml")
    }
}

application {
    mainClass.set("org.apdb4j.core.Main")
}
