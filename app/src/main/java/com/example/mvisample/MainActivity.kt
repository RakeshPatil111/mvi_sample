package com.example.mvisample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvisample.api.AnimalService
import com.example.mvisample.databinding.ActivityMainBinding
import com.example.mvisample.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: AnimalAdapter
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setupUI()
        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Loading -> {
                        mainBinding.btFetchAnimal.visibility = View.GONE
                        mainBinding.pbLoading.visibility = View.VISIBLE
                    }

                    is MainState.Idle -> {

                    }

                    is MainState.Animals -> {
                        mainBinding.rvAnimal.visibility = View.VISIBLE
                        mainBinding.btFetchAnimal.visibility = View.GONE
                        mainBinding.pbLoading.visibility = View.GONE
                        adapter.updateList(it.animals)
                    }

                    is MainState.Error -> {
                        mainBinding.rvAnimal.visibility = View.GONE
                        mainBinding.btFetchAnimal.visibility = View.VISIBLE
                        mainBinding.pbLoading.visibility = View.GONE
                        Toast.makeText(this@MainActivity, it.errorString, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupUI() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(AnimalService.api)).get(MainViewModel::class.java)
        mainBinding.rvAnimal.run {
            addItemDecoration(
                DividerItemDecoration(rv_animal.context, (rv_animal.layoutManager as LinearLayoutManager).orientation)
            )
        }
        adapter = AnimalAdapter()
        mainBinding.rvAnimal.adapter = adapter
        mainBinding.btFetchAnimal.setOnClickListener { lifecycleScope.launch { mainViewModel.userIntent.send(MainIntent.FetchAnimals) } }
    }
}