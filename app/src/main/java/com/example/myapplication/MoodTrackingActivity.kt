package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

class MoodTrackingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val physicalDistress = intent.getIntExtra("physical_distress", 0) // مقدار دریافتی

        val medicationList = intent.getStringExtra("medication_list")

        if (!medicationList.isNullOrEmpty()) {
            sharedPreferences.edit().putString("medication_list", medicationList).apply()
        }

        setContent {
            MoodTrackingScreen(physicalDistress)
        }
    }
}

@Composable
fun MoodTrackingScreen(physicalDistress: Int) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val sharedPreferencesHelp = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    val assessmentResult = sharedPreferences.getString("assessment_result", "No Data")
    val sleepQuality = sharedPreferences.getString("sleep_quality", "No data available") ?: "No data available"
    val stressLevel = sharedPreferences.getInt("STRESS_LEVEL", -1)
    val professionalHelp = sharedPreferencesHelp.getString("ProfessionalHelp", "No data available") ?: "No data available"
    val medicationList = sharedPreferences.getString("medication_list", "No medication recorded") ?: "No medication recorded"
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(24.dp)
            .verticalScroll(scrollState),  // اضافه کردن قابلیت اسکرول
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mood Tracking",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A322F)
        )

        Spacer(modifier = Modifier.height(24.dp))

        InfoBox(text = "Sleep Quality: $sleepQuality")
        Spacer(modifier = Modifier.height(16.dp))
        InfoBox(text = "Stress Level: ${getStressEmoji(stressLevel)} $stressLevel")
        Spacer(modifier = Modifier.height(16.dp))
        InfoBox(
            text = "Any Professional Help: $professionalHelp",
            textColor = if (professionalHelp == "No") Color(0xFFFF4081) else Color(0xFF3ADE3A)
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoBox(text = "Medication: $medicationList")
        Spacer(modifier = Modifier.height(16.dp))
        InfoBox(text = "Physical Distress: ${if (physicalDistress == 1) "Yes, one or multiple" else "I'm experiencing physical pain in different places over my body."}")

        Spacer(modifier = Modifier.height(32.dp))
        InfoBox(text = "Your Mood Goal: $assessmentResult")
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(text = "Go to Profile", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                val moodData = """
            Sleep Quality: $sleepQuality
            Stress Level: ${getStressEmoji(stressLevel)} $stressLevel
            Any Professional Help: $professionalHelp
            Medication: $medicationList
            Physical Distress: ${if (physicalDistress == 1) "Yes" else "No"}
            Your Mood Goal: $assessmentResult
        """.trimIndent()

                exportMoodTrackingToPDF(context, moodData)
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(text = "Export to PDF", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { sharePDF(context) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(text = "Send to ChatGPT", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun InfoBox(text: String, textColor: Color = Color(0xFF6D6460)) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = textColor)
    }
}

fun getStressEmoji(stressLevel: Int): String {
    return when (stressLevel) {
        1 -> "😌"
        2 -> "🙂"
        3 -> "😐"
        4 -> "😟"
        5 -> "😖"
        else -> "❓"
    }
}
fun exportMoodTrackingToPDF(context: Context, moodData: String) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas
    val paint = Paint()

    paint.textSize = 16f
    paint.isFakeBoldText = true

    val lines = moodData.split("\n")
    var yPosition = 50f

    for (line in lines) {
        canvas.drawText(line, 50f, yPosition, paint)
        yPosition += 30f
    }

    pdfDocument.finishPage(page)

    val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MoodTracking.pdf")

    try {
        val outputStream = FileOutputStream(directory)
        pdfDocument.writeTo(outputStream)
        pdfDocument.close()
        outputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
fun sharePDF(context: Context) {
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        "MoodTracking.pdf"
    )

    if (!file.exists()) {
        return
    }

    val uri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Share PDF via"))
}
