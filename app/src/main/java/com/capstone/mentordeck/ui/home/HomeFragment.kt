package com.capstone.mentordeck.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mentordeck.R
import com.capstone.mentordeck.adapter.MentorListHomeAdapter
import com.capstone.mentordeck.databinding.FragmentHomeBinding
import com.capstone.mentordeck.dummy.DummyData
import com.capstone.mentordeck.dummy.MentorModel
import com.capstone.mentordeck.ui.detail.ServiceDetailActivity
import dagger.hilt.android.AndroidEntryPoint

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private var list: ArrayList<MentorModel> = arrayListOf()

//    private var viewModel: HomeViewModel by viewModels()

    private lateinit var viewModel: HomeViewModel

    private lateinit var adapter: MentorListHomeAdapter

//    private var job: Job = Job()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setViewModel()
//        setRecyclerView()

        (activity as AppCompatActivity).supportActionBar?.hide()

        with(binding) {
            this?.btnFavorite?.setOnClickListener {
                startActivity(Intent(requireActivity(), ServiceDetailActivity::class.java))
            }
            this?.homeSearch?.setOnClickListener {
                NavHostFragment
                    .findNavController(this@HomeFragment)
                    .navigate(R.id.action_home_to_search)
            }

        }

        list.addAll(DummyData.listData)
        binding?.rvTopRatedList?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvRecommendationList?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val adapter = MentorListHomeAdapter(list)
        binding?.rvTopRatedList?.adapter = adapter
        binding?.rvRecommendationList?.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}