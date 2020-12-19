package com.example.esamefinale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


const val KEY = "com.example.esamefinale"
class SingleUser : AppCompatActivity() {

    lateinit var name: TextView
    lateinit var surname: TextView
    lateinit var id: TextView
    lateinit var check: CheckBox
    lateinit var student: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        name = findViewById(R.id.textView_name_info)
        surname = findViewById(R.id.textView_surname_info)
        id = findViewById(R.id.textView_id_info)
        check = findViewById(R.id.checkbox_info)

        Log.d(intent.toString(), "intent: ")
        student = intent?.getSerializableExtra(KEY) as User
        name.text = student.name
        surname.text = student.surname
        id.text = student.IDPC.toString()
        check.isChecked = student.HaveMac

        check.setOnCheckedChangeListener { _, isChecked -> student.HaveMac = isChecked
            val data = Intent().apply {
                putExtra(KEY, student)
            }
            setResult(RESULT_OK, data)
            finish()
        }
    }

    companion object {
        fun newIntent(context: Context, student: User): Intent {
            return Intent(context, SingleUser::class.java).apply {
                putExtra(KEY, student)
            }
        }
    }
}