package com.azuredragon.learnings.ktxextensions

fun mergeMaps(vararg maps: Map<*, *>?): Map<*, *> {
    val mergedMap = mutableMapOf<Any?, Any?>()

    maps.forEach { map ->
        map?.let { mergedMap.putAll(map) }
    }

    return mergedMap.toMap()
}