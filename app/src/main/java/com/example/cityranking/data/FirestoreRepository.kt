package com.example.cityranking.data

import com.example.cityranking.utilities.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirestoreRepository {

    private const val collectionPath = "cities-test5"
    private val db = Firebase.firestore

    suspend fun getMercerTop5Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .orderBy("mercer.$MERCER_LATEST_DATA_YEAR")
            .limit(5)
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getEconomistTop5Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .orderBy("eiu.$ECONOMIST_LATEST_DATA_YEAR")
            .limit(5)
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getMonocleTop5Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .orderBy("monocle.$MONOCLE_LATEST_DATA_YEAR")
            .limit(5)
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getNumbeoTop5Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .orderBy("numbeo.$NUMBEO_LATEST_DATA_YEAR")
            .limit(5)
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getQsTop5Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .orderBy("qs.$QS_LATEST_DATA_YEAR")
            .limit(5)
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getMercerTop25Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .whereLessThan("mercer.$MERCER_LATEST_DATA_YEAR", 26)
            .orderBy("mercer.$MERCER_LATEST_DATA_YEAR")
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getEconomistTop10Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .whereLessThan("eiu.$ECONOMIST_LATEST_DATA_YEAR", 11)
            .orderBy("eiu.$ECONOMIST_LATEST_DATA_YEAR")
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getMonocleTop25Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .whereLessThan("monocle.$MONOCLE_LATEST_DATA_YEAR", 26)
            .orderBy("monocle.$MONOCLE_LATEST_DATA_YEAR")
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getNumbeoTop25Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .whereLessThan("numbeo.$NUMBEO_LATEST_DATA_YEAR", 26)
            .orderBy("numbeo.$NUMBEO_LATEST_DATA_YEAR")
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getQsTop25Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .whereLessThan("qs.$QS_LATEST_DATA_YEAR", 26)
            .orderBy("qs.$QS_LATEST_DATA_YEAR")
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getMostVisitedTop10Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .orderBy("mostVisited.$MOST_VISITED_LATEST_DATA_YEAR", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }

    suspend fun getUhnwTop10Cities(): Resource<ArrayList<City>> {
        val result = db.collection(collectionPath)
            .orderBy("uhnwPopulation", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .await()
        val cities = Utils.querySnapshotToArrayList(result)
        return Resource.Success(cities)
    }


}