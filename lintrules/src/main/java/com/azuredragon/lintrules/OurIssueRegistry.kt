package com.azuredragon.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.azuredragon.lintrules.detectors.*

@Suppress("UnstableApiUsage")
class OurIssueRegistry: IssueRegistry() {

    override val issues: List<Issue> = listOf(
        WrongContextGetColorUsageDetector.ISSUE,
        InvalidVectorDrawableDetector.ISSUE,
        IncompleteTodoDetector.ISSUE,
        ConstraintLayoutChildIssuesDetector.ISSUE,
        InvalidParcelizeImportDetector.ISSUE,
        FragmentConstructorUsageDetector.ISSUE,
    )

    override val api: Int = CURRENT_API
}