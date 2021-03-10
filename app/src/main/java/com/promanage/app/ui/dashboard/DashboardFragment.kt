package com.promanage.app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promanage.app.ProManageApplication
import com.promanage.app.R
import com.promanage.app.adapters.ProjectsListAdapter

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(
                this,
                DashboardViewModelFactory(repository = (activity?.application as ProManageApplication).repository)
            ).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ProjectsListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        dashboardViewModel.allProjects.observe(viewLifecycleOwner) { projects ->
            projects.let { adapter.submitList(it) }
        }
        return root
    }
}