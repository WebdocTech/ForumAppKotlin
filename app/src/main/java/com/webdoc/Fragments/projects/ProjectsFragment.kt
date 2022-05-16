package com.webdoc.Fragments.projects

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.webdoc.Adapters.ProjectsAdapter
import com.webdoc.theforum.databinding.FragmentProjectsBinding


class ProjectsFragment : Fragment() {

    private lateinit var binding: FragmentProjectsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectsBinding.inflate(inflater, container, false)
        initViews()
        //clickListeners()
        return binding.root

    }

    private fun clickListeners() {

    }

    private fun initViews() {
        projectsPopulateRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Projects"
    }
    private fun projectsPopulateRecycler() {
        binding.rvProjects.setLayoutManager(GridLayoutManager(activity as AppCompatActivity, 1))
        val projectsAdapter = ProjectsAdapter(activity as AppCompatActivity)
        binding.rvProjects.setAdapter(projectsAdapter)
    }
}