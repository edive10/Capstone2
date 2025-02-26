package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(currentScreen: String) {
    val context = LocalContext.current

    NavigationBar(
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentScreen == "Home",
            onClick = {
                navigateToScreen(context, ProfileActivity::class.java)
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Assessment") },
            label = { Text("Assessment") },
            selected = currentScreen == "Assessment",
            onClick = {
                navigateToScreen(context, AssessmentActivity::class.java)
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Edit Profile") },
            selected = currentScreen == "Edit Profile",
            onClick = {
                navigateToScreen(context, EditProfileActivity::class.java)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Music") },
            label = { Text("Music") },
            selected = currentScreen == "Music",
            onClick = {
                navigateToScreen(context, MusicPlayerActivity::class.java)
            }
        )
    }
}

fun navigateToScreen(context: Context, activity: Class<*>) {
    val intent = Intent(context, activity)
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar("Home")
}
