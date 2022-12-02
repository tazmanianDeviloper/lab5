package com.example.lab4.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.R
import com.example.lab4.databinding.FragmentCardViewBinding
import com.example.lab4.datalayer.Project


class MyProjListRecyclerViewAdapter(
    private val onProjectClickListener: OnProjectClickListener
):
    RecyclerView.Adapter<MyProjListRecyclerViewAdapter.ViewHolder>() {

    private val projects: MutableList<Project> = mutableListOf<Project>()
    //changes data source content
    fun replaceItems(myProjects: List<Project>) {
        projects.clear()
        projects.addAll(myProjects)
        notifyDataSetChanged()
    }

    interface OnProjectClickListener {
        fun onProjectClick(project: Project);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val runnerProject = projects[position]

        holder.projIdNumber.text = runnerProject.id.toString()
        holder.projTitle.text=runnerProject.title
        holder.projCollaborate.text= runnerProject.collaborate
        val size = runnerProject.collaborate
        holder.numCollaborate.text=(size!!+1)

        holder.cardView.setOnClickListener{
//                val action = ProjRecycleViewFragmentDirections
//                    .actionProjListRecycleViewFragmentToDetailFragment(position)
//                it.findNavController().navigate(action)

            onProjectClickListener.onProjectClick(runnerProject)
//            it.findNavController()?.navigate(
//                R.id.action_projListRecycleViewFragment_to_nav_graph)
        }


        if (runnerProject.isFavorite)
        {
            holder.checkMark.setTextColor(Color.parseColor("#7FFF00"))
        }
        else
        {
            holder.checkMark.setTextColor(Color.parseColor("#DCDCDC"))
        }

    }

    fun getProject(pos: Int): Project {
        if (projects.size > 0)
            return projects[pos]
        else
            return Project(0,"","",false,"",0)
    }

    inner class ViewHolder(binding: FragmentCardViewBinding)
        : RecyclerView.ViewHolder(binding.root) {
        var projIdNumber:TextView = binding.projIdNumber
        var projTitle:TextView = itemView.findViewById(R.id.proj_title)
        var projCollaborate:TextView = itemView.findViewById(R.id.proj_collaborate)
        var numCollaborate:TextView = itemView.findViewById(R.id.num_collaborate)
        var checkMark:TextView = itemView.findViewById(R.id.check_mark)
        var cardView:CardView=itemView.findViewById(R.id.projectCard)

    }

    override fun getItemCount(): Int = projects.size

}

