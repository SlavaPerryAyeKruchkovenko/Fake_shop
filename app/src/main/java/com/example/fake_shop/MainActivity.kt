package com.example.fake_shop

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fake_shop.databinding.ActivityMainBinding
import com.example.fake_shop.ui.product.ProductFragment
import com.example.fake_shop.ui.product.ProductFragment.Companion.PRODUCT_PARAM

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this._binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initParam()
    }

    private fun initParam() {
        val fragmentParam = intent.getStringExtra(FRAGMENT_PARAM)
        val productId = intent.getStringExtra(PRODUCT_PARAM)
        if (fragmentParam == "ProductFragment" && productId != null) {
            val productFragment = ProductFragment.newInstance(productId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_container, productFragment)
                .commit()
        }
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

    companion object {
        const val FRAGMENT_PARAM = "fragment_param"
    }
}