package com.example.personaltasks.ui

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TaskAdapter
import com.example.personaltasks.controller.PersonalTasksController
import com.example.personaltasks.data.FirebaseRepository
import com.example.personaltasks.databinding.ActivityDeletedTasksBinding
import com.example.personaltasks.model.IOnTaskInteractionListener

import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskFormMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeletedTasksActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()
    private lateinit var deletedTasksBinding: ActivityDeletedTasksBinding
    private var selectedTask: Task? = null
    private lateinit var controller: PersonalTasksController
    private val firebaseRepository = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deletedTasksBinding = ActivityDeletedTasksBinding.inflate(layoutInflater)
        setContentView(deletedTasksBinding.root)

        // Inicializa o controller com o repositório Firebase
        controller = PersonalTasksController(firebaseRepository = firebaseRepository)

        taskAdapter = TaskAdapter(tasks, object : IOnTaskInteractionListener {
            override fun onTaskLongClick(task: Task, view: View) {
                selectedTask = task
                view.showContextMenu()
            }

            override fun onDoneClickListener(task: Task, view: View) {
                // Nenhuma ação para tarefas deletadas
            }
        })

        deletedTasksBinding.tasksRv.apply {
            layoutManager = LinearLayoutManager(this@DeletedTasksActivity)
            adapter = taskAdapter
        }

        // Registra o menu de contexto
        registerForContextMenu(deletedTasksBinding.tasksRv)

        // Observa as tarefas excluídas no Firebase (deleted = true)
        controller.getAll(deleted = true).observe(this, Observer { deletedTaskList ->
            tasks.clear()
            tasks.addAll(deletedTaskList)
            taskAdapter.notifyDataSetChanged()
        })
    }

    // Define o comportamento do menu de contexto
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.deleted_menu, menu) // Use o menu correto
    }

    // Define o comportamento ao clicar em uma opção do menu de contexto
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.context_reactivate -> {
                selectedTask?.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        // Reativa a tarefa no Firebase chamando o método do controller
                        controller.reactivateTask(it)
                        tasks.remove(it) // Remove da lista local
                        taskAdapter.notifyDataSetChanged() // Atualiza a lista
                    }
                }
                true
            }
            R.id.context_view -> {
                selectedTask?.let {
                    val intent = Intent(this, TaskFormActivity::class.java).apply {
                        putExtra("mode", TaskFormMode.VIEW)
                        putExtra("task", it)
                    }
                    startActivity(intent)
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}
