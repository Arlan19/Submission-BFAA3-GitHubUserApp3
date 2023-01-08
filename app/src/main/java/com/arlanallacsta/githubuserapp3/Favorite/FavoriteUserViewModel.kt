package com.arlanallacsta.githubuserapp3.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arlanallacsta.githubuserapp3.database.FavoriteUser
import com.arlanallacsta.githubuserapp3.database.FavoriteUserDao
import com.arlanallacsta.githubuserapp3.database.FavoriteUserRoomDatabase

class FavoriteUserViewModel(application: Application): AndroidViewModel(application) {

    private var favoriteUserDao: FavoriteUserDao?
    private var favoriteDatabase: FavoriteUserRoomDatabase?

    init {
        favoriteDatabase = FavoriteUserRoomDatabase.getDatabase(application)
        favoriteUserDao = favoriteDatabase?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return favoriteUserDao?.getFavUser()
    }
}