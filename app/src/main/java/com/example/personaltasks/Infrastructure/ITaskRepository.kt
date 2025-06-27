package com.example.personaltasks.Infrastructure

import androidx.lifecycle.LiveData
import com.example.personaltasks.model.Task

interface ITaskRepository {
    /** Observa em tempo real todas as tarefas com o filtro deleted */
    fun getTasks(deleted: Boolean = false): LiveData<List<Task>>

    /** Busca uma única tarefa por ID */
    suspend fun getTask(id: String): Task?

    /** Cria uma nova tarefa (id gerado pelo Firestore) */
    suspend fun createTask(task: Task)

    /** Atualiza todos os campos de uma tarefa existente */
    suspend fun updateTask(task: Task)

    /** Marca a tarefa como ‘deleted = true’ */
    suspend fun deleteTask(task: Task)

    /** Reverte a exclusão (deleted = false) */
    suspend fun reactivateTask(task: Task)
}