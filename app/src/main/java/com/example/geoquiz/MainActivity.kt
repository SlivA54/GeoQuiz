package com.example.geoquiz

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.geoquiz.ui.theme.GeoQuizTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Question(val text: String, val answer: Boolean)

val questions = listOf(
    Question("Canberra is the capital of Australia.", true),
    Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
    Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
    Question("The source of the Nile River is in Egypt.", false),
    Question("The Amazon River is the longest river in the Americas.", true),
    Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
)

@Composable
fun QuizScreen(modifier: Modifier = Modifier) {

    val currentIndex = remember { mutableStateOf(0) }
    val isAnswered = remember { mutableStateOf(false) }
    val userScore = remember { mutableStateOf(0) }
    val isQuizFinished = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val question = questions[currentIndex.value]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = question.text,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        if (!isAnswered.value && !isQuizFinished.value) {
            Row {
                Button(
                    onClick = {
                        isAnswered.value = true
                        if (question.answer) userScore.value++
                    },
                    modifier = Modifier.padding(8.dp)
                ) { Text("True") }

                Button(
                    onClick = {
                        isAnswered.value = true
                        if (!question.answer) userScore.value++
                    },
                    modifier = Modifier.padding(8.dp)
                ) { Text("False") }
            }
        }

        if (isAnswered.value && !isQuizFinished.value) {
            Button(
                onClick = {
                    if (currentIndex.value < questions.size - 1) {
                        currentIndex.value++
                        isAnswered.value = false
                    } else {
                        isQuizFinished.value = true
                        Toast.makeText(context, "Правильных: ${userScore.value} из ${questions.size}", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) { Text("Next") }
        }

        if (isQuizFinished.value) {
            Text(
                text = "Правильных ответов: ${userScore.value} из ${questions.size}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

