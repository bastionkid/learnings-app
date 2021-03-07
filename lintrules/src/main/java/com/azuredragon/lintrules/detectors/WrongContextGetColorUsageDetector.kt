package com.azuredragon.lintrules.detectors

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class WrongContextGetColorUsageDetector: LayoutDetector() {

    private var cachedMinApi = -1

    override fun getApplicableAttributes(): Collection<String> = listOf(
        "textColor",
        "background",
        "tint",
        "backgroundTint",
        "drawableTint",
        "iconTint",
        "buttonTint",
        "indeterminateTint",
        "thumbTint",
        "progressTint",
        "progressBackgroundTint",
        "chipIconTint"
    )

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        if (!supportedUriSet.contains(attribute.namespaceURI)) return

        if (getMinSdk(context) < 23) {
            if (attribute.value.contains("context.getColor")) {
                context.report(
                    issue = ISSUE,
                    scope = attribute,
                    location = context.getLocation(attribute),
                    message = REPORT_MESSAGE,
                    quickfixData = getQuickfixData()
                )
            }
        }
    }

    private fun getMinSdk(context: Context): Int {
        if (cachedMinApi == -1) {
            cachedMinApi = context.mainProject.minSdkVersion.featureLevel

            if (cachedMinApi == 1 && !context.mainProject.isAndroidProject) {
                // Don't flag API checks in non-Android projects
                cachedMinApi = Integer.MAX_VALUE
            }
        }

        return cachedMinApi
    }

    private fun getQuickfixData(): LintFix {
        return LintFix.create()
            .replace()
            .text("context.getColor(")
            .with("ContextCompat.getColor(context, ")
            .robot(true)
            .independent(true)
            .build()
    }

    companion object {
        private val supportedUriSet = setOf(SdkConstants.ANDROID_URI, SdkConstants.AUTO_URI)

        private val IMPLEMENTATION = Implementation(WrongContextGetColorUsageDetector::class.java, Scope.ALL_RESOURCES_SCOPE, Scope.RESOURCE_FILE_SCOPE)

        private const val REPORT_MESSAGE = "Usage of context.getColor(colorRes) is prohibited as it leads to crash on pre-Marshmallow i.e. < API 23 devices"

        val ISSUE = Issue.create(
            id = "WrongContextGetColorUsage",
            briefDescription = "Usage of context.getColor(colorRes) is prohibited as it leads to crash on pre-Marshmallow i.e. < API 23 devices",
            explanation = "Please use ContextCompat.getColor(context, colorRes) to resolve colors for backward compatibility.",
            category = Category.CORRECTNESS,
            priority = 10,
            severity = Severity.FATAL,
            implementation = IMPLEMENTATION
        )
    }
}