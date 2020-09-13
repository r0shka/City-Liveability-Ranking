package com.example.cityranking.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Object that matches Firestore data
 * Maps contain data of ranking since 2015 included (2016 for qs)
 * Keys are "2015" to "2019"
 * Data for 2020 will be added eventually
 */
@Parcelize
data class City(
    val name: String? = null,
    var id: String? = null,
    val country: String? = null,
    val description: String? = null,
    val population: Int? = null,
    val crimeIndex: Int? = null,
    val airQuality: Double? = null,
    val buySqmNotCenterUsd: Int? = null,
    val rentOneBedroomNotCenterUsd: Int? = null,
    val salaryNetUsd: Int? = null,
    val trafficCongestion: Int? = null,
    val uhnwPopulation: Int? = null,
    val vacationDaysYear: Double? = null,
    val mercer: Map<String, Int>? = null,
    val eiu: Map<String, Int>? = null,
    val monocle: Map<String, Int>? = null,
    val numbeo: Map<String, Int>? = null,
    val qs: Map<String, Int>? = null,
    val mostVisited: Map<String, Int>? = null
) : Parcelable