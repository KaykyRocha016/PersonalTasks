package com.example.personaltasks.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TaskAdapter
import com.example.personaltasks.controller.PersonalTasksController
import com.example.personaltasks.data.FirebaseRepository
import com.example.personaltasks.databinding.PersonalTasksBinding
import com.example.personaltasks.model.IOnTaskInteractionListener
import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskFormMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), IOnTaskInteractionListener {

    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()
    private lateinit var addEditTaskLauncher: ActivityResultLauncher<Intent>
    private lateinit var personalTasksBinding: PersonalTasksBinding
    private var selectedTask: Task? = null
    private lateinit var controller: PersonalTasksController
    private val firebaseRepository = FirebaseRepository();


    // Método chamado na criação da Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personalTasksBinding = PersonalTasksBinding.inflate(layoutInflater)
        setContentView(personalTasksBinding.root)

        // Inicia o controller com o repositório do Firebase
        controller = PersonalTasksController(firebaseRepository = firebaseRepository)

        taskAdapter = TaskAdapter(tasks, this)
        personalTasksBinding.tasksRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        // Registra o menu de contexto na RecyclerView
        registerForContextMenu(personalTasksBinding.tasksRv)

        // Observar as tarefas no Firebase
        controller.getAll().observe(this, Observer { taskList ->
            tasks.clear()
            tasks.addAll(taskList)
            taskAdapter.notifyDataSetChanged()
        })

        // Edição ou adição de task
        addEditTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    data?.getParcelableExtra("task", Task::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    data?.getParcelableExtra<Task>("task")
                }
                task?.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (tasks.any { t -> t.id == it.id }) {
                            controller.updateTask(it) // Atualiza tarefa no Firebase
                            val index = tasks.indexOfFirst { t -> t.id == it.id }
                            tasks[index] = it
                            taskAdapter.notifyItemChanged(index)
                        } else {
                            controller.insertTask(it) // Insere nova tarefa no Firebase
                            tasks.add(it)
                            taskAdapter.notifyItemInserted(tasks.size - 1)
                        }
                    }
                }
            }
        }
    }

    // Define o comportamento da criação do menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Define o comportamento do botão de criar uma task
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_new_task -> {
                val intent = Intent(this, TaskFormActivity::class.java)
                addEditTaskLauncher.launch(intent)
                true
            }
           // R.id.menu_deleted_tasks -> {
                // Navega para a tela de tarefas excluídas
             //   val intent = Intent(this, DeletedTasksActivity::class.java)
               // startActivity(intent)
                //true
           // }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Define o comportamento do clique longo em uma task (menu de contexto)
    override fun onTaskLongClick(task: Task, view: View) {
        selectedTask = task
        view.showContextMenu()
    }

    override fun onDoneClickListener(task: Task, view: View) {
        selectedTask = task
        task.completed = task.completed != true // Alterna o status da tarefa
        CoroutineScope(Dispatchers.Main).launch {
            controller.updateTask(task) // Atualiza o status no Firebase
        }
    }

    // Define o comportamento da abertura do menu de contexto
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.task_menu_context, menu)
    }

    // Método de deleção de task
    private fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.Main).launch {
            controller.deleteTask(task) // Deleta no Firebase
            tasks.remove(task)
            taskAdapter.notifyDataSetChanged()
        }
    }

    // Define o comportamento ao clicar em uma opção do menu de contexto
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.context_edit -> {
                selectedTask?.let {
                    val intent = Intent(this, TaskFormActivity::class.java).apply {
                        putExtra("mode", TaskFormMode.EDIT)
                        putExtra("task", it)
                    }
                    addEditTaskLauncher.launch(intent)
                }
                true
            }
            R.id.context_delete -> {
                selectedTask?.let {
                    AlertDialog.Builder(this)
                        .setTitle("Excluir tarefa")
                        .setMessage("Deseja realmente excluir a tarefa \"${it.title}\"?")
                        .setPositiveButton("Sim") { _, _ -> deleteTask(it) }
                        .setNegativeButton("Não", null)
                        .show()
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
