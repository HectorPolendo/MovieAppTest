package com.hectorpolendo.movieapptest.util

import android.os.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow

object LocationTime {

    val number = MutableStateFlow(0)
    val isCounterRunning = MutableStateFlow(true)

    val timer = object: CountDownTimer((1000*60)*5, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            number.value = (millisUntilFinished/1000).toInt()
        }
        override fun onFinish() {
            isCounterRunning.value = false
            yourOperation()
        }
    }

    fun initialize() {
        yourOperation()
    }

    fun yourOperation() {
        if(!isCounterRunning.value){
            isCounterRunning.value = true;
            timer.start()
        }else{
            timer.cancel()
            timer.start()
        }

    }
}