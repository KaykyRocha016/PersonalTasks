package com.example.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = "task")
data class Task (@PrimaryKey val id: UUID?, val title: String, val description: String, val deadline : String) :
    Parcelable {


}