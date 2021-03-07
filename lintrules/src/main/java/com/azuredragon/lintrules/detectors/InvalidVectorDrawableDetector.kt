package com.azuredragon.lintrules.detectors

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import com.android.utils.XmlUtils
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class InvalidVectorDrawableDetector: ResourceXmlDetector() {

    private var cachedMinApi = -1

    override fun getApplicableElements(): Collection<String> {
        return listOf(SdkConstants.TAG_VECTOR)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        if (getMinSdk(context) < 24) {
            val folderType = context.resourceFolderType
            if (folderType != ResourceFolderType.LAYOUT) {
                if (folderType == ResourceFolderType.DRAWABLE) {
                    XmlUtils.getSubTagsAsList(element).forEach { childElement ->
                        if (SdkConstants.TAG_GRADIENT == childElement.tagName) {
                            context.report(
                                issue = ISSUE,
                                location = context.getLocation(childElement),
                                message = REPORT_MESSAGE,
                                quickfixData = null
                            )
                        }
                    }
                }
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

    companion object {
        private val supportedUriSet = setOf(SdkConstants.ANDROID_URI, SdkConstants.AUTO_URI)

        private val IMPLEMENTATION = Implementation(InvalidVectorDrawableDetector::class.java, Scope.ALL_RESOURCES_SCOPE, Scope.RESOURCE_FILE_SCOPE)

        private const val REPORT_MESSAGE = "Usage of <gradient> is prohibited in VectorDrawables as it leads to crash on pre-Nougat i.e. < API 24 devices"

        val ISSUE = Issue.create(
            id = "InvalidVectorDrawable",
            briefDescription = "Usage of <gradient> is prohibited in VectorDrawables as it leads to crash on pre-Nougat i.e. < API 24 devices",
            explanation = "Please use webp resource instead of VectorDrawable for this resource",
            category = Category.CORRECTNESS,
            priority = 10,
            severity = Severity.FATAL,
            implementation = IMPLEMENTATION
        )
    }
}