package com.example.myapplication

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.viewmodel.UserViewModel

@Composable
fun AgeSelectionScreen(userViewModel: UserViewModel, onAgeSelected: (Int) -> Unit) {
    val context = LocalContext.current
    val userData by userViewModel.userData

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
                val intent = Intent(context, EditProfileActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(text = "🔙", fontSize = 24.sp)
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
                text = "3 of 10",
                fontSize = 14.sp,
                color = Color(0xFF6D6460)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "What's your age?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Age Picker
        AgePicker(
            selectedAge = userData.age ?: 18,
            onAgeChange = { userViewModel.updateUserData(userData.name ?: "", userData.email ?: "", it) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = {
                val selectedAge = userData.age ?: 18
                onAgeSelected(selectedAge)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(
                text = "Submit →",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun AgePicker(selectedAge: Int, onAgeChange: (Int) -> Unit) {
    val minAge = 16
    val maxAge = 99

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = (selectedAge - 1).toString(),
            fontSize = 24.sp,
            color = Color.Gray.takeIf { selectedAge > minAge } ?: Color.Transparent
        )

        Box(
            modifier = Modifier
                .background(Color(0xFFB0C998), RoundedCornerShape(32.dp))
                .padding(vertical = 16.dp, horizontal = 32.dp)
        ) {
            Text(
                text = selectedAge.toString(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            text = (selectedAge + 1).toString(),
            fontSize = 24.sp,
            color = Color.Gray.takeIf { selectedAge < maxAge } ?: Color.Transparent
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            IconButton(onClick = { if (selectedAge > minAge) onAgeChange(selectedAge - 1) }) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Increase Age")
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = { if (selectedAge < maxAge) onAgeChange(selectedAge + 1) }) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Decrease Age")
            }
        }
    }
}
