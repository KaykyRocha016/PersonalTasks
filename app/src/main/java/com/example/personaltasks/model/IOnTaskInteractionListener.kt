package com.example.personaltasks.model

import android.view.View

//interface que define o listener de clique longo
interface IOnTaskInteractionListener {
    fun onTaskLongClick(task:Task, view : View)
    fun onDoneClickListener(task: Task, view: View)

}