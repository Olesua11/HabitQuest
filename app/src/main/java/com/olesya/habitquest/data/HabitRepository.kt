package com.olesya.habitquest.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class HabitRepository(private val db: AppDatabase) {
    private val habitDao = db.habitDao()
    private val profileDao = db.profileDao()

    val habits: Flow<List<HabitEntity>> = habitDao.observeHabits()
    val profile: Flow<ProfileEntity> = profileDao.observeProfile().map { it ?: ProfileEntity() }

    val dashboard = combine(habits, profile) { habits, profile ->
        DashboardState(habits, profile)
    }

    suspend fun seedIfNeeded() {
        if (habitDao.countHabits() == 0) {
            habitDao.insertHabits(
                listOf(
                    HabitEntity(title = "Drink 2L water", emoji = "💧", difficulty = "Easy", xpReward = 20, coinReward = 5, streak = 8, totalCompletions = 17),
                    HabitEntity(title = "Read 30 minutes", emoji = "📚", difficulty = "Medium", xpReward = 40, coinReward = 10, streak = 4, totalCompletions = 9),
                    HabitEntity(title = "Workout", emoji = "🏃", difficulty = "Hard", xpReward = 60, coinReward = 20, streak = 2, totalCompletions = 6),
                    HabitEntity(title = "No phone after 23:00", emoji = "🌙", difficulty = "Medium", xpReward = 40, coinReward = 10, streak = 6, totalCompletions = 14)
                )
            )
        }
        if (profileDao.observeProfileValue() == null) profileDao.save(ProfileEntity())
    }

    suspend fun addHabit(title: String, emoji: String, difficulty: String) {
        val rewards = when (difficulty) {
            "Hard" -> 60 to 20
            "Medium" -> 40 to 10
            else -> 20 to 5
        }
        habitDao.insertHabit(HabitEntity(title = title.trim(), emoji = emoji, difficulty = difficulty, xpReward = rewards.first, coinReward = rewards.second))
    }

    suspend fun completeHabit(habit: HabitEntity, currentProfile: ProfileEntity) {
        if (habit.completedToday) return
        val today = LocalDate.now()
        val previous = habit.lastCompletedDate?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
        val newStreak = when {
            previous == null -> maxOf(1, habit.streak)
            ChronoUnit.DAYS.between(previous, today) == 1L -> habit.streak + 1
            ChronoUnit.DAYS.between(previous, today) == 0L -> habit.streak
            else -> 1
        }
        habitDao.updateHabit(
            habit.copy(
                completedToday = true,
                lastCompletedDate = today.toString(),
                streak = newStreak,
                totalCompletions = habit.totalCompletions + 1
            )
        )
        profileDao.save(currentProfile.copy(xp = currentProfile.xp + habit.xpReward, coins = currentProfile.coins + habit.coinReward))
    }

    suspend fun deleteHabit(id: Int) = habitDao.deleteHabit(id)
}

data class DashboardState(
    val habits: List<HabitEntity> = emptyList(),
    val profile: ProfileEntity = ProfileEntity()
)

private suspend fun ProfileDao.observeProfileValue(): ProfileEntity? = observeProfile().first()
