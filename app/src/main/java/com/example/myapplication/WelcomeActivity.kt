package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                WelcomeScreen(onGetStartedClick = {
                    startActivity(Intent(this, MainActivity::class.java))
                })
            }
        }
    }
}

@Composable
fun WelcomeScreen(onGetStartedClick: () -> Unit) {
    val context = LocalContext.current // ✅ Correct way to get the context

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.doctorai),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome to DoctorAI",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your mindful mental health AI companion\nfor everyone, anywhere 🌱",
            fontSize = 16.sp,
            color = Color(0xFF6D6460),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onGetStartedClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41))
        ) {
            Text(text = "Get Started", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Already have an account? Sign In.",
            color = Color(0xFF6D4C41),
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                val intent = Intent(context, SignInActivity::class.java)
                context.startActivity(intent) // ✅ Corrected linking to SignInActivity
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    MyApplicationTheme {
        WelcomeScreen(onGetStartedClick = {})
    }
}
