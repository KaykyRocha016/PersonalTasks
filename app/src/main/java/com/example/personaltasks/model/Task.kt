package com.example.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

//modelo de Task
@Parcelize
@Entity(tableName = "task")
data class Task (@DocumentId val id: String, val title: String, val description: String, val deadline : String, var completed: Boolean) :
    Parcelable {


}