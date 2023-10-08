package com.example.fake_shop.ui.shop

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fake_shop.R
import com.example.fake_shop.adapters.ProductAdapter
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.databinding.FragmentShopBinding
import com.example.fake_shop.listeners.ProductListener
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.google.android.material.snackbar.Snackbar

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
                    binding.errorText.visibility = View.GONE
                    productAdapter.submitList(newValue.value)
                }
                is OutputOf.Error.NotFoundError -> {
                    binding.characters.visibility = View.GONE
                    binding.loader.root.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                }
                is OutputOf.Error.InternetError -> {
                    binding.characters.visibility = View.VISIBLE
                    binding.loader.root.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    productAdapter.submitList(newValue.value)
                    viewShackBar(newValue.message)
                }
                is OutputOf.Error.ResponseError -> {
                    binding.characters.visibility = View.VISIBLE
                    binding.loader.root.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    productAdapter.submitList(newValue.value)
                    viewShackBar(newValue.message)
                }
                is OutputOf.Error -> {
                    binding.characters.visibility = View.GONE
                    binding.loader.root.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = newValue.message
                }
                is OutputOf.Loader -> {
                    binding.characters.visibility = View.GONE
                    binding.loader.root.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                }
                else -> {
                    binding.characters.visibility = View.GONE
                    binding.loader.root.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    viewShackBar("unchecked Error")
                }
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner, observer)
    }
    private fun viewShackBar(text:String){
        val snackbar = Snackbar.make(binding.root, text,
            Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(),R.color.gray_200))
        snackbar.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
        snackbar.show()
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