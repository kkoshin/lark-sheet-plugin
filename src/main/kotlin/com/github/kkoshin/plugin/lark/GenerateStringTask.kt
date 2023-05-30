package com.github.kkoshin.plugin.lark

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class SyncStringTask : DefaultTask() {

    // python3 scripts/generate_strings.py $path_to_downloaded_csv English ./vlogger/src/main/res/values/strings.xml
    @TaskAction
    fun build() {
        with(project.extensions.getByType(LarkSheetExtension::class.java)) {
            val scriptDirectoryFile = File(scriptDirectory)
            val dir = if (scriptDirectoryFile.isAbsolute) {
                scriptDirectoryFile
            } else {
                File(project.projectDir, scriptDirectoryFile.path)
            }
            val scriptFile = File(dir, "generate_strings.py")
            check(scriptFile.exists()) {
                "can't find generate_strings.py in $scriptDirectory"
            }
            val csvFile = File(getDownloadDir(project, this), "output.csv")
            check(csvFile.exists()) {
                "can't find output.csv, please run `downloadLarkSheet` task first"
            }
            println("python3 ${scriptFile.canonicalPath} ${csvFile.path} ${string.language} ${string.path}")
            project.exec {
                standardOutput = System.out
                errorOutput = System.err
                workingDir = project.projectDir
                executable("python3")
                args(
                    mutableListOf(
                        scriptFile.canonicalPath,
                        csvFile.path,
                        string.language,
                        string.path
                    )
                )
            }
        }
    }
}