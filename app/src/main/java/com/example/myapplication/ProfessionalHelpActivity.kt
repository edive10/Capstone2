package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ProfessionalHelpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfessionalHelpScreen()
        }
    }
}

@Composable
fun ProfessionalHelpScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    var selectedOption by remember { mutableStateOf(sharedPreferences.getString("ProfessionalHelp", null)) }

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
                val intent = Intent(context, NextActivity::class.java)
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
            Text(
                text = "3 of 7",
                fontSize = 14.sp,
                color = Color(0xFF6D6460)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Have you sought professional help before?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Image
        Image(
            painter = painterResource(id = R.drawable.ic_professional_help),
            contentDescription = "Professional Help Illustration",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Yes and No Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    selectedOption = "Yes"
                    sharedPreferences.edit().putString("ProfessionalHelp", "Yes").apply()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedOption == "Yes") Color(0xFF81C784) else Color(0xFFE6E0DD)
                )
            ) {
                Text(text = "Yes", color = Color.Black, fontSize = 16.sp)
            }

            Button(
                onClick = {
                    selectedOption = "No"
                    sharedPreferences.edit().putString("ProfessionalHelp", "No").apply()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedOption == "No") Color(0xFFFFCDD2) else Color(0xFFE6E0DD)
                )
            ) {
                Text(text = "No", color = Color.Black, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = {
                val intent = Intent(context, PhysicalDistressActivity::class.java)
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
