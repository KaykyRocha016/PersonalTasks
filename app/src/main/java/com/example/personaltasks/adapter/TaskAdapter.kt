package com.example.personaltasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.databinding.TaskBinding
import com.example.personaltasks.model.IOnTaskInteractionListener
import com.example.personaltasks.model.Task

//Classe Adapter para a Task
class TaskAdapter(
    private val taskList: MutableList<Task>,
    private val listener: IOnTaskInteractionListener
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
//commit de teste da sala
    // Vincula os dados da task aos elementos visuais do layout
    inner class TaskViewHolder(private val taskBinding: TaskBinding) :
        RecyclerView.ViewHolder(taskBinding.root) {

        fun bind(task: Task) {
            taskBinding.titleTv.text = task.title
            taskBinding.descriptionTv.text = task.description
            taskBinding.deadlineTv.text = "Data limite: ${task.deadline}"
            taskBinding.doneCk.isChecked = task.checked
            taskBinding.doneCk.setOnClickListener {
                listener.onDoneClickListener(task,it)
            }
            taskBinding.root.setOnLongClickListener {
                listener.onTaskLongClick(task, it)
                true
            }
        }
    }
    // Cria um novo ViewHolder, inflando o layout do item Task

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskBinding = TaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(taskBinding)
    }
    // Associa os dados da posição 'position' da lista ao ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size
}