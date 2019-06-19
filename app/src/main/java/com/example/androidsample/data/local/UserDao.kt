package com.example.androidsample.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidsample.data.model.User

@Dao
abstract class UserDao {

    @Query("SELECT * FROM users")
    abstract fun getAll() : LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id LIKE :userId LIMIT 1")
    abstract fun getById(userId: Long) : LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun _insertOrUpdate(user: User)

    /**
     * insert or update with auto timestamp
     */
    fun insertOrUpdate(user: User) {
        user.lastFetched = System.currentTimeMillis() / 1000
        _insertOrUpdate(user)
    }

    fun replaceAll(users: List<User>) {
        deleteAll()
        insertOrUpdateAll(users)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun _insertOrUpdateAll(users: List<User>)

    /**
     * insert or uprate with auto timestamp
     */
    fun insertOrUpdateAll(users: List<User>) {
        val currentTime = System.currentTimeMillis() / 1000
        users.forEach { it.lastFetched = currentTime }
        _insertOrUpdateAll(users)
    }

    @Query("DELETE FROM users")
    abstract fun deleteAll()

}
