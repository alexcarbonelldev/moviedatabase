package com.bd.convention

import org.gradle.api.JavaVersion

object BuildConstants {

    const val COMPILE_SDK_VERSION = 35
    const val MIN_SDK_VERSION = 29
    const val TARGET_SDK_VERSION = 34
    const val JVM_TARGET_VERSION = "11"
    val JAVA_VERSION = JavaVersion.VERSION_11
}
