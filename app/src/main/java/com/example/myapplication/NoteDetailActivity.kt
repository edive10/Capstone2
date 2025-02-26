package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class NoteDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val day = intent.getIntExtra("day", 1)
        setContent {
            NoteDetailScreen(day)
        }
    }
}

@Composable
fun NoteDetailScreen(day: Int) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("Notes", Context.MODE_PRIVATE)
    var noteText by remember { mutableStateOf(sharedPreferences.getString("note_$day", "") ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Notes for Day $day", fontSize = 22.sp, color = Color(0xFF3A322F))

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = noteText,
            onValueChange = { noteText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White, shape = MaterialTheme.shapes.small)
                .padding(8.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                sharedPreferences.edit().putString("note_$day", noteText).apply()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(text = "Save Note", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, NotesActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(text = "Back", fontSize = 18.sp, color = Color.White)
        }
    }
}
