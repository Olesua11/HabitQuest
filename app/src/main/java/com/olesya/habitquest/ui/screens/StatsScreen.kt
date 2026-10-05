package com.olesya.habitquest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olesya.habitquest.MainViewModel
import com.olesya.habitquest.ui.components.StatCard
import com.olesya.habitquest.ui.theme.*

@Composable
fun StatsScreen(viewModel: MainViewModel) {
    val state by viewModel.dashboard.collectAsStateWithLifecycle()
    val total = state.habits.sumOf { it.totalCompletions }
    val bestStreak = state.habits.maxOfOrNull { it.streak } ?: 0
    val doneToday = state.habits.count { it.completedToday }
    val completion = if (state.habits.isEmpty()) 0 else doneToday * 100 / state.habits.size

    LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Text("Statistics", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("See how your hero is growing", color = TextMuted)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Completed quests", total.toString(), "⚔️", Modifier.weight(1f))
                StatCard("Best streak", "$bestStreak days", "🔥", Modifier.weight(1f))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Today", "$completion%", "✨", Modifier.weight(1f))
                StatCard("Current level", levelForXp(state.profile.xp).toString(), "👑", Modifier.weight(1f))
            }
        }
        item {
            Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = Panel)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Quest breakdown", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    state.habits.take(5).forEach { habit ->
                        Column {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${habit.emoji} ${habit.title}")
                                Text("${habit.totalCompletions}", color = Mint)
                            }
                            LinearProgressIndicator(
                                progress = (habit.totalCompletions.coerceAtMost(30) / 30f),
                                modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                                color = Purple
                            )
                        }
                    }
                }
            }
        }
    }
}
