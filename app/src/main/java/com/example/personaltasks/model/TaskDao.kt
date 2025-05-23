package com.example.personaltasks.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.UUID
//Dao do Room
@Dao
interface TaskDao {
    //retorna todas as tasks do banco
    @Query("SELECT * FROM TASK")
    fun getTasks(): MutableList<Task>

//busca uma task pelo id
    @Query("SELECT * FROM TASK WHERE id  = :id")
    fun getTaskByID(id: UUID): Task?
//insere uma task
    @Insert
    fun insertTask(task: Task)
//atualiza uma task
    @Update
    fun updateTask(task:Task)
//deleta uma task
    @Delete
    fun deleteTask(task: Task)


}
