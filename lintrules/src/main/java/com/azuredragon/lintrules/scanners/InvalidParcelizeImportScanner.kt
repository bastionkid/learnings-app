package com.azuredragon.lintrules.scanners

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.azuredragon.lintrules.detectors.InvalidParcelizeImportDetector
import org.jetbrains.uast.UImportStatement

class InvalidParcelizeImportScanner(private val context: JavaContext): UElementHandler() {

    @Suppress("UnstableApiUsage")
    override fun visitImportStatement(node: UImportStatement) {
        if (node.importReference?.sourcePsi?.text == "kotlinx.android.parcel.Parcelize") {
            context.report(
                issue = InvalidParcelizeImportDetector.ISSUE,
                location = context.getLocation(node),
                message = InvalidParcelizeImportDetector.REPORT_MESSAGE,
                quickfixData = getQuickFixData()
            )
        }
    }

    private fun getQuickFixData(): LintFix {
        return LintFix.create()
            .replace()
            .pattern("kotlinx.android.parcel.Parcelize")
            .with("kotlinx.parcelize.Parcelize")
            .robot(true)
            .independent(true)
            .build()
    }
}