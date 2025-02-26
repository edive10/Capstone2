package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen()
        }
    }
}

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("UserData", Context.MODE_PRIVATE) }
    val firstName = sharedPreferences.getString("username", "No Name") ?: "No Name"
    val lastName = sharedPreferences.getString("bio", "No Bio") ?: "No Bio"
    val gender = sharedPreferences.getString("gender", "Unknown") ?: "Unknown"
    val birthDate = sharedPreferences.getString("birth_date", "Not Set") ?: "Not Set"

    val genderEmoji = when (gender.lowercase()) {
        "male" -> "👨"
        "female" -> "👩"
        else -> "❓"
    }
    val genderText: AnnotatedString = buildAnnotatedString {
        append("Gender: $gender ")
        pushStyle(SpanStyle(fontSize = 40.sp))
        append(genderEmoji)
        pop()
    }
    val age = calculateAge(birthDate)

    Scaffold(
        bottomBar = { BottomNavigationBar("Home") }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.homepage),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Home Page", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0EAE2).copy(alpha = 0.9f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("First Name: $firstName", style = MaterialTheme.typography.bodyLarge)
                        Text("Last Name: $lastName", style = MaterialTheme.typography.bodyLarge)
                        Text(genderText, style = MaterialTheme.typography.bodyLarge)
                        Text("Birth Date: $birthDate", style = MaterialTheme.typography.bodyLarge)
                        Text("Age: $age years", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                ProfileButtons(context)
            }
        }
    }
}

@Composable
fun ProfileButtons(context: Context) {
    val buttons = listOf(
        "Start Assessment" to AssessmentActivity::class.java,
        "Edit Profile" to EditProfileActivity::class.java,
        "Track Mood" to MoodTrackingActivity::class.java,
        "Chat With AI" to ChatActivity::class.java,
        "Write Notes" to NotesActivity::class.java
    )

    buttons.forEach { (text, activity) ->
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { context.startActivity(Intent(context, activity)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text)
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
    }

fun calculateAge(birthDateString: String): Int {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val birthDate = sdf.parse(birthDateString) ?: return -1
        val today = Calendar.getInstance()
        val birthCalendar = Calendar.getInstance().apply { time = birthDate }

        var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        age
    } catch (e: Exception) {
        -1
    }
}
