package com.example.lockscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockScreenUI()
        }
    }
}

@Composable
fun LockScreenUI() {
    val timeState = remember { mutableStateOf("") }
    val dateState = remember { mutableStateOf("") }
    var swipeOffset = remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
            timeState.value = timeFormat.format(Date())
            dateState.value = dateFormat.format(Date())
            kotlinx.coroutines.delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a1a))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        swipeOffset.value += dragAmount.y
                        if (swipeOffset.value > 200f) {
                            // Unlock action
                            swipeOffset.value = 0f
                        }
                    },
                    onDragEnd = {
                        swipeOffset.value = 0f
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Time Display
            Text(
                text = timeState.value,
                style = TextStyle(
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Display
            Text(
                text = dateState.value,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(100.dp))

            // Unlock Hint
            Text(
                text = "Swipe Up to Unlock",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )

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
