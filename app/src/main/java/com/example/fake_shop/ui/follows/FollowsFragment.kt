package com.example.fake_shop.ui.follows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fake_shop.R
import com.example.fake_shop.adapters.FollowAdapter
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.databinding.DialogAcceptBinding
import com.example.fake_shop.databinding.FragmentFollowsBinding
import com.example.fake_shop.listeners.FollowListener
import com.example.fake_shop.utils.DialogUtils
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
        initAppbar()
        initFollowRecycle()
        initClearButton()
    }

    private fun initAppbar() {
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initFollowRecycle() {
        binding.follows.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        binding.follows.adapter = followAdapter
        val observer = Observer<List<Product>> { newValue ->
            if (newValue.isNotEmpty()) {
                binding.emptyText.visibility = View.GONE
                binding.followBlock.visibility = View.VISIBLE
            } else {
                binding.emptyText.visibility = View.VISIBLE
                binding.followBlock.visibility = View.GONE
            }
            followAdapter.submitList(newValue)
        }
        viewModel.liveData.observe(viewLifecycleOwner, observer)
    }

    private fun initClearButton() {
        val context = requireContext()
        binding.clear.setOnClickListener {
            val dialogBinding = DialogAcceptBinding.inflate(LayoutInflater.from(context))
            val dialog = DialogUtils.createCrudDialog(context, dialogBinding)
            dialogBinding.title.text = getString(R.string.clear_follows)
            dialogBinding.cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialogBinding.accept.setOnClickListener {
                viewModel.dislikeAllProduct()
                dialog.dismiss()
            }
        }
    }

    override fun onClick(product: Product) {
        viewModel.dislikeOneProduct(product)
    }
}