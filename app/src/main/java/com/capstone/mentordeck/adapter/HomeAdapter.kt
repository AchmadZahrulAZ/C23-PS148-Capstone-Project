package com.capstone.mentordeck.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mentordeck.data.model.StoryModel
import com.capstone.mentordeck.databinding.ListMentorHomeBinding
import com.capstone.mentordeck.utils.loadImage
import com.capstone.mentordeck.utils.setDateFormat
import java.util.*

class HomeAdapter(private val itemList: ArrayList<StoryModel>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(var binding: ListMentorHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMentorHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mentor = itemList[position]

        holder.binding.ivPhoto.loadImage(mentor.photoUrl)
        holder.binding.title.text = mentor.description
        holder.binding.price.setDateFormat(mentor.createdAt)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(itemList[holder.absoluteAdapterPosition])
        }


//        holder.itemView.setOnClickListener {
//            val intent = Intent(, ItemDetailActivity::class.java)
//            intent.putExtra(EXTRA_DATA, mentor)
//            itemView.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int = itemList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: StoryModel)
    }

}
