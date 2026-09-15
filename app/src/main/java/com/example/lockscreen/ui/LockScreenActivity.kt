package com.example.lockscreen.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lockscreen.ui.theme.LockScreenTheme
import java.text.SimpleDateFormat
import java.util.*

class LockScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockScreenTheme {
                LockScreenContent(
                    onUnlock = { finish() }
                )
            }
        }
    }
}

@Composable
fun LockScreenContent(onUnlock: () -> Unit) {
    var currentTime by remember { mutableStateOf("") }
    var dragOffset by remember { mutableStateOf(0f) }
    val unlockThreshold = 300f

    // Update time every second
    LaunchedEffect(Unit) {
        while (true) {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            currentTime = sdf.format(Date())
            kotlinx.coroutines.delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.foundation.background(
                    Color(0xFF1a1a1a)
                ).brush
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        dragOffset += dragAmount.y
                        if (dragOffset > unlockThreshold) {
                            onUnlock()
                        }
                    },
                    onDragEnd = {
                        dragOffset = 0f
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Time display
            Text(
                text = currentTime,
                style = TextStyle(
                    fontSize = 72.sp,
                    color = Color.White
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Date display
            Text(
                text = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date()),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.LightGray
                ),
                modifier = Modifier.padding(bottom = 48.dp)
            )
        }

        // Unlock hint at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Swipe up to unlock",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "↑",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Gray
                )
            )
        }
    }
}
