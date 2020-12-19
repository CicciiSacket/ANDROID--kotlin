package com.example.esamefinale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var username:EditText = findViewById(R.id.text_username)
        var password:EditText = findViewById(R.id.text_password)
        var submit:Button = findViewById(R.id.login_btn)

        submit.setOnClickListener {
            val usernick:String = username.text.toString();
            val pass:String = password.text.toString();

            if(usernick == "admin" && pass == "francesco.sacco"){
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                val intent = ListUser.newIntent(this)
                startActivity(intent)
            }
        }
    }
}
