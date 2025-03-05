package com.example.coroutinefactorialapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonCalculate.setOnClickListener {
            Log.d("MainActivity", "click calculate")

            val value = binding.editTextNumber.text.toString()

            Log.d("MainActivity", "value: $value")
            viewModel.calculate(value)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) { state ->
            stateIsInProgress(state)
            stateIsError(state)

            binding.textViewFactorial.text = state.factorial
        }

    }

    private fun stateIsError(state: State) {
        if (state.isError) {
            Toast.makeText(this, "You didn't enter value", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stateIsInProgress(state: State) {
        if (state.isInProgress) {
            binding.progressBarLoading.visibility = View.VISIBLE
            binding.buttonCalculate.isEnabled = false
            binding.editTextNumber.isEnabled = false
        } else {
            binding.progressBarLoading.visibility = View.GONE
            binding.buttonCalculate.isEnabled = true
            binding.editTextNumber.isEnabled = true
        }
    }
}