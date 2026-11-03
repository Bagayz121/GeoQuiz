package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.geoquiz.ui.theme.GeoQuizTheme

data class Question(val text: String, val answer: Boolean)

val questionBank = listOf(
    Question("Canberra is the capital of Australia.", true),
    Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
    Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
    Question("The source of the Nile River is in Egypt.", false),
    Question("The Amazon River is the longest river in the Americas.", true),
    Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
)

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

@Composable
fun QuizScreen(modifier: Modifier = Modifier) {
    var currentIndex by remember { mutableStateOf(0) }
    // Состояние: ответил ли пользователь на текущий вопрос
    var answered by remember { mutableStateOf(false) }
    // Состояние: был ли ответ правильным
    var isCorrect by remember { mutableStateOf(false) }

    val currentQuestion = questionBank[currentIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = currentQuestion.text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))

        // Если не ответили - показываем кнопки. Если ответили - скрываем и показываем текст
        if (!answered) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    isCorrect = (true == currentQuestion.answer)
                    answered = true
                }) { Text("TRUE") }

                Button(onClick = {
                    isCorrect = (false == currentQuestion.answer)
                    answered = true
                }) { Text("FALSE") }
            }
        } else {
            Text(
                text = if (isCorrect) "Correct!" else "Incorrect!",
                style = MaterialTheme.typography.titleLarge,
                color = if (isCorrect) androidx.compose.ui.graphics.Color.Green else androidx.compose.ui.graphics.Color.Red
            )
        }

        Spacer(Modifier.height(32.dp))

        // Кнопка Next теперь также сбрасывает состояние answered
        Button(
            onClick = {
                if (currentIndex < questionBank.size - 1) {
                    currentIndex++
                    answered = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("NEXT")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    GeoQuizTheme {
        QuizScreen()
    }
}