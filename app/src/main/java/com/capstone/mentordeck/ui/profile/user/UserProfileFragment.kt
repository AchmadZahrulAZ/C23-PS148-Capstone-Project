package com.capstone.mentordeck.ui.profile.user


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.capstone.mentordeck.R
import com.capstone.mentordeck.databinding.FragmentUserProfileBinding
import com.capstone.mentordeck.utils.loadImageCircleCrop
import com.capstone.mentordeck.utils.loadImageCircleCropDummy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding?.ivPhoto?.loadImageCircleCropDummy(R.drawable.furimuitehohoemu)

        binding?.btnEdit?.setOnClickListener {
            NavHostFragment
                .findNavController(this@UserProfileFragment)
                .navigate(R.id.action_userProfileFragment_to_editUserProfileFragment)
        }
    }

}