package com.example.fake_shop.ui.product

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fake_shop.R
import com.example.fake_shop.data.models.OutputOf
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.databinding.FragmentProductBinding
import com.example.fake_shop.ui.NavigationBarHelper
import com.example.fake_shop.utils.ImageUtils.getImageFromUrl
import com.example.fake_shop.utils.ImageUtils.getImageToShare
import com.example.fake_shop.utils.ProductUtils
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel


class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { getViewModel<ProductViewModel>() }
    private lateinit var requestWriteExternalStorage: ActivityResultLauncher<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        initRequestPermissions()
        init()
        initArguments()
        return binding.root
    }

    private fun initArguments() {
        if (arguments != null) {
            val id = this.arguments?.getString("product_id")
            if (id != null) {
                viewModel.init(id)
            }
        }
    }

    private fun initRequestPermissions() {
        requestWriteExternalStorage = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                val product = viewModel.getCurProduct()
                val context = requireContext()
                if (product != null) {
                    downloadProductImage(context, product)
                } else {
                    viewShackBar("product not found")
                }
            } else {
                viewShackBar("permissions not granted")
            }
        }
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
        binding.appbar.toolBar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.like -> {
                    viewModel.changeLike()
                    true
                }
                R.id.notify -> {
                    viewShackBar("notify function isn't allow")
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
                is OutputOf.Loader -> {
                    binding.product.visibility = View.GONE
                    binding.loader.root.visibility = View.VISIBLE
                }
                else -> {
                    binding.product.visibility = View.GONE
                    binding.loader.root.visibility = View.GONE
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
            val product = viewModel.getCurProduct()
            val context = requireContext()

            val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
            val hasWritePermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED || minSdk29

            if (product != null) {
                val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
                if (!hasWritePermission) {
                    requestWriteExternalStorage.launch(permission)
                } else {
                    downloadProductImage(context, product)
                }
            }
        }
    }
    private fun initShareBtn() {
        binding.share.setOnClickListener {
            val product = viewModel.getCurProduct()
            if (product != null) {
                shareProduct(product)
            } else {
                viewShackBar("Product not found")
            }

        }
    }

    private fun shareProduct(product: Product) {
        lifecycleScope.launch {
            val image = getImageFromUrl(product.image)
            if (image != null) {
                val uri = getImageToShare(requireContext(), image)
                if (uri != null) {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_STREAM, uri)
                        putExtra(Intent.EXTRA_TEXT, product.title)
                        putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                        type = "image/*"
                    }
                    startActivity(Intent.createChooser(intent, "Share Product"))
                }
            } else {
                viewShackBar("Image not loaded")
            }
        }
    }

    private fun downloadProductImage(context: Context, product: Product) {
        lifecycleScope.launch {
            val isSuccess = ProductUtils.downloadImage(context, product)
            if (isSuccess) {
                viewShackBar("Success")
            } else {
                viewShackBar("Image not download")
            }
        }
    }

    private fun viewShackBar(text: String) {
        val snackbar = Snackbar.make(
            binding.root, text, Snackbar.LENGTH_LONG
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