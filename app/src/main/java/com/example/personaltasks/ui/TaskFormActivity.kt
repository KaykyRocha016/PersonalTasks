package com.example.personaltasks.ui
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityTaskFormBinding
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Toast
import com.example.personaltasks.model.Task
import com.example.personaltasks.model.TaskFormMode
import java.util.*

class TaskFormActivity :AppCompatActivity() {
    private lateinit var activityTaskFormBinding: ActivityTaskFormBinding;
    private var selectedDate : String = "";
    private var mode : TaskFormMode = TaskFormMode.NEW;
    private var taskToEdit :Task?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTaskFormBinding = ActivityTaskFormBinding.inflate(layoutInflater)
        setContentView(activityTaskFormBinding.root)

        activityTaskFormBinding.deadlineTv.setOnClickListener{
            openDatePickerDialog()


        }
        mode = if(  Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("mode", TaskFormMode::class.java) ?: TaskFormMode.NEW
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("mode") as? TaskFormMode ?: TaskFormMode.NEW
        }
        taskToEdit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("task", Task::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Task>("task")
        }

        // Preencher campos se for edição ou visualização
        taskToEdit?.let {
            activityTaskFormBinding.titleEt.setText(it.title)
            activityTaskFormBinding.descriptionEt.setText(it.description)
            activityTaskFormBinding.deadlineTv.setText(it.deadline )
        }

        if (mode == TaskFormMode.VIEW) {
            activityTaskFormBinding.titleEt.isEnabled = false
            activityTaskFormBinding.deadlineTv.isEnabled=false
            activityTaskFormBinding.descriptionEt.isEnabled=false
            activityTaskFormBinding.btnSave.visibility= View.GONE


        }
        activityTaskFormBinding.btnSave.setOnClickListener{
            val title= activityTaskFormBinding.titleEt.text.toString()
            val description = activityTaskFormBinding.descriptionEt.text.toString()
            val deadline = activityTaskFormBinding.deadlineTv.text.toString()
            if (title.isBlank() || description.isBlank()||deadline.isBlank()) {
                Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            val task = Task(UUID.randomUUID(),title,description,deadline)
            val resultIntent = Intent().apply { putExtra("task",task) }
            setResult(RESULT_OK,resultIntent)
            finish()
        }
        activityTaskFormBinding.btnCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }


    }
    private fun openDatePickerDialog(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "%02d/%02d/%d".format(selectedDay, selectedMonth + 1, selectedYear)
            activityTaskFormBinding.deadlineTv.text = selectedDate
        }, year, month, day)

        datePicker.show()
    }


}
