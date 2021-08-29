package com.example.android.siv

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import com.example.android.siv.databinding.ActivityAddTaskBinding


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilLocal.text = it.local
                binding.tilAplicador.text = it.aplicador
                binding.tilCrm.text = it.crm
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
            }
        }
        //chamar metodo
        insertListeners()
    }

    private fun insertListeners() {
        //abrir alert dialog calendario
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            //adicionar data
            datePicker.addOnPositiveButtonClickListener {
                //time  zone da maquina / padrão
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                //ultilizar java util / chamar extension/ set data
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }//data
        //hora
        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }// Fim hora
        //cancelar
        binding.btnCancel.setOnClickListener {
            finish()
        }
        //adicionar
        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                local = binding.tilLocal.text,
                aplicador = binding.tilAplicador.text,
                crm = binding.tilCrm.text,
                date = binding.tilDate.text,
                hour = binding.tilHour.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataSource.insertTask(task)
            //teste lista
            //log.e("Tag", "adicionar"+DataSourceTarefa.getList())
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    companion object {
        const val TASK_ID = "task_id"
    }
}

