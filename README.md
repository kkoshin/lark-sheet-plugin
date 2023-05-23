## android-library
> 当前版本：`0.2.0` 

android library 模版。

使用该模版创建后记得修改 settings.gradle 里 rootProject.name 哦

### 构建说明
当前仓库不包含 app 模块，需要在根目录下的 local.properties 声明 application 所在的路径

```
appShell=~/Projects/android-application
```

### 依赖版本说明
- `libs.versions.toml` 声明了基础的版本依赖，默认就会生效，不要直接修改，来自于 https://github.com/foodiestudio/application
- `libraryLibs.versions.toml` 用于基础的版本依赖之外额外需要的 library 的依赖


### 混淆说明
如果使用了序列化，记得在 proguard 里添加, 详见 [kotlinx serialization 说明](https://github.com/Kotlin/kotlinx.serialization#android)：

```
# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <1>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

# Serializer for classes with named companion objects are retrieved using `getDeclaredClasses`.
# If you have any, uncomment and replace classes with those containing named companion objects.
#-keepattributes InnerClasses # Needed for `getDeclaredClasses`.
#-if @kotlinx.serialization.Serializable class
#com.example.myapplication.HasNamedCompanion, # <-- List serializable classes with named companions.
#com.example.myapplication.HasNamedCompanion2
#{
#    static **$* *;
#}
#-keepnames class <1>$$serializer { # -keepnames suffices; class is kept when serializer() is kept.
#    static <1>$$serializer INSTANCE;
#}
```
