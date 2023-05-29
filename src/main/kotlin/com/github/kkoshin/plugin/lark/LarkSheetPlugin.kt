package com.github.kkoshin.plugin.lark

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * lark/feishu sheet
 */
class LarkSheetPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create(EXTENSION_NAME, LarkSheetExtension::class.java)
        target.afterEvaluate {
            target.tasks.maybeCreate(TASK_LARK_SHEET_DOWNLOAD, LarkSheetDownloadTask::class.java).apply {
                group = TASK_GROUP
                description = "download lark sheet to local as csv file"
            }
        }
    }

    companion object {
        const val EXTENSION_NAME = "lark"

        const val TASK_GROUP = "lark"
        const val TASK_LARK_SHEET_DOWNLOAD = "larkSheetDownload"
    }
}