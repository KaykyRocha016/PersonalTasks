    package com.example.personaltasks


    import android.content.Intent
    import android.os.Build
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.Menu
    import android.view.MenuItem
    import androidx.activity.result.ActivityResultCallback
    import androidx.activity.result.ActivityResultLauncher
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.personaltasks.adapter.TaskAdapter
    import com.example.personaltasks.databinding.PersonalTasksBinding
    import com.example.personaltasks.model.Task
    import com.example.personaltasks.ui.TaskFormActivity

    class MainActivity : AppCompatActivity() {

        private lateinit var taskAdapter: TaskAdapter
        private val tasks = mutableListOf<Task>()
        private lateinit var addEditTaskLauncher: ActivityResultLauncher<Intent>
        lateinit var personalTasksBinding: PersonalTasksBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            personalTasksBinding = PersonalTasksBinding.inflate(layoutInflater)
            setContentView(personalTasksBinding.root)


            tasks.addAll(
                listOf(
                    Task(1, "Estudar Kotlin", "Revisar fundamentos básicos", "21/05/2025"),
                    Task(2, "Criar layout", "Implementar RecyclerView", "22/05/2025"),
                    Task(3, "Finalizar app", "Subir no GitHub com vídeo", "25/05/2025")
                )
            )


            taskAdapter = TaskAdapter(tasks)
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
    }
