import com.bd.convention.BuildConstants
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = BuildConstants.JAVA_VERSION
    targetCompatibility = BuildConstants.JAVA_VERSION
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(BuildConstants.JVM_TARGET)
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
    }
}