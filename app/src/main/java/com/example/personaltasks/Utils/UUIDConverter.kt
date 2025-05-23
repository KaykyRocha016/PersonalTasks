package com.example.personaltasks.Utils

import androidx.room.TypeConverters
import java.util.UUID

class UUIDConverter {
    @TypeConverters
    fun fromUUID(uuid: UUID?):String? = uuid?.toString()
    @TypeConverters
    fun toUUID(string:String?):UUID? = UUID.fromString(string)
}