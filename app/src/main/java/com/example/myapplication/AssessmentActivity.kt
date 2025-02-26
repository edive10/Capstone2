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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import android.content.Context
import android.content.SharedPreferences



class AssessmentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssessmentScreen()
        }
    }
}

@Composable
fun AssessmentScreen() {
    var selectedOption by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val options = listOf(
        "I wanna reduce stress" to Icons.Filled.Favorite,
        "I wanna try AI Therapy" to Icons.Filled.SmartToy,
        "I want to cope with trauma" to Icons.Filled.Psychology,
        "I want to be a better person" to Icons.Filled.SelfImprovement,
        "Just trying out the app, mate!" to Icons.Filled.Person
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Assessment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3A322F)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "1 of 7",
                fontSize = 14.sp,
                color = Color(0xFF6D6460)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "What's your health goal\nfor today?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Options List
        options.forEach { (text, icon) ->
            AssessmentOption(
                text = text,
                icon = icon,
                selected = selectedOption == text,
                onSelect = { selectedOption = text }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // ذخیره‌ی انتخاب کاربر
                sharedPreferences.edit().putString("assessment_result", selectedOption).apply()

                // رفتن به صفحه بعدی
                val intent = Intent(context, NextActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41))
        ) {
            Text(text = "Continue →", color = Color.White, fontSize = 18.sp)
        }
        Button(
            onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41))
        ) {
            Text(text = "Back", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun AssessmentOption(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = if (selected) Color(0xFFB0C998) else Color.White,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onSelect() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = Color(0xFF6D4C41))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 16.sp, color = Color(0xFF3A322F), fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(selected = selected, onClick = onSelect, colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6D4C41)))
    }
}
