package com.example.myapplication

import android.content.Intent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class PhysicalDistressActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhysicalDistressScreen()
        }
    }
}

@Composable
fun PhysicalDistressScreen() {
    var selectedOption by remember { mutableStateOf(0) }
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
            IconButton(onClick = {
                val intent = Intent(context, ProfessionalHelpActivity::class.java)
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
                text = "4 of 7",
                fontSize = 14.sp,
                color = Color(0xFF6D6460)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Question Title
        Text(
            text = "Are you experiencing any physical distress?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Option 1: Yes, one or multiple
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .clickable { selectedOption = 1 }
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = selectedOption == 1,
                    onClick = { selectedOption = 1 }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Yes, one or multiple",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3A322F)
                    )
                    Text(
                        text = "I'm experiencing physical pain in different places over my body.",
                        fontSize = 14.sp,
                        color = Color(0xFF6D6460)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Option 2: No Physical Pain At All
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(if (selectedOption == 2) Color(0xFFA5C882) else Color.White)
                .clickable { selectedOption = 2 }
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = selectedOption == 2,
                    onClick = { selectedOption = 2 }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "No Physical Pain At All",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedOption == 2) Color.White else Color(0xFF3A322F)
                    )
                    Text(
                        text = "I'm not experiencing any physical pain in my body at all :)",
                        fontSize = 14.sp,
                        color = if (selectedOption == 2) Color.White else Color(0xFF6D6460)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = {
                val intent = Intent(context, SleepQualityActivity::class.java)
                intent.putExtra("physical_distress", selectedOption)
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

@Preview(showBackground = true)
@Composable
fun PhysicalDistressScreenPreview() {
    PhysicalDistressScreen()
}
