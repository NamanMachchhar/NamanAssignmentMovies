package com.example.namanassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.namanassignment.data.local.dao.MovieDao
import com.example.namanassignment.data.local.dao.RemoteKeyDao
import com.example.namanassignment.data.local.entity.MovieEntity
import com.example.namanassignment.data.local.entity.RemoteKeyEntity

@Database(
    entities = [MovieEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
