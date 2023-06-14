package com.capstone.mentordeck.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mentordeck.R
import com.capstone.mentordeck.adapter.HomeAdapter
import com.capstone.mentordeck.adapter.LoadingStateAdapter
import com.capstone.mentordeck.databinding.FragmentHomeBinding
import com.capstone.mentordeck.ui.detail.ItemDetailActivity
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

        binding?.homeSearch?.setOnClickListener {
            NavHostFragment
                .findNavController(this@HomeFragment)
                .navigate(R.id.action_navigation_home_to_searchFragment)
        }

        binding?.btnProfile?.setOnClickListener {
            NavHostFragment
                .findNavController(this@HomeFragment)
                .navigate(R.id.action_navigation_home_to_userProfileFragment)
        }

        binding?.btnProfile?.loadImageCircleCropDummy(R.drawable.furimuitehohoemu)
    }

    private fun setViewModel() {
        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                getData(user.token)
            }
        }
    }

    private fun getData(token: String) {
        binding?.rvTopRatedList?.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvRecommendationList?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val storyAdapter = HomeAdapter()
        binding?.rvTopRatedList?.adapter = storyAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter {
                storyAdapter.retry()
            },

            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )

        binding?.rvRecommendationList?.adapter = storyAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter {
                storyAdapter.retry()
            },

            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )

        viewModel.getAllStories(token).observe(viewLifecycleOwner) {
            storyAdapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}