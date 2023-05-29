package com.github.kkoshin.plugin.lark

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.URI

abstract class LarkSheetDownloadTask : DefaultTask() {

    @TaskAction
    fun build() {
        with(project.extensions.getByType(LarkSheetExtension::class.java)) {
            val helper = LarkSheetHelper(appId = client.appId, appSecret = client.appSecret, chinaOnly = client.feishu)
            val uri = URI.create(sheetUrl)
            val spreadsheetToken = uri.path.split("/").last()
            val sheetId = uri.findParameterValue("sheet")
                ?: throw IllegalStateException("url is not valid. can't find `sheet` Query")
            println("Fetching sheet($spreadsheetToken/$sheetId) range...")
            val range =
                helper.fetchSheetContentRange(spreadsheetToken = spreadsheetToken, sheetId = sheetId).getOrThrow()
            println("Fetching sheet($spreadsheetToken/$sheetId) content...")
            val content = helper.fetchSheetContent(spreadsheetToken, range).getOrThrow()
            val destDir = if (exportDirectory.isNotEmpty()) {
                File(exportDirectory)
            } else {
                File(project.buildDir, "lark-sheet/$spreadsheetToken/$sheetId")
            }
            destDir.mkdirs()
            csvWriter().writeAll(content, File(destDir, "output.csv"))
        }
    }
}

private fun URI.findParameterValue(parameterName: String): String? {
    return rawQuery.split('&').map {
        val parts = it.split('=')
        val name = parts.firstOrNull() ?: ""
        val value = parts.drop(1).firstOrNull() ?: ""
        Pair(name, value)
    }.firstOrNull { it.first == parameterName }?.second
}