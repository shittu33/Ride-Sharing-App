package com.example.lincride.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lincride.R

private val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold)
)

private val SFProDisplay = FontFamily(
    Font(R.font.sf_pro_display_regular, FontWeight.Normal),
    Font(R.font.sf_pro_display_medium, FontWeight.Medium),
    Font(R.font.sf_pro_display_bold, FontWeight.SemiBold),
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.W600,
        fontSize = 17.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 21.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 15.sp
    ),

    labelSmall = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 21.sp
    ),
)
