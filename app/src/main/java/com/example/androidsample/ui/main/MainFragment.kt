package com.example.androidsample.ui.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsample.R
import com.example.androidsample.databinding.FragmentMainBinding
import com.example.androidsample.util.DrawerController
import com.example.androidsample.util.OnItemClickListener
import com.example.androidsample.util.livedata.EventObserver
import org.jetbrains.anko.design.snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = viewModel
        (activity as DrawerController).setDrawerEnable(true)

        val adapter = UserAdapter(object : OnItemClickListener {
            override fun onItemClick(view: View, id: Long)
                    = Navigation.findNavController(view).navigate(MainFragmentDirections.actionShowDetail(id))
        })

        binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewModel.loading.observe(this, Observer { refreshing -> binding.swipeRefreshLayout.isRefreshing = refreshing == true })
        viewModel.errorEvent.observe(this, EventObserver { code ->
            when (code) {
                MainViewModel.ERROR_API ->
                    binding.swipeRefreshLayout.snackbar(R.string.api_error, R.string.retry) { viewModel.reloadUsers() }
            }
        })

        viewModel.getUsers(this).observe(this, Observer { users ->
            adapter.submitList(users)
        })

        binding.buttonGoSettings.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_go_to_settings)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.reloadUsers()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
    }

}
