package com.example.mvisample.api

import com.example.mvisample.model.Animal
import retrofit2.http.GET

interface AnimalApi {
    @GET("animal.json")
    suspend fun getAnimals() : List<Animal>
}