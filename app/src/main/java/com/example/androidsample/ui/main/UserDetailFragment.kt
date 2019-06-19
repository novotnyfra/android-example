package com.example.androidsample.ui.main

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.androidsample.R
import com.example.androidsample.databinding.FragmentUserDetailBinding
import com.example.androidsample.util.DrawerController
import com.example.androidsample.util.livedata.EventObserver
import com.google.android.flexbox.FlexboxLayout
import org.jetbrains.anko.design.snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel
        (activity as DrawerController).setDrawerEnable(false)

        arguments?.let {
            viewModel.selectUser(this, UserDetailFragmentArgs.fromBundle(it).userId)
        }


        viewModel.loading.observe(this, Observer { refreshing -> binding.swipeRefreshLayout.isRefreshing = refreshing == true })
        viewModel.errorEvent.observe(this, EventObserver { code ->
            when (code) {
                MainViewModel.ERROR_API ->
                    binding.constraintLayout.snackbar(R.string.api_error, R.string.retry) { viewModel.reloadUsers() }
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.reloadUser()
        }

        addSkillTags()

    }

    private fun addSkillTags() {
        // add skills tags
        val params = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT)
        val dimen8dp = context?.resources?.getDimension(R.dimen.margin_8_dp)?.toInt() ?: 0
        params.setMargins(dimen8dp, dimen8dp, dimen8dp, 0)
        viewModel.selectedUser.observe(this, Observer { user ->
            binding.flexboxLayout.removeAllViews()
            user?.skills?.forEach { skillName ->
                val skillTextView = TextView(context).apply {
                    text = skillName
                    background = ContextCompat.getDrawable(context, R.drawable.shape_tag)
                    layoutParams = params
                }
                binding.flexboxLayout.addView(skillTextView)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
    }

}
