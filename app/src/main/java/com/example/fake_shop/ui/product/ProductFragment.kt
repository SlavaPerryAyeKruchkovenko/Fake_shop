package com.example.fake_shop.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fake_shop.R
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.databinding.FragmentProductBinding
import com.example.fake_shop.ui.NavigationBarHelper
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { getViewModel<ProductViewModel>() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        init()
        if (arguments != null) {
            val id = this.arguments?.getString("product_id")
            if (id != null) {
                viewModel.init(id)
            }
        }
        return binding.root
    }

    private fun init() {
        NavigationBarHelper.hideNavigationBar(activity)
        initAppbar()
        initProduct()
        initDownloadBtn()
        initShareBtn()
    }

    private fun initAppbar() {
        binding.appbar.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        initLikeBtn()
        initNotifyBtn()
    }

    private fun initProduct() {
        val productObserver = Observer<OutputOf<Product?>> { newValue ->
            when (newValue) {
                is OutputOf.Success -> {
                    binding.product.visibility = View.VISIBLE
                    binding.loader.root.visibility = View.GONE
                    initProductView(newValue.value)
                }
                is OutputOf.Error.InternetError -> {
                    binding.product.visibility = View.VISIBLE
                    binding.loader.root.visibility = View.GONE
                    initProductView(newValue.value)
                    viewShackBar(newValue.message)
                }
                is OutputOf.Error.ResponseError -> {
                    binding.product.visibility = View.VISIBLE
                    binding.loader.root.visibility = View.GONE
                    initProductView(newValue.value)
                    viewShackBar(newValue.message)
                }
                is OutputOf.Loader -> {
                    binding.product.visibility = View.GONE
                    binding.loader.root.visibility = View.VISIBLE
                }
                else -> {
                    viewShackBar("Unchecked Error")
                }
            }
        }
        viewModel.productLiveData.observe(viewLifecycleOwner, productObserver)
    }

    private fun initProductView(product: Product?) {
        if (product != null) {
            binding.title.text = product.title
            binding.price.text = product.price.toString()
            binding.category.text = product.category
            binding.rating.text = product.rating.toString()
            binding.count.text = product.count.toString()
            try {
                Picasso.get().load(product.image)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.broken_image)
                    .into(binding.image)
            } catch (ex: Exception) {
                Log.e("Error", ex.message.toString())
                ex.printStackTrace()
            }
        }
    }

    private fun initDownloadBtn() {
        binding.download.setOnClickListener {
            viewShackBar("Функция скачивания еще не реализована")
        }
    }

    private fun initShareBtn() {
        binding.share.setOnClickListener {
            viewShackBar("Функция поделиться еще не доступна")
        }
    }

    private fun initLikeBtn() {
        binding.appbar.toolBar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.like -> {
                    viewModel.changeLike()
                    true
                }
                else -> false
            }
        }
        val likeObserver = Observer<Boolean> { newValue ->
            val likeIcon = binding.appbar.toolBar.menu.findItem(R.id.like)
            if (newValue) {
                likeIcon.setIcon(R.drawable.heart_fill)
            } else {
                likeIcon.setIcon(R.drawable.heart)
            }
        }
        viewModel.isLikedLiveData.observe(viewLifecycleOwner, likeObserver)
    }

    private fun initNotifyBtn() {
        binding.appbar.toolBar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.notify -> {
                    viewShackBar("функция уведомления еще не реализована")
                    true
                }
                else -> false
            }
        }
    }

    private fun viewShackBar(text: String) {
        val snackbar = Snackbar.make(
            binding.root, text,
            Snackbar.LENGTH_LONG
        )
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.gray_200))
        snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        NavigationBarHelper.showNavigationBar(activity)
    }
}