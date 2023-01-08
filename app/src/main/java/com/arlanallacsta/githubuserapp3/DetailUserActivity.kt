package com.arlanallacsta.githubuserapp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_AVATAR_URL
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_ID
import com.arlanallacsta.githubuserapp3.ConstValue.EXTRA_USERNAME
import com.arlanallacsta.githubuserapp3.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.tvDetailName.text = it.name
                binding.tvDetailUsername.text = it.login
                binding.tvDetailFollowers.text = it.followers.toString()
                binding.tvDetailFollowing.text = it.following.toString()
                binding.tvDetailRepo.text = it.publicRepos.toString()
                Glide.with(this@DetailUserActivity).load(it.avatarUrl).circleCrop().into(binding.imgDetalUser)

            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.tbFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        binding.tbFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.tbFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.addUserToFav(username, id, avatarUrl.toString())
            }else{
                viewModel.removeUserFromFav(id)
            }
            binding.tbFavorite.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this,supportFragmentManager, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }


}