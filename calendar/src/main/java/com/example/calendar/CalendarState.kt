package com.example.calendar

import com.example.data.calendar.CurrentCalendar
import com.example.data.calendar.ListItem

sealed class CalendarState {
    object Empty : CalendarState()
    data class CalendarScreen(val calendarInfo: CurrentCalendar) : CalendarState()
    data class JobsScreen(val jobsList: List<ListItem>) : CalendarState()
}