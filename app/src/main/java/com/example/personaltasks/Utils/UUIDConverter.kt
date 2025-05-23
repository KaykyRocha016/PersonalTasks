package com.example.personaltasks.Utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.UUID

class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID?):String? = uuid?.toString()
    @TypeConverter
    fun toUUID(string:String?):UUID? = UUID.fromString(string)
}