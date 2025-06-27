package com.example.personaltasks.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.personaltasks.Infrastructure.ITaskRepository
import com.example.personaltasks.model.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FirebaseRepository : ITaskRepository {

    private val db = FirebaseFirestore.getInstance()
    private val uid get() = FirebaseAuth.getInstance().currentUser!!.uid

    private fun tasksColl() =
        db.collection("users")
            .document(uid)
            .collection("tasks")

    override fun getTasks(deleted: Boolean): LiveData<List<Task>> {
        val live = MutableLiveData<List<Task>>()
        tasksColl()
            .whereEqualTo("deleted", deleted)
            .orderBy("deadline", Query.Direction.ASCENDING)
            .addSnapshotListener { snap, e ->
                if (e != null) return@addSnapshotListener
                val list = snap
                    ?.toObjects(Task::class.java)
                    ?: emptyList()
                live.postValue(list)
            }
        return live
    }

    override suspend fun getTask(id: String): Task? {
        val doc = tasksColl().document(id).get().await()
        return doc.toObject(Task::class.java)
    }

    override suspend fun createTask(task: Task) {
        // id será gerado automaticamente
        tasksColl()
            .add(task.copy(id = ""))  // garante que o @DocumentId seja aplicado
            .await()
    }

    override suspend fun updateTask(task: Task) {
        tasksColl()
            .document(task.id)
            .set(task)
            .await()
    }

    override suspend fun deleteTask(task: Task) {
        val updated = task.copy(
            deleted = true,
            deletedAt = Timestamp.now()
        )
        tasksColl()
            .document(task.id)
            .set(updated)
            .await()
    }

    override suspend fun reactivateTask(task: Task) {
        val updated = task.copy(
            deleted = false,
            deletedAt = null
        )
        tasksColl()
            .document(task.id)
            .set(updated)
            .await()
    }
}
