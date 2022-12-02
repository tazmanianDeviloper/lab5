package com.example.lab4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.lab4.R
import com.example.lab4.viewmodel.CurProjectViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class detailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editProj = view.findViewById<ImageButton>(R.id.editProj)
        val backButton= view.findViewById<Button>(R.id.back)
        val projTitle = view.findViewById<TextView>(R.id.projTitle)
        val projDesc =  view.findViewById<TextView>(R.id.projDesc)

        val viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)

        // Create an observer for the curProject livedata.
        // Whenever its value changes, it is notified.
        // The project title and description will be loaded into textviews.
        viewModel.curProject.observe(viewLifecycleOwner, Observer {
            projTitle.text =  it?.title?:""
            projDesc.text = it?.description?:""
        })

        editProj.setOnClickListener {
            view.findNavController().navigate(R.id.action_detailFragment_to_editFragment2)
        }
        backButton.setOnClickListener(){
            view.findNavController().navigate(R.id.action_detailFragment_to_recyclerFragment)
        }
    }
}