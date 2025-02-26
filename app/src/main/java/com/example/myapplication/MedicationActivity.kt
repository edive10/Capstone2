package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MedicationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicationScreen()
        }
    }
}

@Composable
fun MedicationScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    // مقدار ذخیره‌شده قبلی را دریافت کن
    var medicationInput by remember {
        mutableStateOf(sharedPreferences.getString("medication_list", "") ?: "")
    }

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
            IconButton(onClick = {
                val intent = Intent(context, SleepQualityActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(text = "🔙", fontSize = 24.sp)
            }
            Text(
                text = "Assessment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3A322F)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "6 of 7", fontSize = 14.sp, color = Color(0xFF6D6460))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title
        Text(
            text = "What medications are you currently taking?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input Field
        OutlinedTextField(
            value = medicationInput,
            onValueChange = {
                medicationInput = it
                // ذخیره مقدار وارد شده در SharedPreferences
                sharedPreferences.edit().putString("medication_list", it).apply()
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF3A322F)),
            label = { Text("Enter medication names (if any)") },
            placeholder = { Text("e.g., Paracetamol, Vitamin C") },
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = {
                val intent = Intent(context, StressLevelActivity::class.java)
                intent.putExtra("medication_list", medicationInput) // ارسال مقدار وارد شده
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
