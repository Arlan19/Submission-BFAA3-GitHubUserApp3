package com.arlanallacsta.githubuserapp3


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_AVATAR_URL
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_ID
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_USERNAME
import com.arlanallacsta.githubuserapp3.Favorite.FavoriteUserActivity
import com.arlanallacsta.githubuserapp3.databinding.ActivityMainBinding
import com.arlanallacsta.githubuserapp3.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, data.login)
                intent.putExtra(EXTRA_ID, data.id)
                intent.putExtra(EXTRA_AVATAR_URL, data.avatarUrl)
                startActivity(intent)
            }

        })

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)


        val layoutManager = LinearLayoutManager(this@MainActivity)

        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter


        binding.inputText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUsers()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun searchUsers() {
        val queryInput = binding.inputText.text.toString()
        if (queryInput.isEmpty()) return showLoading(true)
        viewModel.setSerachUsers(queryInput)
    }

    private fun showLoading(b: Boolean) { binding.progressBar.visibility = if (b) View.VISIBLE else View.GONE }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_change_language -> {
                val changeLanguage = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(changeLanguage)
                return true
            }
            R.id.menu_favorite -> {
                val favorite = Intent(this, FavoriteUserActivity::class.java)
                startActivity(favorite)
                return true
            }
            R.id.menu_setting -> {
                val setting = Intent(this, SettingActivity::class.java)
                startActivity(setting)
                return true
            }
            else -> {
                return true
            }
        }
        return true
    }
}