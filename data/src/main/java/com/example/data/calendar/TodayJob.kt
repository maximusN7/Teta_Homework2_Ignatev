package com.example.data.calendar

sealed class ListItem {
    data class TodayJob(
        val hours: String,
        val minutes: String,
        val job: String
    ) : ListItem()

    data class JobHeader(
        val title: String
    ) : ListItem()
}
