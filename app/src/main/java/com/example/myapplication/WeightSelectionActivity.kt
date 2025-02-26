package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class WeightSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeightSelectionScreen()
        }
    }
}

@Composable
fun WeightSelectionScreen() {
    var weight by remember { mutableStateOf(65f) }
    var isKg by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // دکمه بازگشت
            IconButton(onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent) }) {
                Text(
                    text = "🔙",
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Assessment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3A322F)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Weight",
                fontSize = 14.sp,
                color = Color(0xFF6D6460)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "What's your weight?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Unit Selector (kg / lbs)
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFEDE0D4))
                .padding(4.dp)
        ) {
            Text(
                text = "kg",
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isKg) Color(0xFFD47F4F) else Color.Transparent)
                    .clickable { isKg = true }
                    .padding(vertical = 8.dp, horizontal = 24.dp),
                color = if (isKg) Color.White else Color.Black
            )
            Text(
                text = "lbs",
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (!isKg) Color(0xFFD47F4F) else Color.Transparent)
                    .clickable { isKg = false }
                    .padding(vertical = 8.dp, horizontal = 24.dp),
                color = if (!isKg) Color.White else Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display Weight
        Text(
            text = "${weight.toInt()} ${if (isKg) "kg" else "lbs"}",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Slider
        Slider(
            value = weight,
            onValueChange = { weight = it },
            valueRange = 40f..120f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF87A96B),
                activeTrackColor = Color(0xFF87A96B)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = {
                val intent = Intent(context, NextActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(
                text = "Continue →",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }

}
