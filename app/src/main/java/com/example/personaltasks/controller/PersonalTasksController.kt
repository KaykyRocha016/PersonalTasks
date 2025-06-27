package com.example.personaltasks.controller

import androidx.lifecycle.LiveData
import com.example.personaltasks.Infrastructure.ITaskRepository
import com.example.personaltasks.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class PersonalTasksController(private val firebaseRepository: ITaskRepository) {

    //insere uma task no banco
    suspend fun insertTask(task : Task) = withContext(Dispatchers.IO){
        firebaseRepository.createTask(task)

    }
    fun getAll(deleted: Boolean = false): LiveData<List<Task>> {
        // retorna imediatamente o LiveData que já vai receber updates do Firestore
        return firebaseRepository.getTasks(deleted)
    }

    //busca uma task no banco pelo id
    suspend fun getTask(id: String) : Task? = withContext(Dispatchers.IO){
        return@withContext firebaseRepository.getTask(id)
    }

    //apaga uma task do banco
    suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO){
        firebaseRepository.deleteTask(task)


    }

    //atualiza uma task
    suspend fun updateTask(task: Task) = withContext(Dispatchers.IO){
        firebaseRepository.updateTask(task)
    }
    suspend fun reactivateTask(task: Task) {
        // Atualiza a tarefa no Firebase, definindo 'deleted' como false e 'deletedAt' como null
        firebaseRepository.reactivateTask(task)
    }
}