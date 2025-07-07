package com.volpis.welcome_screen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ButtonStyle(
    val backgroundColor: Color = Color.Blue,
    val textColor: Color = Color.White,
    val fontSize: TextUnit = 16.sp,
    val fontWeight: FontWeight = FontWeight.Normal,
    val isUnderlined: Boolean = false,
    val isBold: Boolean = false,
    val borderWidth: Dp = 0.dp,
    val borderColor: Color = Color.Transparent,
    val cornerRadius: Dp = 8.dp,
    val paddingHorizontal: Dp = 16.dp,
    val paddingVertical: Dp = 8.dp
)