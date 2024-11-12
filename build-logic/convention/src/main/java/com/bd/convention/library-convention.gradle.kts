import com.bd.convention.BuildConstants

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = BuildConstants.JAVA_VERSION
    targetCompatibility = BuildConstants.JAVA_VERSION
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = BuildConstants.JVM_TARGET_VERSION
    }
}
