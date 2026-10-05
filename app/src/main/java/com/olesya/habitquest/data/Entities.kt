package com.olesya.habitquest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val emoji: String,
    val difficulty: String,
    val xpReward: Int,
    val coinReward: Int,
    val streak: Int = 0,
    val completedToday: Boolean = false,
    val lastCompletedDate: String? = null,
    val totalCompletions: Int = 0
)

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val xp: Int = 120,
    val coins: Int = 80
)
