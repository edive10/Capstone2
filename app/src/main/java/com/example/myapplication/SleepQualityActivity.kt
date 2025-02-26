package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SleepQualityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepQualityScreen()
        }
    }
}

@Composable
fun SleepQualityScreen() {
    var sleepQuality by remember { mutableStateOf(3f) }
    val context = LocalContext.current

    val sleepStages = listOf("Excellent", "Good", "Fair", "Poor", "Worst")
    val emojis = listOf("😴", "🙂", "😐", "😟", "😞") // ایموجی‌های مربوطه
    val colors = mapOf(
        "Excellent" to Color(0xFF4CAF50), // سبز
        "Good" to Color(0xFF8BC34A), // سبز روشن
        "Fair" to Color(0xFFFFC107), // زرد
        "Poor" to Color(0xFFFF5722), // نارنجی
        "Worst" to Color(0xFFD32F2F) // قرمز
    )

    val selectedIndex = sleepQuality.toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                val intent = Intent(context, PhysicalDistressActivity::class.java)
                context.startActivity(intent) }) {
                Text(
                    text = "🔙",
                    fontSize = 24.sp,
                )
            }
            Text(
                text = "Assessment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3A322F)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "5 of 7", fontSize = 14.sp, color = Color(0xFF6D6460))
        }
        Text(
            text = "How would you rate your sleep quality?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(32.dp))

        sleepStages.forEachIndexed { index, stage ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .background(
                        color = if (index == selectedIndex) colors[stage]!!.copy(alpha = 0.2f) else Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { sleepQuality = index.toFloat() }
                    .padding(16.dp)
            ) {
                Text(
                    text = "${emojis[index]} $stage",
                    fontSize = 16.sp,
                    fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal,
                    color = colors[stage] ?: Color(0xFFB0A8A3)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val sharedPreferences: SharedPreferences =
                    context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("sleep_quality", sleepStages[selectedIndex]).apply()

                val intent = Intent(context, MedicationActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(
                text = "Save & Go to The Next Part",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

