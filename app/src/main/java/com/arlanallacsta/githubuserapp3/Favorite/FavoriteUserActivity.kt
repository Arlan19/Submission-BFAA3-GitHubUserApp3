package com.arlanallacsta.githubuserapp3.Favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_AVATAR_URL
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_ID
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_USERNAME
import com.arlanallacsta.githubuserapp3.DetailUserActivity
import com.arlanallacsta.githubuserapp3.ItemsItem
import com.arlanallacsta.githubuserapp3.UsersAdapter
import com.arlanallacsta.githubuserapp3.database.FavoriteUser
import com.arlanallacsta.githubuserapp3.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: UsersAdapter
    private lateinit var viewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, data.login)
                intent.putExtra(EXTRA_ID, data.id)
                intent.putExtra(EXTRA_AVATAR_URL, data.avatarUrl)
                startActivity(intent)
            }
        })

        binding.rvUserFavorite.setHasFixedSize(true)
        binding.rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
        binding.rvUserFavorite.adapter = adapter

        viewModel.getFavoriteUser()?.observe(this,{
            if (it != null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<ItemsItem>{
        val listUsers = ArrayList<ItemsItem>()
        for (user in users){
            val userMapped = ItemsItem(
                user.login,
                user.id,
                user.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}