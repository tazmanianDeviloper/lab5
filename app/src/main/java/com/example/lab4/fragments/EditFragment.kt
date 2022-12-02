package com.example.lab4.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.lab4.R
import com.example.lab4.databinding.FragmentEditBinding
import com.example.lab4.datalayer.Project
import com.example.lab4.datalayer.ProjectDao
import com.example.lab4.viewmodel.CurProjectViewModel
import com.example.lab4.viewmodel.ProjectListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var listViewModel: ProjectListViewModel
    private lateinit var viewModel: CurProjectViewModel

    private lateinit var projectAuthors: ArrayList<String>
    private lateinit var pd: ProjectDao

    //ProjectPortalApplication.ARG_COLUMN_COUNT
    private val position: Int = arguments?.getInt(pd.count().value.toString()) ?: 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel =
            ViewModelProvider(requireActivity()).get(ProjectListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)

        viewModel.curProject.observe(viewLifecycleOwner, Observer {
            binding.projTitleEdit.setText(it.title)
            binding.projDescEdit.setText(it.description)
        })

        binding.submit.setOnClickListener {
            viewModel.updateCurProject(
                binding.projTitleEdit.text.toString(),
                binding.projDescEdit.text.toString(),
                binding.collaborateInstance.text.toString(),
                binding.isFavorite.isChecked,
                1
            )


            view.findNavController().navigate(R.id.action_editFragment_to_detailFragment)

        }
        binding.cancel.setOnClickListener {
            view.findNavController().navigate(R.id.action_editFragment_to_detailFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onProjectClick(project: Project) {
        TODO("Not yet implemented")
    }
}