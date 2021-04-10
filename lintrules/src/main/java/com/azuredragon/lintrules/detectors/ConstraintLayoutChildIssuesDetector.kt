package com.azuredragon.lintrules.detectors

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element
import org.w3c.dom.Node

@Suppress("UnstableApiUsage")
class ConstraintLayoutChildIssuesDetector: LayoutDetector() {

    //TODO Author:akashkhunt Date:01/04/21 What Needs to be Done:Refactor the hard-coded class name with SdkConstants.* alternative once available
    override fun getApplicableElements(): Collection<String> {
        return listOf("androidx.constraintlayout.widget.ConstraintLayout")
    }

    override fun visitElement(context: XmlContext, element: Element) {
        var currentNode: Node? = element.firstChild

        while (currentNode != null) {
            if (currentNode.textContent.contains(SdkConstants.VALUE_MATCH_PARENT)) {
                context.report(
                    issue = ISSUE,
                    location = context.getLocation(currentNode),
                    message = REPORT_MESSAGE,
                    quickfixData = null
                )
            }

            currentNode = currentNode.nextSibling
        }
    }

    companion object {
        private const val REPORT_MESSAGE = "It's recommended to use 0dp with constraints rather then using match_parent for layout_width & layout_height."

        private val IMPLEMENTATION = Implementation(ConstraintLayoutChildIssuesDetector::class.java, Scope.RESOURCE_FILE_SCOPE)

        val ISSUE = Issue.create(
            id = "ConstraintLayoutChildIssues",
            briefDescription = "Use 0dp with constraints for layout_width and layout_height.",
            explanation = REPORT_MESSAGE,
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.ERROR,
            implementation = IMPLEMENTATION
        )
    }
}