package com.olesya.habitquest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olesya.habitquest.MainViewModel
import com.olesya.habitquest.ui.components.HabitCard
import com.olesya.habitquest.ui.components.HeroCard
import com.olesya.habitquest.ui.theme.*

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val state by viewModel.dashboard.collectAsStateWithLifecycle()
    var showAdd by remember { mutableStateOf(false) }
    val level = levelForXp(state.profile.xp)
    val levelStart = xpForLevel(level)
    val nextLevel = xpForLevel(level + 1)

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("HABITQUEST", color = Purple, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                        Text("Today's quests", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        Text("Tiny wins. Epic progress.", color = TextMuted)
                    }
                    FilledIconButton(onClick = { showAdd = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add habit")
                    }
                }
            }
            item {
                HeroCard(
                    level = level,
                    xpInLevel = state.profile.xp - levelStart,
                    xpNeeded = nextLevel - levelStart,
                    coins = state.profile.coins
                )
            }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Daily missions", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    val done = state.habits.count { it.completedToday }
                    Text("$done/${state.habits.size} done", color = Mint, fontSize = 13.sp)
                }
            }
            items(state.habits, key = { it.id }) { habit ->
                HabitCard(
                    habit = habit,
                    onComplete = { viewModel.completeHabit(habit) },
                    onDelete = { viewModel.deleteHabit(habit) }
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }

    if (showAdd) {
        AddHabitDialog(
            onDismiss = { showAdd = false },
            onAdd = { title, emoji, difficulty ->
                viewModel.addHabit(title, emoji, difficulty)
                showAdd = false
            }
        )
    }
}

@Composable
private fun AddHabitDialog(onDismiss: () -> Unit, onAdd: (String, String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf("⚡") }
    var difficulty by remember { mutableStateOf("Medium") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create a new quest") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Habit") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = emoji,
                    onValueChange = { emoji = it.take(2) },
                    label = { Text("Emoji") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Easy", "Medium", "Hard").forEach { option ->
                        FilterChip(
                            selected = difficulty == option,
                            onClick = { difficulty = option },
                            label = { Text(option) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onAdd(title, emoji.ifBlank { "⚡" }, difficulty) }, enabled = title.isNotBlank()) {
                Text("Create")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

fun levelForXp(xp: Int): Int {
    var level = 1
    while (xp >= xpForLevel(level + 1)) level++
    return level
}

fun xpForLevel(level: Int): Int = when {
    level <= 1 -> 0
    else -> ((level - 1) * (level - 1) * 125)
}
