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
            val helper = LarkSheetHelper(
                appId = client.appId,
                appSecret = client.appSecret,
                chinaOnly = client.feishu
            )
            val sheetToken = spreadsheetToken
            val id = sheetId
            println("Fetching sheet($sheetToken/$id) range...")
            val range = helper.fetchSheetContentRange(spreadsheetToken = sheetToken, sheetId = id)
                .getOrThrow()
            println("Fetching sheet($sheetToken/$id) content...")
            val content = helper.fetchSheetContent(sheetToken, range).getOrThrow()
            val destDir = getDownloadDir(project, this)
            destDir.mkdirs()
            csvWriter().writeAll(content, File(destDir, "output.csv"))
        }
    }
}