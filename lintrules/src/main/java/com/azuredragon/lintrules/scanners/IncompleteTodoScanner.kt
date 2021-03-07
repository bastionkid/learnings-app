package com.azuredragon.lintrules.scanners

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.azuredragon.lintrules.detectors.IncompleteTodoDetector
import org.jetbrains.uast.UComment
import org.jetbrains.uast.UFile
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class IncompleteTodoScanner(private val context: JavaContext): UElementHandler() {

    override fun visitFile(node: UFile) {
        val allComments = node.allCommentsInFile
        allComments.forEach { comment ->
            val commentText = comment.text

            if (commentText.contains("TODO", ignoreCase = true) && !isValidTodo(commentText)) {
                reportUsage(context, comment)
            }
        }
    }

    @Suppress("UnstableApiUsage")
    private fun reportUsage(context: JavaContext, comment: UComment) {
        context.report(
            issue = IncompleteTodoDetector.ISSUE,
            location = context.getLocation(comment),
            message = IncompleteTodoDetector.REPORT_MESSAGE,
            quickfixData = getQuickfixData()
        )
    }

    private fun getQuickfixData(): LintFix {
        val regex = Regex("TODO|todo")

        val newText = "TODO Author:${System.getProperty("user.name")} Date:${LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"))} What Needs to be Done:"

        LintFix.create().replace().build()
        return LintFix.create()
            .replace()
            .pattern(regex.pattern)
            .with(newText)
            .robot(true)
            .independent(true)
            .build()
    }

    private fun isValidTodo(commentText: String): Boolean {
        return Regex("//\\s*TODO\\s*AUTHOR[:a-zA-Z\\s]*DATE:[:0-9/]*\\s*WHAT\\sNEEDS\\sTO\\sBE\\sDONE:[\\w\\W]*").matches(commentText.toUpperCase(Locale.ROOT))
    }
}