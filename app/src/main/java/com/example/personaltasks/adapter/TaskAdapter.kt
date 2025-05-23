package com.example.personaltasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.databinding.TaskBinding
import com.example.personaltasks.model.IOnTaskInteractionListener
import com.example.personaltasks.model.Task


class TaskAdapter(
    private val taskList: MutableList<Task>,
    private val listener: IOnTaskInteractionListener
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val taskBinding: TaskBinding) :
        RecyclerView.ViewHolder(taskBinding.root) {

        fun bind(task: Task) {
            taskBinding.titleTv.text = task.title
            taskBinding.descriptionTv.text = task.description
            taskBinding.deadlineTv.text = "Data limite: ${task.deadline}"

            taskBinding.root.setOnLongClickListener {
                listener.onTaskLongClick(task, it)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskBinding = TaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(taskBinding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size
}