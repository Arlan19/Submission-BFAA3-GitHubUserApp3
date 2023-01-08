package com.arlanallacsta.githubuserapp3

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arlanallacsta.githubuserapp3.database.FavoriteUser
import com.arlanallacsta.githubuserapp3.database.FavoriteUserDao
import com.arlanallacsta.githubuserapp3.database.FavoriteUserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {

    val detailUser =  MutableLiveData<DetailUserResponse>()

    private var favoriteUserDao: FavoriteUserDao?
    private var favoriteDatabase: FavoriteUserRoomDatabase?

    init {
        favoriteDatabase = FavoriteUserRoomDatabase.getDatabase(application)
        favoriteUserDao = favoriteDatabase?.favoriteUserDao()
    }

    fun setUserDetail(username: String){
        RetrofitObject.apiInstance.getUsersDetail(username).enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    detailUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("Failure", t.message!!)
            }

        })
    }

    fun getUserDetail() : LiveData<DetailUserResponse>{
        return detailUser
    }

    fun addUserToFav(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var favUser = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            favoriteUserDao?.addUserToFav(favUser)
        }
    }

    suspend fun checkUser(id: Int) = favoriteUserDao?.checkUser(id)

    fun removeUserFromFav(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao?.removeUserFromFav(id)
        }
    }
}