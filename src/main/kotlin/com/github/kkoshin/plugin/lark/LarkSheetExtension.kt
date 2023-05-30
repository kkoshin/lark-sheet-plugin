package com.github.kkoshin.plugin.lark

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import java.io.File
import java.net.URI

open class LarkSheetExtension constructor(objects: ObjectFactory) {
    val client: LarkClientConfig = objects.newInstance(LarkClientConfig::class.java)
    val string: StringConfig = objects.newInstance(StringConfig::class.java)
    lateinit var url: String
    var exportDirectory = ""

    lateinit var scriptDirectory: String

    fun client(fn: Action<LarkClientConfig>) {
        fn.execute(client)
    }

    fun string(fn: Action<StringConfig>) {
        fn.execute(string)
    }
}

open class LarkClientConfig {
    lateinit var appId: String
    lateinit var appSecret: String
    var feishu = true
}

open class StringConfig {
    lateinit var language: String
    lateinit var path: String
}

val LarkSheetExtension.wikiToken: String
    get() = URI.create(url).path.split("/").last()

val LarkSheetExtension.spreadsheetToken: String
    get() = URI.create(url).path.split("/").last()

val LarkSheetExtension.sheetId: String
    get() = URI.create(url).findParameterValue("sheet")
        ?: throw IllegalStateException("url is not valid. can't find `sheet` Query")

internal fun getDownloadDir(project: Project, extension: LarkSheetExtension): File =
    if (extension.exportDirectory.isNotEmpty()) {
        File(extension.exportDirectory)
    } else {
        File(project.buildDir, "lark-sheet/${extension.spreadsheetToken}/${extension.sheetId}")
    }

private fun URI.findParameterValue(parameterName: String): String? {
    return rawQuery.split('&').map {
        val parts = it.split('=')
        val name = parts.firstOrNull() ?: ""
        val value = parts.drop(1).firstOrNull() ?: ""
        Pair(name, value)
    }.firstOrNull { it.first == parameterName }?.second
}