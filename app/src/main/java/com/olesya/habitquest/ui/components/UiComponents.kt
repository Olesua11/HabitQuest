package com.olesya.habitquest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olesya.habitquest.data.HabitEntity
import com.olesya.habitquest.ui.theme.*

@Composable
fun HeroCard(level: Int, xpInLevel: Int, xpNeeded: Int, coins: Int) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(Brush.linearGradient(listOf(Color(0xFF3C2B72), Color(0xFF18132E), Color(0xFF14362D))))
                .padding(22.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = .12f)),
                        contentAlignment = Alignment.Center
                    ) { Text("🧙", fontSize = 30.sp) }
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Adventurer", fontSize = 13.sp, color = TextMuted)
                        Text("Level $level", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Surface(shape = RoundedCornerShape(18.dp), color = Gold.copy(alpha = .14f)) {
                        Text("🪙 $coins", color = Gold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), fontWeight = FontWeight.Bold)
                    }
                }
                LinearProgressIndicator(
                    progress = (xpInLevel.toFloat() / xpNeeded.coerceAtLeast(1)).coerceIn(0f, 1f),
                    modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                    color = Purple,
                    trackColor = Color.White.copy(alpha = .1f)
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("$xpInLevel XP", color = TextMuted, fontSize = 12.sp)
                    Text("$xpNeeded XP to next level", color = TextMuted, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun HabitCard(habit: HabitEntity, onComplete: () -> Unit, onDelete: () -> Unit) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Panel),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(48.dp).clip(RoundedCornerShape(16.dp)).background(PanelSoft),
                    contentAlignment = Alignment.Center
                ) { Text(habit.emoji, fontSize = 24.sp) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(habit.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalFireDepartment, null, tint = Rose, modifier = Modifier.size(16.dp))
                        Text(" ${habit.streak} day streak", color = TextMuted, fontSize = 12.sp)
                    }
                }
                Text("+${habit.xpReward} XP", color = Mint, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text(habit.difficulty) })
                AssistChip(onClick = {}, label = { Text("🪙 +${habit.coinReward}") })
                Spacer(Modifier.weight(1f))
                TextButton(onClick = onDelete) { Text("Delete", color = TextMuted) }
                Button(
                    onClick = onComplete,
                    enabled = !habit.completedToday,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (habit.completedToday) PanelSoft else Violet)
                ) {
                    if (habit.completedToday) Icon(Icons.Default.Check, null)
                    Text(if (habit.completedToday) " Done" else "Complete")
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, emoji: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Panel)) {
        Column(Modifier.padding(16.dp)) {
            Text(emoji, fontSize = 24.sp)
            Spacer(Modifier.height(12.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(label, color = TextMuted, fontSize = 12.sp)
        }
    }
}
