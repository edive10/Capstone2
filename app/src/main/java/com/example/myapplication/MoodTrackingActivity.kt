package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MoodTrackingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)


        val medicationList = intent.getStringExtra("medication_list")


        if (!medicationList.isNullOrEmpty()) {
            sharedPreferences.edit().putString("medication_list", medicationList).apply()
        }

        setContent {
            MoodTrackingScreen()
        }
    }
}

@Composable
fun MoodTrackingScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val sharedPreferencesHelp = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    val sleepQuality = sharedPreferences.getString("sleep_quality", "No data available") ?: "No data available"
    val stressLevel = sharedPreferences.getInt("STRESS_LEVEL", -1)
    val professionalHelp = sharedPreferencesHelp.getString("ProfessionalHelp", "No data available") ?: "No data available"
    val medicationList = sharedPreferences.getString("medication_list", "No medication recorded") ?: "No medication recorded"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mood Tracking",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(24.dp))

        InfoBox(text = "Sleep Quality: $sleepQuality")
        Spacer(modifier = Modifier.height(16.dp))
        InfoBox(text = "Stress Level: ${getStressEmoji(stressLevel)} $stressLevel")
        Spacer(modifier = Modifier.height(16.dp))
        InfoBox(
            text = "Any Professional Help: $professionalHelp",
            textColor = if (professionalHelp == "No") Color(0xFFFF4081) else Color(0xFF3ADE3A)
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoBox(text = "Medication: $medicationList")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(text = "Go to Profile", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun InfoBox(text: String, textColor: Color = Color(0xFF6D6460)) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = textColor)
    }
}

fun getStressEmoji(stressLevel: Int): String {
    return when (stressLevel) {
        1 -> "😌"
        2 -> "🙂"
        3 -> "😐"
        4 -> "😟"
        5 -> "😖"
        else -> "❓"
    }
}
