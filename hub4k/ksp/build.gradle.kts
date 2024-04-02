

dependencies {
    implementation(project(":hub4k"))
    implementation(Square.kotlinPoet)
    implementation("com.squareup:kotlinpoet-ksp:_")
    implementation("com.google.devtools.ksp:symbol-processing-api:_")

    testApi("com.google.devtools.ksp:symbol-processing-api:_")
    testApi(project(":result4k"))

    kspTest(project(":hub4k-ksp"))
}
