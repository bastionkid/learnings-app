package com.azuredragon.lintrules.detectors

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.azuredragon.lintrules.scanners.IncompleteTodoScanner
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UFile

@Suppress("UnstableApiUsage")
class IncompleteTodoDetector: Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UFile::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return IncompleteTodoScanner(context)
    }

    companion object {
        private val IMPLEMENTATION = Implementation(IncompleteTodoDetector::class.java, Scope.JAVA_FILE_SCOPE)

        const val REPORT_MESSAGE = "Please setup a Live Template for ease of usage."

        val ISSUE = Issue.create(
            id = "IncompleteTodo",
            briefDescription = "Please use the well defined TODO format with Author, Date & What Needs to be Done data",
            explanation = REPORT_MESSAGE,
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.ERROR,
            implementation = IMPLEMENTATION
        )
    }
}