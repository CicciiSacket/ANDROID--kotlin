package com.example.esamefinale

import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {


    val list = listOf(
        User("Mario","Rossi",47,true),
        User("Ciccio","Terzi",21,true),
        User("Andrea","Bianchi",39,false)
    )
}