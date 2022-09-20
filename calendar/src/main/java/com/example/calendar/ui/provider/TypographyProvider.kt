package com.example.calendar.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.calendar.ui.DesignSystemTypography

val LocalDesignSystemTypography = staticCompositionLocalOf<DesignSystemTypography> {
    error("No typography provided")
}

@Composable
fun ProvideDesignSystemTypography(
    typography: DesignSystemTypography,
    content: @Composable () -> Unit,
) {
    val styles = remember { typography }
    CompositionLocalProvider(
        values = arrayOf(LocalDesignSystemTypography provides styles),
        content = content
    )
}