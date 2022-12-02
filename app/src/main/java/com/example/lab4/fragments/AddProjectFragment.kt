package com.example.lab4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.lab4.R
import com.example.lab4.databinding.FragmentEditBinding
import com.example.lab4.datalayer.Project
import com.example.lab4.viewmodel.CurProjectViewModel
import com.example.lab4.viewmodel.ProjectListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProjectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProjectFragment : Fragment() {
    // use ViewBinding
    private var _binding: FragmentEditBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listViewModel: ProjectListViewModel
    private lateinit var viewModel: CurProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use ViewBinding
        // FragmentDetailBinding is a generated binding class mapped to fragment_detail.xml
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        listViewModel =
            ViewModelProvider(requireActivity()).get(ProjectListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)

        binding.submit.setOnClickListener {
            val project = Project(
                0, binding.projTitleEdit.text.toString(),
                binding.projDescEdit.text.toString(), false, "", 0
            )
            listViewModel.addProject(project)
            viewModel.setCurProject(project)
            it.findNavController().navigate(R.id.action_addProjectFragment_to_recyclerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}