package com.example.personaltasks.controller

import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class PersonalTasksController(private val taskDao: TaskDao) {

    suspend fun insertTask(task : Task) = withContext(Dispatchers.IO){
        taskDao.insertTask(task)

    }
    suspend fun getAll() : List<Task> = withContext(Dispatchers.IO){

        return@withContext taskDao.getTasks()
    }
    suspend fun getTask(id:UUID) : Task? = withContext(Dispatchers.IO){
        return@withContext taskDao.getTaskByID(id)
    }
    suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO){
        taskDao.deleteTask(task)


    }
    suspend fun updateTask(task: Task) = withContext(Dispatchers.IO){
        taskDao.updateTask(task)
    }
}