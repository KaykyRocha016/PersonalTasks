package com.example.personaltasks.ui
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityTaskFormBinding
import android.content.Intent
import com.example.personaltasks.model.Task
import java.util.*

class TaskFormActivity :AppCompatActivity() {
    private lateinit var activityTaskFormBinding: ActivityTaskFormBinding;
    private var selectedDate : String = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTaskFormBinding = ActivityTaskFormBinding.inflate(layoutInflater)
        setContentView(activityTaskFormBinding.root)

        activityTaskFormBinding.deadlineTv.setOnClickListener{
            openDatePickerDialog()


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
