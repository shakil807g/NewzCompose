package com.shakil.newzcompose.ui.edgetoedge

private val rangeForRandom = (0..100000)

fun randomSampleImageUrl(seed: Int = rangeForRandom.random()): String {
    return "https://picsum.photos/seed/$seed/300/300"
}