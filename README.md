### Quick Start

- register plugin
    ```kotlin
    // settings.gradle.kts
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "lark-sheet") {
                useModule("com.github.kkoshin:lark-sheet-plugin:$version")
            }
        }
    }
    ```
- use plugin in module
  ```kotlin
  plugins {
      id("lark-sheet") // for example
  }
  ```

#### lark-sheet
> 暂不支持多维表格, 并且只支持已经设置过筛选的表格

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
    // 表格访问的 Url
    sheetUrl = "https://qznxol4xbc.feishu.cn/sheets/LS4os3xmMhVwmwt1876cJb4Xn5f?sheet=9d44da"
    // export csv file to custom folder, default path like $buildDir/lark-sheet/LS4os3xmMhVwmwt1876cJb4Xn5f/9d44da/output.csv
//    exportDirectory = "./custom"
}
```