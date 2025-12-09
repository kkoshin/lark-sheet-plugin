### Quick Start
[![](https://jitpack.io/v/kkoshin/lark-sheet-plugin.svg)](https://jitpack.io/#kkoshin/lark-sheet-plugin)

- register plugin
    ```kotlin
    // settings.gradle.kts
    pluginManagement {
        repositories {
            google()
            mavenCentral()
            gradlePluginPortal()
            maven("https://jitpack.io")
        }
        resolutionStrategy {
            eachPlugin {
                if (requested.id.id == "lark-sheet") {
                    useModule("com.github.kkoshin:lark-sheet-plugin:$version") // 记得替换下版本号
                }
            }
        }
    }
    ```
- use plugin in module
  ```kotlin
  plugins {
      id("lark-sheet")
  }
  ```
  
 - run task
    - `downloadLarkSheet`: download sheet as CSV file only. You can find the file in `/build/lark-sheet/xxx/yyy/output.csv`
    - `syncLarkSheetString`: depends on `downloadLarkSheet` task and syncing string after successfully downloading .

#### lark-sheet
> 暂不支持多维表格, 并且只支持已经设置过筛选的表格，有一定的局限性，对于一些被删除的行还是会被拉下来，导致一些数据重复

表格可能是单独的，也可能是知识库下的，链接长下面这样：
- https://qznxol4xbc.feishu.cn/sheets/LS4os3xmMhVwmwt1876cJb4Xn5f?sheet=9d44da
- https://vy7enn9y1k.feishu.cn/wiki/wikcnHkhbcItYtlon6MkF7TJShd?sheet=7qllxA

```kotlin
lark {
    client {
        // https://open.feishu.cn/document/home/event-based-messaging/create-app-request-permission
        // 应用详情界面的凭证与基础信息一栏里，可以查询到应用凭证，也就是 App ID 和 App Secret
        appId = "cli_a4fec49d547a1013"
        appSecret = "loy8zSy27oUv9fcuGf8oQdnRFqejh8o1"
        // 使用飞书还是 Lark 站点，可选，默认为 true
        feishu = true
    }
    // 表格访问的 Url，支持表格的也支持知识库下的表格。注意，url请包含子表（?sheet=???）
    url = "https://qznxol4xbc.feishu.cn/sheets/LS4os3xmMhVwmwt1876cJb4Xn5f?sheet=9d44da"
  
    // 脚本所在目录，相对于当前这个 module 的路径
    scriptDirectory = "../scripts"
    
    // 读取某一类的，目前暂只能支持一种
    string {
        language = "English"
        path = "./src/main/res/values/strings.xml"
    }
}
```

#### 常见问题
- 目前只支持设置过「筛选」的表格，因为获取选择范围是借助「筛选」实现的
- 权限报错：
  - 自建应用应该申请相应的权限，至少得有 「查看、评论、编辑和管理电子表格」这个权限
  - 表格不是私有文档，需要开启「链接分享」，「组织内...可阅读」就够了
- 表格内容获取不全，请检查下格式是否一致，部分没有格式化的化可能会导致这个问题
