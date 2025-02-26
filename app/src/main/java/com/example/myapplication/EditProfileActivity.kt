package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.util.Calendar

class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditProfileScreen()
        }
    }
}

@Composable
fun EditProfileScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    var username by remember { mutableStateOf(sharedPreferences.getString("username", "") ?: "") }
    var bio by remember { mutableStateOf(sharedPreferences.getString("bio", "") ?: "") }
    var selectedGender by remember { mutableStateOf(sharedPreferences.getString("gender", "Male") ?: "Male") }
    var birthDate by remember { mutableStateOf(sharedPreferences.getString("birth_date", "Select Date") ?: "Select Date") }
    var age by remember { mutableStateOf(sharedPreferences.getInt("age", 18)) }

    val showDatePicker = remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

    if (showDatePicker.value) {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                birthDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                val calculatedAge = calculateAge(selectedYear, selectedMonth, selectedDay)
                age = calculatedAge

                editor.putString("birth_date", birthDate)
                editor.putInt("age", calculatedAge)
                editor.apply()

                showDatePicker.value = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    val genderOptions = listOf("Male", "Female")
    val genderImages = listOf(
        painterResource(id = R.drawable.male_image),
        painterResource(id = R.drawable.female_image)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Gender:")
        Column(modifier = Modifier.selectableGroup()) {
            genderOptions.forEachIndexed { index, gender ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (gender == selectedGender),
                            onClick = { selectedGender = gender }
                        )
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = (gender == selectedGender),
                        onClick = { selectedGender = gender }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = genderImages[index],
                        contentDescription = gender,
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = gender)
                }
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        Text(text = "Birth Date: $birthDate", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { showDatePicker.value = true },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Select Birth Date")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                editor.apply {
                    putString("username", username)
                    putString("bio", bio)
                    putString("gender", selectedGender)
                    apply()
                }
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}

fun calculateAge(year: Int, month: Int, day: Int): Int {
    val today = Calendar.getInstance()
    var age = today.get(Calendar.YEAR) - year

    if (today.get(Calendar.MONTH) < month ||
        (today.get(Calendar.MONTH) == month && today.get(Calendar.DAY_OF_MONTH) < day)
    ) {
        age--
    }
    return age
}
