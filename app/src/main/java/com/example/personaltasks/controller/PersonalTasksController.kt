package com.example.personaltasks.controller

import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class PersonalTasksController(private val taskDao: TaskDao) {

    //insere uma task no banco
    suspend fun insertTask(task : Task) = withContext(Dispatchers.IO){
        taskDao.insertTask(task)

    }
    //retorna uma lista com todas as tasks do banco
    suspend fun getAll() : List<Task> = withContext(Dispatchers.IO){

        return@withContext taskDao.getTasks()
    }

    //busca uma task no banco pelo id
    suspend fun getTask(id:UUID) : Task? = withContext(Dispatchers.IO){
        return@withContext taskDao.getTaskByID(id)
    }

    //apaga uma task do banco
    suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO){
        taskDao.deleteTask(task)


    }

    //atualiza uma task
    suspend fun updateTask(task: Task) = withContext(Dispatchers.IO){
        taskDao.updateTask(task)
    }
}