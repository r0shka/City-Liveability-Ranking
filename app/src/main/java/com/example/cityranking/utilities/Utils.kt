package com.example.cityranking.utilities

import com.example.cityranking.data.City
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


object Utils {

    /**
     * Fetches rank of current city in one of the lists
     * @param item current city
     * @param listName name of the list from which we want to get the ranking
     */
    fun getCityRank(item: City, listName: String): Int? {
        when (listName) {
            MERCER_LIST -> return item.mercer?.get(MERCER_LATEST_DATA_YEAR)
            ECONOMIST_LIST -> return item.eiu?.get(ECONOMIST_LATEST_DATA_YEAR)
            MONOCLE_LIST -> return item.monocle?.get(MONOCLE_LATEST_DATA_YEAR)
            NUMBEO_LIST -> return item.numbeo?.get(NUMBEO_LATEST_DATA_YEAR)
            QS_STUDENT_LIST -> return item.qs?.get(QS_LATEST_DATA_YEAR)
            MOST_VISITED_LIST -> return item.mostVisited?.get(MOST_VISITED_LATEST_DATA_YEAR)
        }
        return 0
    }

    /**
     * Creates a firebase reference of the image to download
     * @param id city id, matches the name of the picture
     * @return Firebase Storage Reference
     */
    fun getImageRef(id: String?, size: String): StorageReference {
        val storageReference = Firebase.storage.reference
        return storageReference.child("thumbnails/$id$size")
    }

    /**
     * Transforms Firestore data into arraylist of Cities
     * @param result Firestore answer
     * @return arraylist of Cities
     */
    fun querySnapshotToArrayList(result: QuerySnapshot): ArrayList<City> {
        val cities = arrayListOf<City>()
        for (city in result) {
            val item = city.toObject<City>()
            item.id = city.id
            cities.add(item)
        }
        return cities
    }
}