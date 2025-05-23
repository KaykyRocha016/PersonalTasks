package com.example.personaltasks.Infrastructure

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.personaltasks.Utils.UUIDConverter
import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskDao

//classe que representa o database do room
@Database(entities = [Task::class], version = 1)
@TypeConverters(UUIDConverter::class)
abstract class TasksDatabase:RoomDatabase() {
    abstract fun taskDao(): TaskDao
}