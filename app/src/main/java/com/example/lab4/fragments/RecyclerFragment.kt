package com.example.lab4.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab4.R
import com.example.lab4.adapter.MyProjListRecyclerViewAdapter
import com.example.lab4.databinding.FragmentRecyclerBinding
import com.example.lab4.datalayer.Project
import com.example.lab4.viewmodel.CurProjectViewModel
import com.example.lab4.viewmodel.ProjectListViewModel

/**
 * A fragment representing a list of Items.
 */
class RecyclerFragment : Fragment() {
    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1
    private var largeScreen = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        arguments?.let {
            largeScreen = it.getBoolean(ARG_LARGE_SCREEN)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentRecyclerBinding.inflate(inflater,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isFragAlone = (id== R.id.main)
        val viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)

        val listViewModel =
            ViewModelProvider(this).get(ProjectListViewModel::class.java)


        binding.recyclerView.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            val myAdapter = MyProjListRecyclerViewAdapter(
//                 (listViewModel.projectList?.value ?: emptyList()) ,
                object : MyProjListRecyclerViewAdapter.OnProjectClickListener {
                    override fun onProjectClick(project: Project) {
                        viewModel.setCurProject(project)
                        if (!largeScreen) {
                            view.findNavController()?.navigate(
                                R.id.action_recyclerFragment_to_detailFragment2
                            )
                        }

                    }

                })

            this.adapter = myAdapter

            listViewModel.projectList.observe(viewLifecycleOwner, Observer {
                myAdapter.replaceItems(it)
                viewModel.initCurProject(myAdapter.getProject(0))
            })

            viewModel.curProject.observe(viewLifecycleOwner, Observer {
                myAdapter.notifyDataSetChanged()
            })


        }
        binding.addProject.setOnClickListener {
            it.findNavController()?.navigate(
                R.id.action_recyclerFragment_to_addProjectFragment2
            )
        }

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_LARGE_SCREEN = "large-screen"


        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            RecyclerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}