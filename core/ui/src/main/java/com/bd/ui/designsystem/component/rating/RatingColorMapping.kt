package com.bd.ui.designsystem.component.rating

import androidx.compose.ui.graphics.Color
import com.bd.ui.common.hexToColor

private const val VALUE_1_NUMBER = 1
private const val VALUE_2_NUMBER = 2
private const val VALUE_3_NUMBER = 3
private const val VALUE_4_NUMBER = 4
private const val VALUE_5_NUMBER = 5
private const val VALUE_6_NUMBER = 6
private const val VALUE_7_NUMBER = 7
private const val VALUE_8_NUMBER = 8
private const val VALUE_9_NUMBER = 9
private const val VALUE_10_NUMBER = 10

private const val VALUE_1_COLOR = "#D3012A"
private const val VALUE_2_COLOR = "#EA0B0B"
private const val VALUE_3_COLOR = "#EF3806"
private const val VALUE_4_COLOR = "#F77600"
private const val VALUE_5_COLOR = "#F6B304"
private const val VALUE_6_COLOR = "#F7DD02"
private const val VALUE_7_COLOR = "#CBE00C"
private const val VALUE_8_COLOR = "#90D22A"
private const val VALUE_9_COLOR = "#61B458"
private const val VALUE_10_COLOR = "#12AD49"

enum class RatingColor(val number: Int, val color: Color) {
    VALUE_1(VALUE_1_NUMBER, VALUE_1_COLOR.hexToColor()),
    VALUE_2(VALUE_2_NUMBER, VALUE_2_COLOR.hexToColor()),
    VALUE_3(VALUE_3_NUMBER, VALUE_3_COLOR.hexToColor()),
    VALUE_4(VALUE_4_NUMBER, VALUE_4_COLOR.hexToColor()),
    VALUE_5(VALUE_5_NUMBER, VALUE_5_COLOR.hexToColor()),
    VALUE_6(VALUE_6_NUMBER, VALUE_6_COLOR.hexToColor()),
    VALUE_7(VALUE_7_NUMBER, VALUE_7_COLOR.hexToColor()),
    VALUE_8(VALUE_8_NUMBER, VALUE_8_COLOR.hexToColor()),
    VALUE_9(VALUE_9_NUMBER, VALUE_9_COLOR.hexToColor()),
    VALUE_10(VALUE_10_NUMBER, VALUE_10_COLOR.hexToColor())
}

fun getRatingColorEnumByNumber(number: Int): RatingColor? = RatingColor.entries.firstOrNull { it.number == number }
