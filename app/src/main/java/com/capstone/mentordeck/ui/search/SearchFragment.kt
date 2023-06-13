package com.capstone.mentordeck.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.mentordeck.adapter.MentorListHomeAdapter
import com.capstone.mentordeck.databinding.FragmentHomeBinding
import com.capstone.mentordeck.databinding.FragmentSearchBinding
import com.capstone.mentordeck.dummy.MentorModel
import com.capstone.mentordeck.ui.home.HomeViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}