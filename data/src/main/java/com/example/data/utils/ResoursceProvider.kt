package com.example.data.utils

import android.content.Context
import android.content.res.Resources

class ResourceProvider(private val context: Context, val resources: Resources) {
    fun getString(str: String): String {
        return context.getString(resources.getIdentifier(
            str,
            "string",
            context.packageName
        ))
    }

    fun getStringArray(str: String): Array<String> {
        return resources.getStringArray(resources.getIdentifier(
            str,
            "array",
            context.packageName
        ))
    }
}