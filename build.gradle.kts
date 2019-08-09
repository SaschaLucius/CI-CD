/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.5.1/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    application

    jacoco

    id("org.sonarqube") version "2.7"
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

task codeCoverageReport(type: JacocoReport) {
    executionData fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec")

    subprojects.each {
        sourceSets it.sourceSets.main
    }

    reports {
        xml.enabled true
        xml.destination "${buildDir}/reports/jacoco/report.xml"
        html.enabled false
        csv.enabled false
    }
}

application {
    // Define the main class for the application
    mainClassName = "saschawiegleb.App"
}

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
}
