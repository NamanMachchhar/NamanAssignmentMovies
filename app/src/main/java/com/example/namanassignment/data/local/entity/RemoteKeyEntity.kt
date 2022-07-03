package com.example.namanassignment.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "title", collate = ColumnInfo.NOCASE)
    val title: String,
    @ColumnInfo(name = "next_page")
    val nextPage: Int?
)