package com.example.personaltasks.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.UUID

interface TaskDao {
    @Query("SELECT * FROM TASK")
    fun getTasks(): MutableList<Task>

    @Query("SELECT * FROM TASK WHERE id  = :id")
    fun getTaskByID(id: UUID): Task?

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task:Task)

    @Delete
    fun deleteTask(task: Task)


}
