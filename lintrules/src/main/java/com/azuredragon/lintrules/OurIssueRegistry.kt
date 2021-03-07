package com.azuredragon.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.azuredragon.lintrules.detectors.IncompleteTodoDetector
import com.azuredragon.lintrules.detectors.TextViewIssuesDetector
import com.azuredragon.lintrules.detectors.WrongContextGetColorUsageDetector

@Suppress("UnstableApiUsage")
class OurIssueRegistry: IssueRegistry() {

    override val issues: List<Issue> = listOf(
            TextViewIssuesDetector.ISSUE,
            WrongContextGetColorUsageDetector.ISSUE,
            IncompleteTodoDetector.ISSUE
    )

    override val api: Int = CURRENT_API
}