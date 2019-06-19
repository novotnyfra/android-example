package com.example.androidsample.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsample.data.model.User
import com.example.androidsample.databinding.ItemUserBinding
import com.example.androidsample.util.OnItemClickListener

class UserAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<User, UserAdapter.ViewHolder>(User.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(getItem(holder.adapterPosition), onItemClickListener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = DataBindingUtil.bind<ItemUserBinding>(itemView)!!

        fun bind(user: User, onItemClickListener: OnItemClickListener) {
            itemView.setOnClickListener { onItemClickListener.onItemClick(itemView, user.id) }

            binding.avatarUrl = user.avatar
            binding.userName.text = user.name
            binding.city.text = user.city

        }
    }
}
