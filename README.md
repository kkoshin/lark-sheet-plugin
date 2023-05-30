### Quick Start
[![](https://jitpack.io/v/kkoshin/lark-sheet-plugin.svg)](https://jitpack.io/#kkoshin/lark-sheet-plugin)

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
  
    // 脚本所在目录，相对于当前这个 module 的路径
    scriptDirectory = "../scripts"
    
    // 读取某一类的，目前暂只能支持一种
    strings {
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