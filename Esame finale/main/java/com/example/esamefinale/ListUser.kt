package com.example.esamefinale

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ListUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item_user)

        var fragment = supportFragmentManager.findFragmentById(R.id.recyclerlist)

        if (fragment == null){
            fragment = FragmentList()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }

    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ListUser::class.java)
        }
    }
}