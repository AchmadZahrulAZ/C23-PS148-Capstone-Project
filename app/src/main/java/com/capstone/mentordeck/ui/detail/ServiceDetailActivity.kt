package com.capstone.mentordeck.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capstone.mentordeck.R
import com.capstone.mentordeck.databinding.ActivityServiceDetailBinding
import com.ms.square.android.expandabletextview.ExpandableTextView





class ServiceDetailActivity : AppCompatActivity() {

    private var _binding: ActivityServiceDetailBinding? = null
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityServiceDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // getting reference of  ExpandableTextView
        // getting reference of  ExpandableTextView
        val expTv = binding?.expandableTv as ExpandableTextView

        // calling setText on the ExpandableTextView so that
        // text content will be  displayed to the user

        // calling setText on the ExpandableTextView so that
        // text content will be  displayed to the user
        expTv.text = getString(R.string.detail_description_text)
    }
}