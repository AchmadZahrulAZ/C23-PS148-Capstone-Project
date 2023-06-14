package com.capstone.mentordeck.adapter

import android.content.Intent
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mentordeck.data.test.StoryEntity
import com.capstone.mentordeck.databinding.ListMentorHomeBinding
import com.capstone.mentordeck.ui.detail.ItemDetailActivity
import com.capstone.mentordeck.ui.detail.ItemDetailActivity.Companion.EXTRA_DATA
import com.capstone.mentordeck.utils.loadImage
import com.capstone.mentordeck.utils.setDateFormat
import java.io.IOException
import java.util.*

class HomeAdapter: PagingDataAdapter<StoryEntity, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private var binding: ListMentorHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mentor: StoryEntity) {

            binding.ivPhoto.loadImage(mentor.photoUrl)
            binding.title.text = mentor.description
            binding.price.setDateFormat(mentor.createdAt)

//            try {
//                if (mentor.lat != null && mentor.lon != null && mentor.lat >= -90 && mentor.lat <= 90 && mentor.lon >= -180 && mentor.lon <= 180) {
//                    val geocoder = Geocoder(itemView.context, Locale.getDefault())
//                    val list = geocoder.getFromLocation(mentor.lat, mentor.lon, 1)
//                    binding.rating.text = mentor.lat.toString()
//                    binding.reviewTotal.text = mentor.lon.toString()
//
//                    if ((list != null) && (list.size != 0)) {
//                        binding.location.text = list[0].locality
//
//
//                    }
//                }
//            } catch (exception: IOException) {
//                exception.printStackTrace()
//            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ItemDetailActivity::class.java)
                intent.putExtra(EXTRA_DATA, mentor)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMentorHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
