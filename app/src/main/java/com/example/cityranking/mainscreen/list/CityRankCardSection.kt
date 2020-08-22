package com.example.cityranking.mainscreen.list

import com.xwray.groupie.Section

class CityRankCardSection(description: String, dataSource: String) : Section() {

    private fun update(description: String, dataSource: String) {
        update(listOf(CityRankCardItem(description, dataSource)))
    }

    init {
        update(description, dataSource)
    }
}