package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GenderSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenderSelectionScreen { selectedGender ->
                val resultIntent = Intent().apply {
                    putExtra("SELECTED_GENDER", selectedGender)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}

@Composable
fun GenderSelectionScreen(onGenderSelected: (String) -> Unit) {
    var selectedGender by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
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

            IconButton(onClick = {
                val intent = Intent(context, EditProfileActivity::class.java)
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
                text = "Gender",
                fontSize = 14.sp,
                color = Color(0xFF6D6460)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "What's your official gender?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Gender Options
        GenderOption("I am Male", R.drawable.ic_male, selectedGender == "Male") {
            selectedGender = "Male"
        }

        GenderOption("I am Female", R.drawable.ic_female, selectedGender == "Female") {
            selectedGender = "Female"
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Skip & Continue Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Prefer to skip, thanks",
                color = Color(0xFF6D4C41),
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable { onGenderSelected("Skipped") }
                    .padding(12.dp)
            )

            Button(
                onClick = {
                    if (selectedGender != null) {
                        val intent = Intent(context, EditProfileActivity::class.java)
                        context.startActivity(intent)
                        onGenderSelected(selectedGender!!)
                    }
                },
                enabled = selectedGender != null,
                modifier = Modifier
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41))
            ) {
                Text(text = "Submit →", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun GenderOption(text: String, icon: Int, selected: Boolean, onSelect: () -> Unit) {
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
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color(0xFF3A322F),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(selected = selected, onClick = onSelect, colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6D4C41)))
    }
}

