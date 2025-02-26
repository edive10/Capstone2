package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class NextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodSelectionScreen()
        }
    }
}

@Composable
fun MoodSelectionScreen() {
    var selectedMood by remember { mutableStateOf(1) }
    val context = LocalContext.current

    val moodData = listOf(
        MoodItem("😢", "I Feel Sad.", Color(0xFFFFAB91)),
        MoodItem("😐", "I Feel Neutral.", Color(0xFFFFD54F)),
        MoodItem("😊", "I Feel Happy.", Color(0xFF81C784))
    )

    val animatedColor by animateColorAsState(targetValue = moodData[selectedMood].color)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                text = "2 of 7",
                fontSize = 14.sp,
                color = Color(0xFF6D6460)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "How would you describe your mood?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = moodData[selectedMood].text,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF6D6460)
        )

        Spacer(modifier = Modifier.height(32.dp))


        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(8.dp, shape = CircleShape)
                .background(animatedColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = moodData[selectedMood].emoji, fontSize = 48.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            moodData.forEachIndexed { index, mood ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .shadow(6.dp, shape = CircleShape)
                        .background(if (selectedMood == index) mood.color else Color(0xFFD7CCC8), shape = CircleShape)
                        .clickable { selectedMood = index },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = mood.emoji, fontSize = 40.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                val intent = Intent(context, ProfessionalHelpActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .shadow(8.dp, shape = RoundedCornerShape(32.dp)),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(
                text = "Continue →",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                val intent = Intent(context, AssessmentActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .shadow(8.dp, shape = RoundedCornerShape(32.dp)),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(
                text = "Back",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


data class MoodItem(val emoji: String, val text: String, val color: Color)
