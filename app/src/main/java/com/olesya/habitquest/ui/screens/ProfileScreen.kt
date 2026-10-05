package com.olesya.habitquest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olesya.habitquest.MainViewModel
import com.olesya.habitquest.ui.theme.*

@Composable
fun ProfileScreen(viewModel: MainViewModel) {
    val state by viewModel.dashboard.collectAsStateWithLifecycle()
    val level = levelForXp(state.profile.xp)
    val rank = when {
        level >= 20 -> "Master"
        level >= 10 -> "Hero"
        level >= 5 -> "Adventurer"
        else -> "Novice"
    }

    Column(
        Modifier.fillMaxSize().padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(Violet, Mint))),
            contentAlignment = Alignment.Center
        ) { Text("🧙", fontSize = 58.sp) }
        Text("Olesya", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text("$rank • Level $level", color = Purple, fontWeight = FontWeight.SemiBold)

        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Panel), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Hero inventory", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    ProfileStat("XP", state.profile.xp.toString())
                    ProfileStat("Coins", state.profile.coins.toString())
                    ProfileStat("Quests", state.habits.sumOf { it.totalCompletions }.toString())
                }
                HorizontalDivider(color = PanelSoft)
                Text("Next unlock", color = TextMuted, fontSize = 12.sp)
                Text(if (level < 5) "✨ Adventurer rank at level 5" else "🐉 New legendary rewards coming soon", fontWeight = FontWeight.Medium)
            }
        }

        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Panel), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("About HabitQuest", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Turn everyday habits into quests, build streaks and level up your hero one small win at a time.", color = TextMuted, lineHeight = 20.sp)
                Text("v1.0 • Local-first • No account required", color = Mint, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun ProfileStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(label, color = TextMuted, fontSize = 12.sp)
    }
}
