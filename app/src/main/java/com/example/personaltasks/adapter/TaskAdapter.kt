package com.example.personaltasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.databinding.TaskBinding
import com.example.personaltasks.model.Task

class TaskAdapter(private val taskList: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: TaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.titleTv.text = task.title
            binding.descriptionTv.text = task.description
            binding.deadlineTv.text = "Data limite: ${task.deadline}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size
}