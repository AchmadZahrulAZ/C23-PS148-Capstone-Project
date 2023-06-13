package com.capstone.mentordeck.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.capstone.mentordeck.databinding.ListMentorHomeBinding
import com.capstone.mentordeck.dummy.MentorModel

class MentorListHomeAdapter(private val mentorList: List<MentorModel>) : RecyclerView.Adapter<MentorListHomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListMentorHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = mentorList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(mentorList[position])
    }

    inner class ViewHolder(var binding: ListMentorHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(mentor: MentorModel) {

            Glide.with(itemView.context)
                .load(mentor.image)
                .into(binding.ivPhoto)

            binding.title.text = mentor.subject
            binding.price.text = mentor.price
            binding.location.text = mentor.location
            binding.rating.text = mentor.rate
            binding.reviewTotal.text = mentor.review

//           itemView.setOnClickListener {
//               val intent = Intent(itemView.context, DetailLN::class.java)
//               intent.putExtra(DetailLN.EXTRA_LIGHT_NOVEL, l)
//               itemView.context.startActivity(intent)
//           }

        }
    }
}
