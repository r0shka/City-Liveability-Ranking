package com.example.cityranking.data


data class MainScreenSection(
    val title: String? = null,
    val description: String? = null,
    val dataSource: String,
    val carouselItems: ArrayList<City>
)
