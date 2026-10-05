package com.olesya.habitquest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olesya.habitquest.MainViewModel
import com.olesya.habitquest.ui.theme.*

private data class Achievement(val emoji: String, val title: String, val description: String, val unlocked: Boolean, val progress: String)

@Composable
fun AchievementsScreen(viewModel: MainViewModel) {
    val state by viewModel.dashboard.collectAsStateWithLifecycle()
    val total = state.habits.sumOf { it.totalCompletions }
    val bestStreak = state.habits.maxOfOrNull { it.streak } ?: 0
    val level = levelForXp(state.profile.xp)
    val unlockedToday = state.habits.count { it.completedToday }
    val achievements = listOf(
        Achievement("🌱", "First Step", "Complete your first quest", total >= 1, "${minOf(total, 1)}/1"),
        Achievement("🔥", "On Fire", "Reach a 7 day streak", bestStreak >= 7, "${minOf(bestStreak, 7)}/7"),
        Achievement("⚔️", "Quest Hunter", "Complete 25 quests", total >= 25, "${minOf(total, 25)}/25"),
        Achievement("👑", "Rising Hero", "Reach level 5", level >= 5, "${minOf(level, 5)}/5"),
        Achievement("✨", "Perfect Day", "Finish every daily mission", state.habits.isNotEmpty() && unlockedToday == state.habits.size, "$unlockedToday/${state.habits.size}"),
        Achievement("🐉", "Legend", "Complete 100 quests", total >= 100, "${minOf(total, 100)}/100")
    )

    LazyColumn(
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Achievements", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("Your collection of victories", color = TextMuted)
        }
        items(achievements) { achievement -> AchievementCard(achievement) }
    }
}

@Composable
private fun AchievementCard(a: Achievement) {
    Card(
        colors = CardDefaults.cardColors(containerColor = if (a.unlocked) Panel else Panel.copy(alpha = .7f)),
        shape = RoundedCornerShape(22.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(54.dp).background(if (a.unlocked) Gold.copy(alpha = .14f) else Color.White.copy(alpha = .05f), RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (a.unlocked) Text(a.emoji, fontSize = 28.sp) else Icon(Icons.Default.Lock, null, tint = TextMuted)
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(a.title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(a.description, color = TextMuted, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = if (a.unlocked) 1f else .45f,
                    modifier = Modifier.fillMaxWidth(),
                    color = if (a.unlocked) Gold else Purple,
                    trackColor = Color.White.copy(alpha = .08f)
                )
            }
            Spacer(Modifier.width(12.dp))
            Text(a.progress, color = if (a.unlocked) Mint else TextMuted, fontSize = 12.sp)
        }
    }
}
