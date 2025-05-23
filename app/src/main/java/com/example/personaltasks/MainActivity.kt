    package com.example.personaltasks


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
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.personaltasks.adapter.TaskAdapter
    import com.example.personaltasks.databinding.PersonalTasksBinding
    import com.example.personaltasks.model.IOnTaskInteractionListener
    import com.example.personaltasks.model.Task
    import com.example.personaltasks.model.TaskFormMode
    import com.example.personaltasks.ui.TaskFormActivity
    import java.util.UUID

    class MainActivity : AppCompatActivity(), IOnTaskInteractionListener {

        private lateinit var taskAdapter: TaskAdapter
        private val tasks = mutableListOf<Task>()
        private lateinit var addEditTaskLauncher: ActivityResultLauncher<Intent>
        private lateinit var personalTasksBinding: PersonalTasksBinding
        private var selectedTask : Task? = null

        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            personalTasksBinding = PersonalTasksBinding.inflate(layoutInflater)
            setContentView(personalTasksBinding.root)


            tasks.addAll(
                listOf(
                    Task(UUID.randomUUID(), "Estudar Kotlin", "Revisar fundamentos básicos", "21/05/2025"),
                    Task(UUID.randomUUID(), "Criar layout", "Implementar RecyclerView", "22/05/2025"),
                    Task(UUID.randomUUID(), "Finalizar app", "Subir no GitHub com vídeo", "25/05/2025")
                )
            )


            taskAdapter = TaskAdapter(tasks,this)
            personalTasksBinding.tasksRv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = taskAdapter
            }

            addEditTaskLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        data?.getParcelableExtra("task", Task::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        data?.getParcelableExtra<Task>("task")
                    }
                    task?.let {

                        val index = tasks.indexOfFirst { t -> t.id == it.id }
                        if (index >= 0) {
                            tasks[index] = it
                        } else {
                            tasks.add(it)
                        }
                        taskAdapter.notifyDataSetChanged()
                    }
                }
            }
            registerForContextMenu(personalTasksBinding.tasksRv)
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_new_task -> {
                    val intent = Intent(this, TaskFormActivity::class.java)
                    addEditTaskLauncher.launch(intent)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }

        override fun onTaskLongClick(task: Task, view: View) {
            selectedTask=task
            view.showContextMenu()
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            super.onCreateContextMenu(menu, v, menuInfo)
            menuInflater.inflate(R.menu.task_menu_context,menu);
        }
        override fun onContextItemSelected(item: MenuItem): Boolean {
            return when(item.itemId) {
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
                            .setPositiveButton("Sim") { _, _ ->
                                tasks.remove(it)
                                taskAdapter.notifyDataSetChanged()
                            }
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
