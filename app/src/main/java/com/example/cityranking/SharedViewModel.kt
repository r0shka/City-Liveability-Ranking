package com.example.cityranking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cityranking.data.City
import com.example.cityranking.data.FirestoreRepository
import com.example.cityranking.data.Resource
import kotlinx.coroutines.Dispatchers

class SharedViewModel(private val handle: SavedStateHandle) : ViewModel() {

    var firestoreRepository = FirestoreRepository

    fun setCity(city: City) {
        handle.set("city", city)
    }

    fun getCity(): City? {
        return handle.get<City>("city")
    }

    /**
     * First 5 cities from Mercer list
     */
    val getMercerTop5Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getMercerTop5Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    /**
     * Top 25 cities from Mercer list
     */
    val getMercerTop25Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getMercerTop25Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    /**
     * First 5 cities from the Economist list
     */
    val getEconomistTop5Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getEconomistTop5Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    /**
     * Top 10 cities from the Economist list
     */
    val getEconomistTop10Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getEconomistTop10Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    val getMonocleTop5Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getMonocleTop5Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    val getMonocleTop25Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getMonocleTop25Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    val getNumbeoTop5Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getNumbeoTop5Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    val getNumbeoTop25Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getNumbeoTop25Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    val getQsTop5Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getQsTop5Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    val getQsTop25Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getQsTop25Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    val getMostVisitedTop10Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getMostVisitedTop10Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

    val getUhnwTop10Cities = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val eventList = firestoreRepository.getUhnwTop10Cities()
            emit(eventList)
        } catch (e: Exception) {
            emit(Resource.Failure(e.cause!!))
        }
    }

}