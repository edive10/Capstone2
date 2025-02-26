package com.example.myapplication

import android.content.Intent
import android.media.MediaPlayer
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class MusicPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerScreen()
        }
    }
}

@Composable
fun MusicPlayerScreen() {
    val context = LocalContext.current

    val songs = listOf(
        "Relaxing Music" to R.raw.sample_music,
        "Calm Piano" to R.raw.lostsoul,
        "Soft Guitar" to R.raw.raphael_novarina,
        "Relaxation" to R.raw.deepintheforest
    )

    var currentIndex by remember { mutableStateOf(0) }
    var currentSong by remember { mutableStateOf(songs[currentIndex]) }

    var mediaPlayer by remember {
        mutableStateOf(MediaPlayer.create(context, currentSong.second))
    }

    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var currentTime by remember { mutableStateOf(0) }
    var totalTime by remember { mutableStateOf(mediaPlayer.duration) }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            progress = if (mediaPlayer.duration > 0) {
                mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration
            } else 0f
            currentTime = mediaPlayer.currentPosition
            delay(1000)
        }
    }

    fun changeSong(newIndex: Int) {
        if (newIndex in songs.indices) {
            mediaPlayer.stop()
            mediaPlayer.release()
            currentIndex = newIndex
            currentSong = songs[currentIndex]
            mediaPlayer = MediaPlayer.create(context, currentSong.second)
            totalTime = mediaPlayer.duration
            mediaPlayer.start()
            isPlaying = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F1EB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Music Player", fontSize = 22.sp, color = Color(0xFF3A322F))

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = currentSong.first, fontSize = 20.sp, color = Color(0xFF3A322F))

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${formatTime(currentTime)} / ${formatTime(totalTime)}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = progress,
            onValueChange = {},
            valueRange = 0f..1f,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFCE7C44),
                activeTrackColor = Color(0xFFCE7C44),
                inactiveTrackColor = Color(0xFFDCCEC7)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { if (currentIndex > 0) changeSong(currentIndex - 1) },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3A322F))
            ) {
                Text("⏮", fontSize = 24.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = {
                    if (isPlaying) {
                        mediaPlayer.pause()
                    } else {
                        mediaPlayer.start()
                    }
                    isPlaying = !isPlaying
                },
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFCE7C44))
            ) {
                Text(if (isPlaying) "⏸" else "▶", fontSize = 24.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = { if (currentIndex < songs.lastIndex) changeSong(currentIndex + 1) },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3A322F))
            ) {
                Text("⏭", fontSize = 24.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Song List", fontSize = 18.sp, color = Color(0xFF3A322F))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(songs) { song ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFFE5DCD2))
                        .clickable {
                            val newIndex = songs.indexOf(song)
                            changeSong(newIndex)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = song.first,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp),
                        color = Color(0xFF3A322F)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A322F))
        ) {
            Text(text = "Back to Profile", fontSize = 18.sp, color = Color.White)
        }
    }
}

fun formatTime(millis: Int): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) % 60
    return String.format("%02d:%02d", minutes, seconds)
}
