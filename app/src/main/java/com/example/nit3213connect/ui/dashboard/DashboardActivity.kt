package com.example.nit3213connect.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nit3213connect.databinding.ActivityDashboardBinding
import com.example.nit3213connect.ui.adapter.EntitiesAdapter
import com.example.nit3213connect.ui.details.DetailsActivity
import com.example.nit3213connect.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val vm: DashboardViewModel by viewModels()
    private lateinit var adapter: EntitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = EntitiesAdapter { e ->
            startActivity(
                Intent(this, DetailsActivity::class.java)
                    .putExtra("title", e.title)
                    .putExtra("subtitle", e.subtitle)
                    .putExtra("description", e.description)
            )
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.entities.collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            Log.d("NIT3213", "Loading dashboard…")
                        }
                        is Resource.Success -> {
                            binding.progress.visibility = View.GONE
                            Log.d("NIT3213", "Loaded ${state.data.size} items")
                            adapter.submitList(state.data)
                        }
                        is Resource.Error -> {
                            binding.progress.visibility = View.GONE
                            val msg = state.cause?.localizedMessage ?: state.message ?: "Failed to load"
                            Toast.makeText(this@DashboardActivity, "Error: $msg", Toast.LENGTH_LONG).show()
                            Log.e("NIT3213", "Dashboard error", state.cause)
                            // Optional: keep fallback items so the screen isn't empty
                            // adapter.submitList(listOf(...))
                        }
                    }
                }
            }
        }

        // *** Use lowercase keypass with safe default ***
        val keypass = (intent.getStringExtra("keypass") ?: "animals").trim().lowercase()
        Toast.makeText(this, "Loading dashboard for: $keypass", Toast.LENGTH_SHORT).show()
        vm.load(keypass)
    }
}
