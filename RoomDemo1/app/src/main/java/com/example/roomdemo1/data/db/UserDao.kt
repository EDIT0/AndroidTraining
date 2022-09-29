package com.example.roomdemo1.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roomdemo1.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User) : Long

    @Query("DELETE FROM User WHERE id = :id")
    suspend fun deleteUsingId(id: Int) : Int

    @Query("UPDATE User SET name = :newName WHERE id = :id")
    suspend fun updateUsingId(id: Int, newName: String) : Int

    @Query("SELECT * FROM User WHERE name = :name ORDER BY id DESC")
    fun selectUsingName(name: String): Flow<List<User>>

    @Query("SELECT * FROM User ORDER BY id DESC")
    fun getAllUser(): Flow<List<User>>

}