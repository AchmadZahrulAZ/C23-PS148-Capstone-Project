package com.capstone.mentordeck.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mentordeck.R
import com.capstone.mentordeck.adapter.HomeAdapter
import com.capstone.mentordeck.data.model.StoryModel
import com.capstone.mentordeck.databinding.FragmentHomeBinding
import com.capstone.mentordeck.ui.detail.detail.ItemDetailActivity
import com.capstone.mentordeck.ui.profile.user.UserProfileActivity
import com.capstone.mentordeck.ui.search.SearchActivity
import com.capstone.mentordeck.utils.loadImageCircleCropDummy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()

        (activity as AppCompatActivity).supportActionBar?.hide()

        viewModel.storyList.observe(this) { storyList ->
            if (storyList != null) {
                setUpRecyclerData(storyList)
            }
        }

        binding?.searchView?.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }

        binding?.btnFavorite?.setOnClickListener {
            startActivity(Intent(requireContext(), ItemDetailActivity::class.java))
        }

        binding?.btnProfile?.setOnClickListener {
            startActivity(Intent(requireContext(), UserProfileActivity::class.java))
        }

        binding?.btnProfile?.loadImageCircleCropDummy(R.drawable.furimuitehohoemu)
    }

    private fun setUpRecyclerData(mentor: List<StoryModel>) {
        val mentorList = ArrayList<StoryModel>()
        for (m in mentor) {
            mentorList.clear()
            mentorList.addAll(mentor)
        }

        binding?.rvTopRatedList?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvRecommendationList?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val homeAdapter = HomeAdapter(mentorList)
        binding?.rvTopRatedList?.adapter = homeAdapter

        binding?.rvRecommendationList?.adapter = homeAdapter
    }

    private fun setViewModel() {
        viewModel.getUser().observe(this) { user ->
            viewModel.getAllStories(user.token)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

