package com.example.data.calendar.repository

import android.provider.CalendarContract
import com.example.data.calendar.CurrentCalendar
import com.example.data.calendar.ListItem
import com.example.data.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JobsRepositoryMock : IJobsRepository {
    override fun getJobsData(): Flow<Result<List<ListItem>, Throwable>> {
        return flow {
            val mockJobList = arrayListOf<ListItem>(
                ListItem.TodayJob("00", "00", "Спать"),
                ListItem.TodayJob("01", "00", "Спать"),
                ListItem.TodayJob("02", "00", "Спать"),
                ListItem.TodayJob("03", "00", "Спать"),
                ListItem.TodayJob("04", "00", "Спать"),
                ListItem.TodayJob("05", "00", "Спать"),
                ListItem.TodayJob("06", "00", "Подъем"),
                ListItem.TodayJob("07", "00", "И так далее список дел"),
                ListItem.TodayJob("08", "00", "Чтобы выходил за пределы экрана"),
                ListItem.TodayJob(
                    "09",
                    "00",
                    "Для желающих усложнить можно попробовать сделать список с разными ViewType (заголовок “День” и список дел, заголовок “Утро” и список дел)"
                ),
                ListItem.TodayJob(
                    "10",
                    "00",
                    "Для желающих усложнить можно попробовать сделать список с разными ViewType (заголовок “День” и список дел, заголовок “Утро” и список дел)"
                ),
                ListItem.TodayJob(
                    "16",
                    "00",
                    "Для желающих усложнить можно попробовать сделать список с разными ViewType (заголовок “День” и список дел, заголовок “Утро” и список дел)"
                ),
                ListItem.TodayJob(
                    "20",
                    "00",
                    "Для желающих усложнить можно попробовать сделать список с разными ViewType (заголовок “День” и список дел, заголовок “Утро” и список дел)"
                ),
            )
            mockJobList.add(0, ListItem.JobHeader("Ночь"))
            var isMorningAdded = false
            var isDayAdded = false
            var isEveningAdded = false

            val list = mutableListOf<ListItem>()
            for (job in mockJobList) {
                if (job is ListItem.TodayJob) {
                    when {
                        (job.hours.toInt() >= 6 && !isMorningAdded) -> {
                            list.add(ListItem.JobHeader("Утро"))
                            isMorningAdded = true
                        }
                        (job.hours.toInt() >= 11 && !isDayAdded) -> {
                            list.add(ListItem.JobHeader("День"))
                            isDayAdded = true
                        }
                        (job.hours.toInt() >= 18 && !isEveningAdded) -> {
                            list.add(ListItem.JobHeader("Вечер"))
                            isEveningAdded = true
                        }
                    }
                }
                list.add(job)
            }
            emit(Result.Success(list.toList()))
        }
    }

    override fun getCalendarData(): Flow<Result<CurrentCalendar, Throwable>> {
        return flow {
            emit(Result.Success(CurrentCalendar("May", 2021)))
        }
    }

    override fun getDaysData(
        currentMonth: String,
        previousMonth: List<List<String>>
    ): Flow<Result<List<List<String>>, Throwable>> {
        return flow {
            emit(Result.Success(generateMonth(currentMonth, previousMonth)))
        }
    }


    private fun generateMonth(
        currentMonth: String,
        previousMonth: List<List<String>>
    ): List<List<String>> {
        return if (previousMonth.size == 1) {
            listOf(
                listOf("", "", "", "", "", "1", "2"),
                listOf("3", "4", "5", "6", "7", "8", "9"),
                listOf("10", "11", "12", "13", "14", "15", "16"),
                listOf("17", "18", "19", "20", "21", "22", "23"),
                listOf("24", "25", "26", "27", "28", "29", "30"),
                listOf("31", "", "", "", "", "", "")
            )
        } else {
            val mask = Array(6) { _ -> Array(7) { _ -> "" } }
            val setOfDays = when (currentMonth) {
                "January", "March", "May", "July", "August", "October", "December" -> Array(31) { i -> (i + 1).toString() }
                "April", "June", "September", "November" -> Array(30) { i -> (i + 1).toString() }
                else -> Array(28) { i -> (i + 1).toString() }
            }
            var step = 0
            for (a in previousMonth[5]) {
                if (a == "") step++
            }
            if (step == 7) {
                step = 0
                for (a in previousMonth[4]) {
                    if (a == "") step++
                }
            }
            step = if (7 - step != 7) 7 - step else 0
            val nextMonthArray = List(6) { i ->
                List(7) { j ->
                    when {
                        (i == 0 && j < step) -> mask[i][j]
                        (setOfDays.size > i * 7 + j - step) -> {
                            val a = setOfDays[i * 7 + j - step]
                            a
                        }
                        else -> mask[i][j]
                    }
                }
            }
            nextMonthArray
        }
    }
}