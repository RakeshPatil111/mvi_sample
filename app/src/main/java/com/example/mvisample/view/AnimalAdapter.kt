package com.example.mvisample.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mvisample.api.AnimalService
import com.example.mvisample.databinding.AnimalItemBinding
import com.example.mvisample.model.Animal

class AnimalAdapter: RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {
    var animals = listOf<Animal>()

    inner class AnimalViewHolder(val binding: AnimalItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = AnimalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.binding.apply {
            this.tvAnimalLocation.text = animals[position].location
            this.tvAnimalName.text = animals[position].name
            this.ivAnimal.load(AnimalService.BASE_URL+""+animals[position].image)
        }
    }

    override fun getItemCount() = animals.size

    fun updateList(animals: List<Animal>) {
        this.animals = animals
    }
}