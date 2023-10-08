package com.example.fake_shop.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fake_shop.R
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.databinding.FollowProductBinding
import com.example.fake_shop.listeners.FollowListener
import com.squareup.picasso.Picasso

class FollowAdapter(private val listener: FollowListener) :
    ListAdapter<Product, RecyclerView.ViewHolder>(MyDiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return R.id.navigation_follows
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.id.navigation_follows -> {
                val binding = FollowProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                FollowHolder(binding)
            }
            else -> throw IllegalStateException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.id.navigation_follows -> (holder as FollowHolder).bind(getItem(position))
            else -> throw IllegalStateException("Unknown item view type ${holder.itemViewType}")
        }
    }

    inner class FollowHolder(
        private val binding: FollowProductBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            itemView.setOnClickListener {
                listener.onClick(product)
            }
            name.text = product.title
            count.text = product.count.toString()
            heart.setImageResource(
                if (product.isLike) {
                    R.drawable.heart_fill
                } else {
                    R.drawable.heart
                }
            )
            heart.setOnClickListener {
                listener.onClick(product)
            }
            try {
                Picasso.get().load(product.image)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.broken_image)
                    .into(image)
            } catch (ex: Exception) {
                Log.e("Error", ex.message.toString())
                ex.printStackTrace()
            }
        }
    }

    class MyDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.isLike == newItem.isLike
        }
    }
}