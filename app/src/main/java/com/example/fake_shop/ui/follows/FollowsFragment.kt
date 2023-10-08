package com.example.fake_shop.ui.follows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fake_shop.adapters.FollowAdapter
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.databinding.FragmentFollowsBinding
import com.example.fake_shop.listeners.FollowListener
import org.koin.androidx.viewmodel.ext.android.getViewModel

class FollowsFragment : Fragment(), FollowListener {
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy { getViewModel<FollowsViewModel>() }
    private val followAdapter = FollowAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        init()
        viewModel.init()
        return binding.root
    }

    private fun init() {
        initFollowRecycle()
        initClearButton()
    }

    private fun initFollowRecycle() {
        binding.follows.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        binding.follows.adapter = followAdapter
        val observer = Observer<List<Product>> { newValue ->
            followAdapter.submitList(newValue)
        }
        viewModel.liveData.observe(viewLifecycleOwner, observer)
    }

    private fun initClearButton() {
        binding.clear.setOnClickListener {
            viewModel.dislikeAllProduct()
        }
    }

    override fun onClick(product: Product) {
        viewModel.dislikeOneProduct(product)
    }
}