package com.example.data.calendar.repository

import com.example.data.calendar.CurrentCalendar
import com.example.data.calendar.ListItem
import kotlinx.coroutines.flow.Flow
import com.example.data.utils.Result

interface IJobsRepository {
    fun getJobsData(): Flow<Result<List<ListItem>, Throwable>>
    fun getCalendarData(): Flow<Result<CurrentCalendar, Throwable>>
    fun getDaysData(): Flow<Result<List<List<String>>, Throwable>>
}