package com.example.composeflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class HomeViewMoıdel : ViewModel() {

    val countDownTimerFlow = flow<Int> {
        val coundDownFrom = 10
        var counter = coundDownFrom

        emit(coundDownFrom)

        while (counter > 0){
            delay(1000)
            counter--
            emit(counter)
        }
    }

}