package com.azuredragon.lintrules.detectors

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.azuredragon.lintrules.scanners.FragmentConstructorUsageScanner
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement

@Suppress("UnstableApiUsage")
class FragmentConstructorUsageDetector: Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UClass::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return FragmentConstructorUsageScanner(context)
    }

    companion object {
        const val REPORT_MESSAGE = "It's recommended to not use Fragment constructor to pass data to it unless FragmentFactory is used to instantiate the Fragment. Passing data directly via constructor leads to crashes when a process with Fragment at top is restored after being killed by the OS."

        private val IMPLEMENTATION = Implementation(FragmentConstructorUsageDetector::class.java, Scope.JAVA_FILE_SCOPE)

        val ISSUE = Issue.create(
            id = "FragmentConstructorUsage",
            briefDescription = "To pass the data to Fragments please use Bundle arguments",
            explanation = REPORT_MESSAGE,
            category = Category.CORRECTNESS,
            priority = 10,
            severity = Severity.FATAL,
            implementation = IMPLEMENTATION
        )
    }
}