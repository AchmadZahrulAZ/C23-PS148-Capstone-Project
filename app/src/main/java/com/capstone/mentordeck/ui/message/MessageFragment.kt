package com.capstone.mentordeck.ui.message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.mentordeck.R
import com.capstone.mentordeck.databinding.FragmentMessageBinding
import com.capstone.mentordeck.ui.favorite.FavoriteActivity
import com.capstone.mentordeck.ui.profile.user.UserProfileActivity
import com.capstone.mentordeck.utils.loadImageCircleCropDummy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding?.btnFavorite?.setOnClickListener {
            startActivity(Intent(requireContext(), FavoriteActivity::class.java))
        }

        binding?.btnProfile?.setOnClickListener {
            startActivity(Intent(requireContext(), UserProfileActivity::class.java))
        }

        binding?.btnProfile?.loadImageCircleCropDummy(R.drawable.furimuitehohoemu)
    }

}