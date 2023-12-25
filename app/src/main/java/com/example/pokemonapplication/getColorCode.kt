package com.example.pokemonapplication

import android.graphics.Color

fun getColorCode(colorName: String): Int {
    return when (colorName.toLowerCase()) {
        "black" -> Color.parseColor("#000000")
        "white" -> Color.parseColor("#FFFFFF")
        "red" -> Color.parseColor("#FF0000")
        "green" -> Color.parseColor("#00FF00")
        "blue" -> Color.parseColor("#0000FF")
        "yellow" -> Color.parseColor("#FFFF00")
        "purple" -> Color.parseColor("#800080")
        "brown" -> Color.parseColor("#A52A2A")
        "pink" -> Color.parseColor("#FFC0CB")
        "gray" -> Color.parseColor("#808080")

        // Add more color mappings as needed
        else -> Color.parseColor("#FFFFFF")
    }
}
