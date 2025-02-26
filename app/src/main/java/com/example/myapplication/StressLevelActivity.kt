package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class StressLevelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StressLevelScreen()
        }
    }
}

@Composable
fun StressLevelScreen() {
    var selectedLevel by remember { mutableStateOf(5) }
    val context = LocalContext.current

    // تغییر رنگ پس‌زمینه بر اساس سطح استرس انتخابی
    val backgroundColor = when (selectedLevel) {
        1 -> Color(0xFFC8E6C9) // سبز ملایم (خیلی آرام)
        2 -> Color(0xFFFFF9C4) // زرد ملایم (کم استرس)
        3 -> Color(0xFFFFE0B2) // نارنجی ملایم (متوسط)
        4 -> Color(0xFFFFCCBC) // قرمز روشن (مضطرب)
        else -> Color(0xFFFFAB91) // قرمز تیره (خیلی استرسی)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor) // تنظیم رنگ پس‌زمینه
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                val intent = Intent(context, MedicationActivity::class.java)
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
            Text(text = "7 of 7", fontSize = 14.sp, color = Color(0xFF6D6460))
        }
        Text(
            text = "How would you rate your stress level?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // نمایش مقدار انتخاب شده
        Text(
            text = selectedLevel.toString(),
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // انتخاب سطح استرس
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFEDE0D4))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..5).forEach { level ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (level == selectedLevel) Color(0xFFD47F4F) else Color.Transparent)
                        .clickable { selectedLevel = level }
                ) {
                    Text(
                        text = when (level) {
                            1 -> "😌" // خیلی آرام
                            2 -> "🙂" // کم استرس
                            3 -> "😐" // متوسط
                            4 -> "😟" // مضطرب
                            else -> "😖" // خیلی استرسی
                        },
                        fontSize = 24.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when (selectedLevel) {
                1 -> "You are very relaxed."
                2 -> "You are slightly stressed."
                3 -> "You are moderately stressed."
                4 -> "You are quite stressed."
                else -> "You Are Extremely Stressed Out."
            },
            fontSize = 16.sp,
            color = Color(0xFF6D6460)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // دکمه ارسال اطلاعات به صفحه بعد
        Button(
            onClick = {
                val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                sharedPreferences.edit().putInt("STRESS_LEVEL", selectedLevel).apply() // ذخیره مقدار استرس

                val intent = Intent(context, MoodTrackingActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(
                text = "Submit",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
