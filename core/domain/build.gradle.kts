plugins {
    id("library-convention")
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.javax.inject)
}
