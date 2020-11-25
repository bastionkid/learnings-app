package com.azuredragon.learnings.models

data class GridItem(
    val rowSpan: Int,
    val columnSpan: Int,
    val color: Int
) {
    companion object {
        val DATA_LIST = listOf(
            GridItem(1, 2, android.R.color.holo_blue_dark),
            GridItem(2, 1, android.R.color.holo_red_dark),
            GridItem(1, 1, android.R.color.holo_orange_dark),
            GridItem(1, 1, android.R.color.holo_green_dark),
            GridItem(1, 1, android.R.color.holo_blue_dark),
            GridItem(1, 1, android.R.color.holo_red_dark),
            GridItem(1, 1, android.R.color.holo_orange_dark),
            GridItem(1, 3, android.R.color.holo_green_dark)
        )
    }
}