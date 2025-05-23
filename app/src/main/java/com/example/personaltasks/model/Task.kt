package com.example.personaltasks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Task (val id: UUID?, val title: String, val description: String, val deadline : String) :
    Parcelable {


}