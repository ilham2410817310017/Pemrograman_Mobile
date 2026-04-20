package com.ilham.fokusapp

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {
    private var countDownTimer: CountDownTimer? = null

    // State untuk waktu (detik)
    private val _timeLeft = MutableStateFlow(1500L)
    val timeLeft: StateFlow<Long> = _timeLeft

    // State untuk status timer
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    fun startTimer() {
        if (_isRunning.value) return
        _isRunning.value = true
        countDownTimer = object : CountDownTimer(_timeLeft.value * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = millisUntilFinished / 1000
            }
            override fun onFinish() {
                _isRunning.value = false
            }
        }.start()
    }

    fun pauseTimer() {
        countDownTimer?.cancel()
        _isRunning.value = false
    }

    fun resetTimer(newTime: Long = 1500L) {
        pauseTimer()
        _timeLeft.value = newTime
    }
}