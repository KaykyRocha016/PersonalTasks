package com.example.personaltasks


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaltasks.adapter.TaskAdapter
import com.example.personaltasks.databinding.PersonalTasksBinding
import com.example.personaltasks.model.Task
import com.example.personaltasks.ui.TaskFormActivity

class MainActivity : AppCompatActivity() {

    private lateinit var personalTasksBinding: PersonalTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        personalTasksBinding = PersonalTasksBinding.inflate(layoutInflater)
        setContentView(personalTasksBinding.root)


        val fakeTasks = listOf(
            Task(1, "Estudar Kotlin", "Revisar fundamentos básicos", "21/05/2025"),
            Task(2, "Criar layout", "Implementar RecyclerView", "22/05/2025"),
            Task(3, "Finalizar app", "Subir no GitHub com vídeo", "25/05/2025")
        )

        personalTasksBinding.tasksRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = TaskAdapter(fakeTasks)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_main,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_new_task -> {
                val intent = Intent(this, TaskFormActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

}

