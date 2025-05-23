package com.example.personaltasks.model

import android.view.View

interface IOnTaskInteractionListener {
    fun onTaskLongClick(task:Task, view : View)

}