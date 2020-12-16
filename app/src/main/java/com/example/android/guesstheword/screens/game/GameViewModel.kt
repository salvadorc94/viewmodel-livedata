package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel(){

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    private  val timer: CountDownTimer


    // The current word
    //var word = ""
    //val word = MutableLiveData<String>()

    private val _word = MutableLiveData<String>()
    val word : LiveData<String>
        get() = _word

    // The current score
    //var score = 0

    //LiveData score, always starts with a null value
    //val score = MutableLiveData<Int>()
    //Encapsulation
    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    init {
        resetList()
        nextWord()
        //LiveData default thing
        _score.value = 0

        //CREATES THE COUNTDOWN
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }

        timer.start()

        Log.i("GameViewModel", "GameViewModel created!")
    }

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish : LiveData<Boolean>
        get() = _eventGameFinish

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
            //_eventGameFinish.value = true
        }
        _word.value = wordList.removeAt(0)

    }
    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    /** Methods for completed events **/
    // false
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

}