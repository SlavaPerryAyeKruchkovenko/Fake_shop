package com.example.fake_shop.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fake_shop.adapters.ProductAdapter
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.databinding.FragmentShopBinding
import com.example.fake_shop.listeners.ProductListener
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ShopFragment : Fragment(), ProductListener {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { getViewModel<ShopViewModel>() }
    private val productAdapter = ProductAdapter(this)
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        searchView = binding.searchAppbar.searchBar
        init()
        viewModel.init()
        return binding.root
    }

    private fun init() {
        initProductsRecycle()
        initSearchBar()
        initUpdateButton()
    }

    private fun initProductsRecycle() {
        binding.characters.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        binding.characters.adapter = productAdapter
        val observer = Observer<OutputOf<List<Product>>> { newValue ->
            when (newValue) {
                is OutputOf.Success -> {
                    binding.characters.visibility = View.VISIBLE
                    binding.loader.root.visibility = View.GONE
                    productAdapter.submitList(newValue.value)
                }
                is OutputOf.Error.NotFoundError -> {
                    binding.characters.visibility = View.GONE
                    binding.loader.root.visibility = View.GONE
                }
                is OutputOf.Error -> {
                    binding.characters.visibility = View.GONE
                    binding.loader.root.visibility = View.GONE
                }
                is OutputOf.Loader -> {
                    binding.characters.visibility = View.GONE
                    binding.loader.root.visibility = View.VISIBLE
                }
                else -> {
                    binding.characters.visibility = View.GONE
                    binding.loader.root.visibility = View.GONE
                }
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner, observer)
    }

    private fun initSearchBar() {
        this.searchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(value: String): Boolean {
                searchView?.clearFocus()
                return false
            }

            override fun onQueryTextChange(value: String): Boolean {
                viewModel.filterProductsByTitle(value)
                return false
            }
        })
    }
    private fun initUpdateButton(){
        this.binding.updateBtn.setOnClickListener{
            this.viewModel.updateProducts()
        }
    }
    override fun onClick(product: Product) {
        val bundle = Bundle()
        /*bundle.apply {
            putString("character_id", profile.id)
        }
        findNavController().navigate(R.id.action_characters_to_character_portrait, bundle)*/
    }

    override fun onDestroy() {
        searchView?.clearFocus()
        super.onDestroy()
        _binding = null
    }
}