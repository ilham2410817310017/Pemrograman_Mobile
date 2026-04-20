package com.ilham.fokusapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TimerScreen(viewModel: TimerViewModel = viewModel()) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fokus Belajar",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- BAGIAN MODE FOKUS ---
        Text("Mode Fokus", style = MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = timeLeft == 1500L, onClick = { viewModel.resetTimer(1500L) }, label = { Text("25m") })
            FilterChip(selected = timeLeft == 2400L, onClick = { viewModel.resetTimer(2400L) }, label = { Text("40m") })
            FilterChip(selected = timeLeft == 3120L, onClick = { viewModel.resetTimer(3120L) }, label = { Text("52m") })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Mode Istirahat", style = MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = timeLeft == 300L, onClick = { viewModel.resetTimer(300L) }, label = { Text("5m") })
            FilterChip(selected = timeLeft == 1020L, onClick = { viewModel.resetTimer(1020L) }, label = { Text("17m") })
            FilterChip(selected = timeLeft == 1200L, onClick = { viewModel.resetTimer(1200L) }, label = { Text("20m") })
        }

        Spacer(modifier = Modifier.height(48.dp))

        val minutes = timeLeft / 60
        val seconds = timeLeft % 60
        Text(
            text = "%02d:%02d".format(minutes, seconds),
            fontSize = 100.sp, // Saya perbesar supaya lebih dramatis di video
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { if (isRunning) viewModel.pauseTimer() else viewModel.startTimer() },
                modifier = Modifier.width(140.dp).height(56.dp)
            ) {
                Text(if (isRunning) "PAUSE" else "START")
            }

            Spacer(modifier = Modifier.width(16.dp))

            OutlinedButton(
                onClick = { viewModel.resetTimer() },
                modifier = Modifier.width(140.dp).height(56.dp)
            ) {
                Text("RESET")
            }
        }
    }
}