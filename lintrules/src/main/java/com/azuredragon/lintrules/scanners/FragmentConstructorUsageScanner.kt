package com.azuredragon.lintrules.scanners

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.azuredragon.lintrules.detectors.FragmentConstructorUsageDetector
import org.jetbrains.uast.UClass

class FragmentConstructorUsageScanner(private val context: JavaContext): UElementHandler() {

    @Suppress("UnstableApiUsage")
    override fun visitClass(node: UClass) {
        var superClass = node.javaPsi.supers.firstOrNull()

        if (node.javaPsi.constructors.firstOrNull()?.hasParameters() == true) {
            while (superClass != null) {
                if (superClass.text.contains("Fragment()", true)) {
                    context.report(
                        issue = FragmentConstructorUsageDetector.ISSUE,
                        location = context.getNameLocation(node),
                        message = FragmentConstructorUsageDetector.REPORT_MESSAGE,
                        quickfixData = null
                    )

                    break
                }

                superClass = superClass.supers.firstOrNull()
            }
        }
    }
}