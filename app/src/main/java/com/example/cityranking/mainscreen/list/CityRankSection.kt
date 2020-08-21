package com.example.cityranking.mainscreen.list

import com.example.cityranking.data.City
import com.example.cityranking.data.Resource
import com.xwray.groupie.Section

class CityRankSection(
    private val title: String,
    private val description: String,
    private val dataSource: String,
    data: Resource<ArrayList<City>>
) : Section() {

    fun update(data: Resource<ArrayList<City>>) {
        when (data) {
            is Resource.Success<*> -> {
                update(
                    listOf(
                        CityRankSectionItem(
                            title,
                            description,
                            dataSource,
                            data.data as ArrayList<City>
                        )
                    )
                )
            }
            is Resource.Loading<*> -> {
                // TODO show loading item
            }
            is Resource.Failure<*> -> {
                // TODO show error item
            }
        }

    }

    init {
        update(data)
    }
}