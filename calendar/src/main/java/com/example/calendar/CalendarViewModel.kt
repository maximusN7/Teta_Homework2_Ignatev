package com.example.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.calendar.repository.IJobsRepository
import com.example.data.utils.doOnError
import com.example.data.utils.doOnSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(private val repository: IJobsRepository) : ViewModel() {
    private val _state: MutableStateFlow<CalendarState> = MutableStateFlow(CalendarState.Empty)
    val state = _state.asStateFlow()

    private val _buttonState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val buttonState = _buttonState.asStateFlow()

    private val _currentDay: MutableStateFlow<String> = MutableStateFlow("")
    val currentDay = _currentDay.asStateFlow()
    private val _currentMonth: MutableStateFlow<String> = MutableStateFlow("May")
    val currentMonth = _currentMonth.asStateFlow()
    private val _currentYear: MutableStateFlow<Int> = MutableStateFlow(2021)
    val currentYear = _currentYear.asStateFlow()

    private val _currentMonthDays: MutableStateFlow<List<List<String>>> = MutableStateFlow(listOf(listOf("")))
    val currentMonthDays = _currentMonthDays.asStateFlow()

    init {
        initCalendarScreen()
    }

    fun initCalendarScreen() {
        getCalendar()
        getDaysData()
    }

    fun getJobsData() {
        viewModelScope.launch {
            _state.emit(CalendarState.Empty)
            repository.getJobsData().collect {
                it.doOnError {

                }.doOnSuccess { jobs ->
                    _state.emit(CalendarState.JobsScreen(jobs))
                }
            }
        }
    }

    private fun getCalendar() {
        viewModelScope.launch {
            _state.emit(CalendarState.Empty)
            repository.getCalendarData().collect {
                it.doOnError {

                }.doOnSuccess { calendarInfo ->
                    _state.emit(CalendarState.CalendarScreen(calendarInfo))
                }
            }
        }
    }

    fun getDaysData() {
        viewModelScope.launch {
            _currentMonthDays.emit(listOf(listOf("")))
            repository.getDaysData().collect {
                it.doOnError {

                }.doOnSuccess { currentMonthDays ->
                    _currentMonthDays.emit(currentMonthDays)
                }
            }
        }
    }

    fun setCurrentPressed(currentDay: String) {
        _currentDay.value = currentDay
    }

    fun setMonth(currentMonth: String) {
        _currentMonth.value = currentMonth
    }

    fun setYear(currentYear: Int) {
        _currentYear.value = currentYear
    }
}