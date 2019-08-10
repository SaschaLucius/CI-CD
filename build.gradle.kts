plugins {
    java

    // Apply the application plugin to add support for building a CLI application
    application

    jacoco

    id("org.sonarqube") version "2.7"

    eclipse
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:27.1-jre")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

application {
    // Define the main class for the application
    mainClassName = "saschawiegleb.App"
}

tasks {
    "test"(Test::class) {
        // Use junit platform for unit tests
        useJUnitPlatform()
    }

    val codeCoverageReport by creating(JacocoReport::class) {
        executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

        // Add all relevant sourcesets from the subprojects
        subprojects.onEach {
            sourceSets(it.sourceSets["main"])
        }

        reports {
            sourceDirectories.setFrom(files(sourceSets["main"].allSource.srcDirs))
            classDirectories.setFrom(files(sourceSets["main"].output))
            xml.isEnabled = true
            xml.destination = File("$buildDir/reports/jacoco/report.xml")
            html.isEnabled = false
            csv.isEnabled = false
        }

        dependsOn("test")
    }
}

sonarqube {
    properties {
        property("sonar.projectName", "CI-CD")
        property("sonar.projectKey", "SaschaWiegleb_CI-CD")
    }
}
