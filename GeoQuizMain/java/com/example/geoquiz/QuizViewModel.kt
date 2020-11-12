package com.example.geoquiz


import android.app.Activity
import android.content.Intent
import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.lifecycle.ViewModel
import android.widget.Toast.*
import androidx.lifecycle.ComputableLiveData


private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() { //non fa creare da capo l'app alla rotazione

    var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer : Boolean  // un metodo sotto forma di variabile che non accetta dati in ingresso e maschera appunto il metodo come se fosse una variabile normale
        get() = questionBank[currentIndex].answer

    val currentQuestionText : Int
        get () = questionBank[currentIndex].textResId

    fun moveToNext(){
        if (currentIndex == questionBank.lastIndex){
            currentIndex = questionBank.lastIndex
        }
        else {
            currentIndex = (currentIndex + 1) % questionBank.size  // se non mettessimo questo andrebbe in errore matematico, una sorta di circolarità dell'array, quando in posizione 5 non va alla 6 ma torna allo zero
        }

    }

    fun moveToPrev(){
        if (currentIndex == 0){ // per evitare il crush dell'app appena è zero deve rimanere zero e non andare indietro sennò crusha tutte cose
            currentIndex = 0
        }
        else {
            currentIndex = (currentIndex - 1) % questionBank.size
        }
    }


}