package com.sunny.shakelog.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.sunny.shakelog.ShakeLog
import com.sunny.shakelog.demo.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    shape = RectangleShape, color = MaterialTheme.colors.background
                ) {
                    Counter()
                }
            }
        }
    }
}


@Composable
fun Counter() {
    val counter = remember {
        mutableStateOf(value = 0)
    }
    Scaffold(topBar = {
        TopAppBar() {
            Text("Counter App")
        }

    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            ShakeLog.logEvent("Click", counter.value.toString())
            counter.value++
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    },

        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Tap + icon to log an event ", color = Color.Black)
                Text("${counter.value}", color = Color.Black)
                Text(text = "Shake device to open log view")
                Button(onClick = {
                    ShakeLog.openLog()
                }) {
                    Text(text = "Open Log");
                }
                Button(onClick = {
                    ShakeLog.clearLog()
                }) {
                    Text(text = "Clear Log");
                }
            }
        })


}


@Composable
fun DefaultPreview() {
    AppTheme {
        Counter()
    }
}