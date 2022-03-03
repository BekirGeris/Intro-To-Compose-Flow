package com.example.composeflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewMoıdel : ViewModel() {

    val countDownTimerFlow = flow<Int> {
        val coundDownFrom = 10
        var counter = coundDownFrom

        emit(coundDownFrom)

        while (counter > 0) {
            delay(1000)
            counter--
            emit(counter)
        }
    }

    init {
        //collectInViewModel()
    }

    private fun collectInViewModel() {
        viewModelScope.launch {
            countDownTimerFlow
                .filter {
                    it % 3 == 0
                }
                .map {
                    it * it
                }
                .onEach {
                }
                .collect {
                    println("counter : ${it}")
                }

            countDownTimerFlow.collectLatest {
                delay(2000)//her gönderilen veriden sonra 2 sn bekler eğer 2 sn geçmeden yeni bir veri gelirse bu sefer onun için 2sn bekler
                println("counter : ${it}")
            }
        }

        countDownTimerFlow.onEach {
            println(it)
        }.launchIn(viewModelScope)
    }

    //LiveData comparisons

    private val _liveData = MutableLiveData<String>("Kotlin Live Data")
    val liveData: LiveData<String> = _liveData

    fun changeLiveDataValue() {
        _liveData.value = "Live Data"
    }

    private val _stateFlow = MutableStateFlow("Kotlin State Flow")
    val stateFlow: StateFlow<String> = _stateFlow.asStateFlow()

    fun changeStateFlowValue(){
        _stateFlow.value = "State Flow"
    }

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow: SharedFlow<String> = _sharedFlow.asSharedFlow()

    fun changeSharedFlowValue(){
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }

}