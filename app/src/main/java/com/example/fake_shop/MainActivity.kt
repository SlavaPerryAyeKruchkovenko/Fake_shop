package com.example.fake_shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fake_shop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this._binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        val navView = binding.navigationBar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
                    as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
    fun hideBottomNavigationView() {
        binding.navigationBar.visibility = View.GONE
    }

    fun showBottomNavigationView() {
        binding.navigationBar.visibility = View.VISIBLE
    }
}