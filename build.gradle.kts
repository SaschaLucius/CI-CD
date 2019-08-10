plugins {
    java
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
    implementation("com.google.guava:guava:27.1-jre")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }

    val codeCoverageReport by creating(JacocoReport::class) {
        executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

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
