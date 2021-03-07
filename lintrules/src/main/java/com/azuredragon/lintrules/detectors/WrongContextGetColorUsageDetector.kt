package com.azuredragon.lintrules.detectors

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class WrongContextGetColorUsageDetector: LayoutDetector() {

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

        //TODO Author:akashkhunt Date:7/3/21 What Needs to be Done:If possible add a minSdkVersion check here
        if (attribute.value.contains("context.getColor")) {
            context.report(ISSUE, attribute, context.getLocation(attribute), REPORT_MESSAGE)
        }
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