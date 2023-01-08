package com.arlanallacsta.githubuserapp3


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arlanallacsta.githubuserapp3.databinding.ItemUsersBinding
import com.bumptech.glide.Glide

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val listUsers = ArrayList<ItemsItem>()

    private  var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


    fun setList(users: ArrayList<ItemsItem>) {
        listUsers.clear()
        listUsers.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun dataHolder(itemsItem: ItemsItem) {

            binding.tvNamaPengguna.setOnClickListener{
                onItemClickCallback?.onItemClicked(itemsItem)
            }

            binding.imgGambarUser.setOnClickListener{
                onItemClickCallback?.onItemClicked(itemsItem)
            }


            binding.tvNamaPengguna.text = itemsItem.login

            Glide.with(itemView.context)
                .load(itemsItem.avatarUrl)
                .circleCrop()
                .into(binding.imgGambarUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUsersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder((view))
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.dataHolder(listUsers[position])
    }

    override fun getItemCount() = listUsers.size

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }
}