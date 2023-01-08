package com.arlanallacsta.githubuserapp3.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "fav_user")
@Parcelize
data class FavoriteUser(

    @ColumnInfo(name = "login")
    val login: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "avatarUrl")
    val avatarUrl : String

) : Parcelable
