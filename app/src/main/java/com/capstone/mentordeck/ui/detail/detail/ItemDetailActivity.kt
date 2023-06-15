package com.capstone.mentordeck.ui.detail.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.mentordeck.R
import com.capstone.mentordeck.databinding.ActivityItemDetailBinding
import com.capstone.mentordeck.utils.loadImageCircleCropDummy
import com.capstone.mentordeck.utils.loadImageDummy
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

        supportActionBar?.hide()

        binding?.ivPhoto?.loadImageDummy(R.drawable.furimuitehohoemu)
        binding?.profile?.loadImageCircleCropDummy(R.drawable.furimuitehohoemu)

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