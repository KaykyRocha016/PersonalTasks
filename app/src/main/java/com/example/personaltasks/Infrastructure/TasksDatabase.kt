package com.example.personaltasks.Infrastructure

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskDao

@Database(entities = [Task::class], version = 1)
@TypeConverters(TypeConverters::class)
abstract class TasksDatabase:RoomDatabase() {
    abstract fun TaskDao(): TaskDao
}