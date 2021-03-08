package com.azuredragon.learnings.ktxextensions

import java.lang.reflect.Field
import java.lang.reflect.Method

fun <T> Class<T>.getDeclaredAccessibleField(fieldName: String): Field? {
    return try {
        getDeclaredField(fieldName).apply {
            isAccessible = true
        }
    } catch (e: NoSuchFieldException) {
        null
    } catch (e: ClassNotFoundException) {
        null
    }
}

fun <T> Class<T>.getDeclaredAccessibleMethod(methodName: String, parameterTypes: Class<*>): Method? {
    return try {
        getDeclaredMethod(methodName, parameterTypes).apply {
            isAccessible = true
        }
    } catch (e: NoSuchMethodException) {
        null
    } catch (e: ClassNotFoundException) {
        null
    }
}