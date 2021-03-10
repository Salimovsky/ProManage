package com.promanage.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promanage.app.R
import com.promanage.app.db.Project

class ProjectsListAdapter :
    ListAdapter<Project, ProjectsListAdapter.ProjectViewHolder>(PROJECTS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val projectNameView: TextView = itemView.findViewById(R.id.project_name)
        private val clientAndAddressView: TextView = itemView.findViewById(R.id.client_name_address)
        private val editView: View = itemView.findViewById(R.id.edit_view)
        private val acceptedView: TextView = itemView.findViewById(R.id.accepted_view)

        fun bind(project: Project) {
            projectNameView.text = project.projectName
            clientAndAddressView.text = "${project.clientName} | ${project.address}"
            editView.visibility = View.GONE
            acceptedView.visibility = View.GONE
            if (project.isAccepted) acceptedView.visibility = View.VISIBLE else editView.visibility = View.VISIBLE
        }

        companion object {
            fun create(parent: ViewGroup): ProjectViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_project, parent, false)
                return ProjectViewHolder(view)
            }
        }
    }

    companion object {
        private val PROJECTS_COMPARATOR = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem.projectId == newItem.projectId
            }
        }
    }
}
