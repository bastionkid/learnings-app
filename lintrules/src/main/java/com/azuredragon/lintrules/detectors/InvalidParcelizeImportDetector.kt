package com.azuredragon.lintrules.detectors

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.azuredragon.lintrules.scanners.InvalidParcelizeImportScanner
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UImportStatement

@Suppress("UnstableApiUsage")
class InvalidParcelizeImportDetector: Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UImportStatement::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return InvalidParcelizeImportScanner(context)
    }

    companion object {
        private val IMPLEMENTATION = Implementation(InvalidParcelizeImportDetector::class.java, Scope.JAVA_FILE_SCOPE)

        const val REPORT_MESSAGE = "Please use kotlinx.parcelize.Parcelize instead of kotlinx.android.parcel.Parcelize as the latter one is deprecated."

        val ISSUE = Issue.create(
            id = "InvalidParcelizeImport",
            briefDescription = "Please use kotlinx.parcelize.Parcelize instead of kotlinx.android.parcel.Parcelize as the latter one is deprecated.",
            explanation = REPORT_MESSAGE,
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.ERROR,
            implementation = IMPLEMENTATION
        )
    }
}