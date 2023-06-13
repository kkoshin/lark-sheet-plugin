plugins {
    `kotlin-dsl`
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type"
    }
}

dependencies {
    // kotlin 版本由 gradle 里的版本决定，升级 gradle 会升级对应的 kotlin 依赖
    implementation(kotlin("stdlib"))
    implementation("com.larksuite.oapi:oapi-sdk:2.0.21")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.1")
}

group = "com.github.kkoshin"
version = "1.0.1"

// 为 buildSrc 里的 Plugin 创建对应的 id，这样才能在 plugins{} 中使用
gradlePlugin {
    plugins {
        create("larkSheet") {
            id = "lark-sheet"
            implementationClass = "com.github.kkoshin.plugin.lark.LarkSheetPlugin"
        }
    }
}