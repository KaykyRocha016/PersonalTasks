package com.example.personaltasks.Infrastructure

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class TasksDatabase:RoomDatabase() {
    abstract fun TaskDao(): TaskDao
}