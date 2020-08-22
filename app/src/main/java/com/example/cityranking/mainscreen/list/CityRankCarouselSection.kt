package com.example.cityranking.mainscreen.list

import com.example.cityranking.data.City
import com.example.cityranking.data.Resource
import com.xwray.groupie.Section

class CityRankCarouselSection(
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
                        CityRankCarouselItem(
                            title,
                            description,
                            dataSource,
                            data.data as ArrayList<City>
                        )
                    )
                )
            }
            is Resource.Loading<*> -> {
                update(
                    listOf(
                        CityRankCarouselItem(
                            title,
                            description,
                            dataSource,
                            ArrayList()
                        )
                    )
                )
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