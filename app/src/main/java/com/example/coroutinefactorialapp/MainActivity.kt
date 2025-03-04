package com.example.coroutinefactorialapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinefactorialapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()

        binding.buttonCalculate.setOnClickListener {
            Log.d("MainActivity", "click calculate")
            val value = binding.editTextNumber.text.toString()
            Log.d("MainActivity", "value: $value")
            viewModel.calculate(value)
        }
    }

    private fun observeViewModel() {
        viewModel.progress.observe(this) { progress ->
            if (progress) {
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.editTextNumber.isEnabled = false
            } else {
                binding.progressBarLoading.visibility = View.GONE
                binding.editTextNumber.isEnabled = true
            }
        }

        viewModel.error.observe(this) { error ->
            if (error) {
                Toast.makeText(this, "You didn't enter value", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.factorial.observe(this) { factorial ->
            binding.textViewFactorial.text = factorial
        }

    }
}