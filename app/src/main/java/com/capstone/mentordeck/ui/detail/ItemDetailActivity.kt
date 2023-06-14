package com.capstone.mentordeck.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.mentordeck.R
import com.capstone.mentordeck.databinding.ActivityItemDetailBinding
import com.ms.square.android.expandabletextview.ExpandableTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailActivity : AppCompatActivity() {

    private var _binding: ActivityItemDetailBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityItemDetailBinding.inflate(layoutInflater)
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

    companion object {
        const val EXTRA_DATA = "Extra_Data"
    }
}