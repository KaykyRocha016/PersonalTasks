package com.example.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

// Adicionando o construtor padrão para permitir a desserialização pelo Firestore
@Parcelize
@Entity(tableName = "task")
data class Task(
    @DocumentId
    var id: String = "",  // Definido como var e valor padrão
    var title: String = "",  // Valor padrão
    var description: String = "",  // Valor padrão
    var deadline: String = "",  // Valor padrão
    var completed: Boolean = false,  // Valor padrão
    var deletedAt: Timestamp? = null,  // Valor padrão
    var deleted: Boolean = false  // Valor padrão
) : Parcelable {
    // O Firestore vai usar esse construtor padrão para criar instâncias de Task
}
