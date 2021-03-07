package com.azuredragon.lintrules.detectors

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class TextViewIssuesDetector: LayoutDetector() {

    override fun getApplicableElements(): Collection<String>? {
        return listOf(SdkConstants.TEXT_VIEW)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        context.report(
                issue = ISSUE,
                location = context.getElementLocation(element),
                message = REPORT_MESSAGE,
                quickfixData = null
        )
    }

    private fun getQuickFixData(): LintFix {
        return LintFix.create()
                .replace()
                .text("")
                .with("")
                .build()
    }

    companion object {
        private const val REPORT_MESSAGE = "Do not use hard-coded values for margins and paddings."

        private val IMPLEMENTATION = Implementation(TextViewIssuesDetector::class.java, Scope.RESOURCE_FILE_SCOPE)

        val ISSUE = Issue.create(
                id = "TextViewHardCodedAttributes",
                briefDescription = "Use dimension resources for specifying margins and paddings",
                explanation = "For better support of design guidelines it'a always a better practice to use resources instead of hard-coded values.",
                category = Category.CORRECTNESS,
                priority = 5,
                severity = Severity.WARNING,
                implementation = IMPLEMENTATION
        )
    }
}