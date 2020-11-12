package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0


class MainActivity : AppCompatActivity(), View.OnClickListener {

    //dichiaro le due variabili lateinit perché verranno inizializzate a seguire
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView


    private val quizViewModel : QuizViewModel by lazy{ //by lazy costrutto di kotlin
        ViewModelProvider(this).get(QuizViewModel :: class.java) // iniziallizza la prima volta, tipo un init solo che non è var ma è val.
    }

    //metodi override per vedere il cycleLife dell'activity e grazie alla classe viewmodel.kt anche della viwmodel, dato che alla rotazione schermo viene distrutta l'activity ma non il viewmodel
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "OnStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "OnPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {//prima del view model si usava anche questo per il problema della rotazione
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,"onSaveInstaceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex) // salva in key index il valore di current index di quizviewmodel
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "OnStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        setContentView(R.layout.activity_main) //richiama il file xml della schermata

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0 //richiama il valore di keyindex che abbiamo lasicato alla chiusura altrimenti da il valore di default, questo sempre se esiste, altrimenti se savedinstantstate è null, si prende l'elvis operator{se girato sembra elvis dagli occhi e il ciuffo}
        quizViewModel.currentIndex = currentIndex // solo se entrambi sono dello stesso tipo altrimenti non compila, ecco perche si mette elvis, per impedire che sia nullable

//        val provider : ViewModelProvider = ViewModelProviders.of(this)
//        val quizViewModel= provider.get(QuizViewModel::class.java)
//        Log.d(TAG,"Got a QuizViewModel : $quizViewModel") // $ -> concatena il valore della variabile alla stringa, tipo il + in java; ${quizviewmodel.tostring()}-> modo per richiamare i metodi




        //assegno alle variabili le view presenti nel filo di layout tramite il loro id
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        prevButton = findViewById(R.id.prev_button)
        cheatButton = findViewById(R.id.cheat_button)

        //assegno ai button il listener agli eventi di tipo OnClick
        trueButton.setOnClickListener(this) //si usa this perchè abbiamo riscritto la funzione onClick dell'interfaccia View.OnClickListener
        falseButton.setOnClickListener(this)
        nextButton.setOnClickListener(this)
        prevButton.setOnClickListener(this)
        cheatButton.setOnClickListener(this)


        //setto la prima domanda nella TextView
        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
            if (resultCode != Activity.RESULT_OK){
                return
            }
            if (requestCode == REQUEST_CODE_CHEAT){
                quizViewModel.isCheater =
                    data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            }
    }



    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResIs = when {
            quizViewModel.isCheater-> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.error_toast
        }
        makeText(this, messageResIs, LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {

        //controllo se view è null o meno
        view?.let {
            when (it.id) { //poiché non abbiamo messo nome al parametro e quindi é questo

                R.id.true_button -> checkAnswer(true)

                R.id.false_button -> checkAnswer(false)

                R.id.next_button -> {
                    quizViewModel.isCheater = false //risolve il bug che il cheat partiva per tutte le domande
                    quizViewModel.moveToNext()
                    updateQuestion()
                }
                R.id.prev_button -> {
                    quizViewModel.moveToPrev()
                    updateQuestion()
                }
                R.id.cheat_button -> {
                    val answerIsTrue = quizViewModel.currentQuestionAnswer
                    val intent = ActivityCheat.newIntent(this, answerIsTrue)
                    startActivityForResult(intent,REQUEST_CODE_CHEAT)
                }
            }
            //updateQuestion() //se si elimina il next e prev e va avanti da solo dopo la risposta giusta o sbagliata che sia
        }
    }
}



//metodi override per vedere il cycleLife dell'activity e grazie alla classe viewmodel.kt anche della viwmodel, dato che alla rotazione schermo viene distrutta l'activity ma non il viewmodel
//    override fun onStart() {
//        super.onStart()
//        Log.d(TAG, "OnStart() called")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d(TAG, "OnResume() called")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d(TAG, "OnPause() called")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.d(TAG, "OnStop() called")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(TAG, "OnDestroy() called")
//    }


