package com.olesya.habitquest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.olesya.habitquest.data.AppDatabase
import com.olesya.habitquest.data.DashboardState
import com.olesya.habitquest.data.HabitEntity
import com.olesya.habitquest.data.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HabitRepository(AppDatabase.get(application))

    val dashboard = repository.dashboard.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        DashboardState()
    )

    init {
        viewModelScope.launch { repository.seedIfNeeded() }
    }

    fun addHabit(title: String, emoji: String, difficulty: String) {
        if (title.isBlank()) return
        viewModelScope.launch { repository.addHabit(title, emoji, difficulty) }
    }

    fun completeHabit(habit: HabitEntity) {
        viewModelScope.launch { repository.completeHabit(habit, dashboard.value.profile) }
    }

    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch { repository.deleteHabit(habit.id) }
    }
}
