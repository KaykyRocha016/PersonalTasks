package com.example.personaltasks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task (val id: Int?, val title: String, val description: String, val deadline : String) :
    Parcelable {


}