package com.example.mvisample.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvisample.api.AnimalApi
import com.example.mvisample.api.AnimalRepo

class ViewModelFactory(private val animalApi: AnimalApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // If model class is correct return them as ViewModel with Value
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(AnimalRepo(animalApi)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}