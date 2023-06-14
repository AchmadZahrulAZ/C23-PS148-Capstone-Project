package com.capstone.mentordeck.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.capstone.mentordeck.R
import com.capstone.mentordeck.adapter.FeedAdapter
import com.capstone.mentordeck.adapter.LoadingStateAdapter
import com.capstone.mentordeck.databinding.FragmentFeedBinding
import com.capstone.mentordeck.utils.loadImageCircleCropDummy
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment(), CardStackListener {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<FeedViewModel>()

    private val manager by lazy { CardStackLayoutManager(requireContext(), this) }

    private val cardStackView by lazy {
        binding?.cardStackView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
        setupCardStackView()

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding?.btnProfile?.setOnClickListener {
            NavHostFragment
                .findNavController(this@FeedFragment)
                .navigate(R.id.action_navigation_feed_to_userProfileFragment)
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
        binding?.cardStackView?.layoutManager = CardStackLayoutManager(requireContext())

        val storyAdapter = FeedAdapter()
        binding?.cardStackView?.adapter = storyAdapter.withLoadStateHeaderAndFooter(
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

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {

    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
//        val textView = view.findViewById<TextView>(R.id.item_name) ${textView.text}
        Log.d("CardStackView", "onCardAppeared: ($position) ")
    }

    override fun onCardDisappeared(view: View, position: Int) {
//        val textView = view.findViewById<TextView>(R.id.item_name) ${textView.text}
        Log.d("CardStackView", "onCardDisappeared: ($position) ")
    }

    private fun setupCardStackView() {
        initialize()
    }



    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView?.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}