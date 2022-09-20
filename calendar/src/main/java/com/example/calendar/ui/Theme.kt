package com.example.calendar.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.calendar.ui.palette.*
import com.example.calendar.ui.provider.LocalDesignSystemColors
import com.example.calendar.ui.provider.LocalDesignSystemTypography
import com.example.calendar.ui.provider.ProvideDesignSystemColors
import com.example.calendar.ui.provider.ProvideDesignSystemTypography
import com.example.calendar.ui.debug.debugColors
import com.example.calendar.ui.debug.debugTypography

val lightPalette = DesignSystemColors(
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    textHeadline = TextHeadline,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    textTertiary = TextTertiary,
    textInverted = TextInverted,
    textPositive = TextPositive,
    textNegative = TextNegative,
    textPrimaryLink = TextPrimaryLink,
    textSecondaryLink = TextSecondaryLink,

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/
    backgroundPrimary = BackgroundPrimary,
    backgroundPrimaryElevated = BackgroundPrimaryElevated,
    backgroundModal = BackgroundModal,
    backgroundStroke = BackgroundStroke,
    backgroundSecondary = BackgroundSecondary,
    backgroundSecondaryElevated = BackgroundSecondaryElevated,
    backgroundInverted = BackgroundInverted,
    backgroundOverlay = BackgroundOverlay,
    backgroundHover = BackgroundHover,
    backgroundNavbarIos = BackgroundNavbarIos,

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/
    controlsPrimaryActive = ControlPrimaryActive,
    controlsSecondaryActive = ControlSecondaryActive,
    controlsTertiaryActive = ControlTertiaryActive,
    controlsInactive = ControlInactive,
    controlsAlternative = ControlAlternative,
    controlsActiveTabBar = ControlActiveTabbar,
    controlsInactiveTabBar = ControlInactiveTabbar,

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/
    accentActive = AccentActive,
    accentPositive = AccentPositive,
    accentWarning = AccentWarning,
    accentNegative = AccentNegative,

    brandMtsRed = Brand,

    isDark = false
)

val darkPalette = DesignSystemColors(
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    /** Text **/
    textHeadline = TextHeadlineDark,
    textPrimary = TextPrimaryDark,
    textSecondary = TextSecondaryDark,
    textTertiary = TextTertiaryDark,
    textInverted = TextInvertedDark,
    textPositive = TextPositiveDark,
    textNegative = TextNegativeDark,
    textPrimaryLink = TextPrimaryLinkDark,
    textSecondaryLink = TextSecondaryLinkDark,

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/

    /** Background **/
    backgroundPrimary = BackgroundPrimaryDark,
    backgroundPrimaryElevated = BackgroundPrimaryElevatedDark,
    backgroundModal = BackgroundModalDark,
    backgroundStroke = BackgroundStrokeDark,
    backgroundSecondary = BackgroundSecondaryDark,
    backgroundSecondaryElevated = BackgroundSecondaryElevatedDark,
    backgroundInverted = BackgroundInvertedDark,
    backgroundOverlay = BackgroundOverlayDark,
    backgroundHover = BackgroundHoverDark,
    backgroundNavbarIos = BackgroundNavbarIosDark,

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/

    /** Controls **/
    controlsPrimaryActive = ControlPrimaryActiveDark,
    controlsSecondaryActive = ControlSecondaryActiveDark,
    controlsTertiaryActive = ControlTertiaryActiveDark,
    controlsInactive = ControlInactiveDark,
    controlsAlternative = ControlAlternativeDark,
    controlsActiveTabBar = ControlActiveTabbarDark,
    controlsInactiveTabBar = ControlInactiveTabbarDark,

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/

    /** Accent **/
    accentActive = AccentActiveDark,
    accentPositive = AccentPositiveDark,
    accentWarning = AccentWarningDark,
    accentNegative = AccentNegativeDark,

    brandMtsRed = Brand,

    isDark = true
)

@Composable
fun DesignSystemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) darkPalette else lightPalette
    val typography = DesignSystemTypography()

    ProvideDesignSystemTypography(typography) {
        ProvideDesignSystemColors(colors) {
            MaterialTheme(
                colors = debugColors(darkTheme), // hardcode colors
                typography = debugTypography(), // hardcode typography
                content = content
            )
        }
    }
}

object DesignSystemTheme {
    val colors: DesignSystemColors
        @Composable
        get() = LocalDesignSystemColors.current

    val typography: DesignSystemTypography
        @Composable
        get() = LocalDesignSystemTypography.current
}