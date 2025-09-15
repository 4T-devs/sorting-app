plugins {
    id("java")
    id("application")
    id("checkstyle")
    id("pmd")
    id("jacoco")
    id("com.diffplug.spotless") version "7.2.1"
}

group = "com.ftdevs.sortingapp"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    sourceSets {
        val main by getting {
            java.srcDirs("src/main/java")
        }
        val test by getting {
            java.srcDirs("src/test/java")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.40")
    annotationProcessor("org.projectlombok:lombok:1.18.40")
    testCompileOnly("org.projectlombok:lombok:1.18.40")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.40")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass = "com.ftdevs.sortingapp.SorterMain"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

// quality gate
tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(tasks.test)
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.70".toBigDecimal()
            }
        }
    }
}

// checkstyle
configure<CheckstyleExtension> {
    toolVersion = "10.12.4"
    configFile = file("config/checkstyle/checkstyle.xml")
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
    }
}

// pmd
configure<PmdExtension> {
    toolVersion = "7.12.0"
    ruleSets = listOf()
    ruleSetFiles = files("config/pmd/ruleset.xml")
}

tasks.withType<Pmd>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
    }
}

// spotless
spotless {
    java {
        googleJavaFormat("1.28.0").aosp()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.check {
    dependsOn(
        tasks.named("spotlessCheck"),
        tasks.named("checkstyleMain"),
        tasks.named("pmdMain"),
        tasks.named("jacocoTestCoverageVerification")
    )
}

tasks.register("format") {
    dependsOn("spotlessApply")
}
