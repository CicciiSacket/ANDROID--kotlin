package com.example.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int , val answer: Boolean) // classe completa con due proprietá -- data class crea da solo i metodi equal(), hashCode() e toString(); Ció che c'è dentro le parentesi é un costruttore che definisce le proprietá e i tipi dei dati, il primo dato é una risorsa ID di tipo intero ad esempio; val è una variabile che idealmente ha solo un get e non un set(read only)



