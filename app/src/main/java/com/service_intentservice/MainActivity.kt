package com.service_intentservice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.service_intentservice.services.IntentServiceExample
import com.service_intentservice.ui.theme.Service_IntentServiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Service_IntentServiceTheme {
                initializeIntentService()
            }
        }
    }
}

@Composable
fun initializeIntentService() {
    val context = LocalContext.current as Activity
    var isRunning by rememberSaveable {
        mutableStateOf(false)
    }

    var count by rememberSaveable {
        mutableStateOf(0)
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Box {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Blue)
                        .clickable {
                            // Clicking multiple times here will trigger number of intent services
                            // entering in a queue performing their tasks one after another.
                            Intent(context, IntentServiceExample::class.java).also {
                                it.putExtra("jobNumber", ++count)
                                context.startService(it)
                                isRunning = true
                            }
                        },
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    text = "START"
                )

                Spacer(modifier = Modifier.width(100.dp))

                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Blue)
                        .clickable {
                            // Stopping all of the intent service jobs at once
                            IntentServiceExample.stopService()
                            isRunning = false
                        },
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    text = "STOP"
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.align(Center),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                text = if (isRunning) "Service Started" else "Service Stopped"
            )
        }
    }
}