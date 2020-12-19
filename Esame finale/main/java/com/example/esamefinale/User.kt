package com.example.esamefinale

import java.io.Serializable

data class User (
    val name: String,
    var surname: String,
    var IDPC: Int,
    var HaveMac: Boolean,

    ) : Serializable
