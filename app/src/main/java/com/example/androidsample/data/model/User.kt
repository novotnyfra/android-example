package com.example.androidsample.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.recyclerview.widget.DiffUtil

@Entity(tableName = "users")
class User {
    @PrimaryKey
    var id = -1L

    var name = ""
    var email = ""
    var city = ""
    var avatar = ""

    var skills = mutableListOf<String>()

    // field to help keep data fresh
    var lastFetched = 0L    // in sec

    fun isFresh(freshTimeout: Long)
            = System.currentTimeMillis() / 1000 - lastFetched <= freshTimeout

    // for RecyclerView ListAdapter
    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean
                = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean
                = oldItem.equals(newItem)
    }
}
