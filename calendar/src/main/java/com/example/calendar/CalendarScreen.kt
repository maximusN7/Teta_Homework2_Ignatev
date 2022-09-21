package com.example.calendar

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.calendar.ui.DesignSystemTheme
import com.example.data.calendar.ListItem
import com.example.data.utils.ResourceProvider
import java.util.*

@Composable
fun CalendarScreen(viewModel: CalendarViewModel, resources: ResourceProvider) {
    val state = viewModel.state.collectAsState()
    val currentDay = viewModel.currentDay.collectAsState()
    val currentMonth = viewModel.currentMonth.collectAsState()
    val currentYear = viewModel.currentYear.collectAsState()
    val currentMonthDays = viewModel.currentMonthDays.collectAsState()
    val buttonState = viewModel.buttonState.collectAsState()
    when (currentDay.value) {
        "" -> viewModel.setButtonState(false)
        else -> viewModel.setButtonState(true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (state.value) {
            is CalendarState.CalendarScreen -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MonthAndYearBar(currentMonth.value, currentYear.value, resources, viewModel)
                    DaysOfTheWeekTitles(resources)
                    DaysOfTheWeek(currentMonthDays.value, viewModel)
                    ButtonForJobs(buttonState.value, resources, viewModel)
                }
            }
            is CalendarState.JobsScreen -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = resources.getString("job_list_title").format(currentDay.value),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(bottom = 13.dp, top = 24.dp, start = 21.dp)
                            .size(width = 180.dp, height = 24.dp),
                        style = DesignSystemTheme.typography.h3.medium
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(start = 28.dp, end = 23.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (state.value is CalendarState.JobsScreen) {
                            val list = (state.value as CalendarState.JobsScreen).jobsList
                            items(items = list) { listItem ->
                                when (listItem) {
                                    is ListItem.TodayJob -> ListItemJob(
                                        resources = resources,
                                        job = listItem
                                    )
                                    is ListItem.JobHeader -> ListItemHeader(
                                        resources = resources,
                                        header = listItem
                                    )
                                }
                            }
                        }

                    }
                }
                val onBack = { viewModel.getCalendarScreen() }
                BackPressHandler(onBackPressed = onBack)
            }
            is CalendarState.Empty -> {

            }
        }
    }
}

@Composable
fun MonthAndYearBar(
    month: String,
    year: Int,
    resources: ResourceProvider,
    viewModel: CalendarViewModel
) {
    val months = resources.getStringArray("months")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 28.dp, start = 20.dp, end = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = resources.getString("month_year_placeholder")
                .format(month, year),
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 6.dp),
            style = DesignSystemTheme.typography.h3.medium
        )
        OutlinedButton(
            onClick = {
                viewModel.setYear(year + 1)
            },
            modifier = Modifier
                .size(width = 10.dp, height = 25.dp)
                .padding(top = 10.dp),
            shape = CircleShape,
            border = BorderStroke(0.dp, DesignSystemTheme.colors.backgroundSecondary),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = DesignSystemTheme.colors.backgroundSecondary,
                contentColor = DesignSystemTheme.colors.backgroundInverted
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.year_arrows),
                contentDescription = "",
                tint = DesignSystemTheme.colors.controlsSecondaryActive
            )
        }
        Spacer(Modifier.weight(1f))
        OutlinedButton(
            onClick = {
                var currentIndex = months.indexOf(month)
                if (currentIndex == 0) currentIndex = 11 else currentIndex--
                viewModel.setMonth(months[currentIndex])
                viewModel.getDaysData(months[currentIndex])
            },
            modifier = Modifier.size(32.dp),
            shape = CircleShape,
            border = BorderStroke(0.dp, DesignSystemTheme.colors.backgroundSecondary),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = DesignSystemTheme.colors.backgroundSecondary,
                contentColor = DesignSystemTheme.colors.backgroundInverted
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button_previous_month),
                contentDescription = "",
                tint = DesignSystemTheme.colors.controlsSecondaryActive
            )
        }
        Spacer(
            Modifier.size(width = 8.dp, height = 0.dp)
        )
        OutlinedButton(
            onClick = {
                var currentIndex = months.indexOf(month)
                if (currentIndex == 11) currentIndex = 0 else currentIndex++
                viewModel.setMonth(months[currentIndex])
                viewModel.getDaysData(months[currentIndex])
            },
            modifier = Modifier.size(32.dp),
            shape = CircleShape,
            border = BorderStroke(0.dp, DesignSystemTheme.colors.backgroundSecondary),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = DesignSystemTheme.colors.backgroundSecondary,
                contentColor = DesignSystemTheme.colors.backgroundInverted
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button_second_month),
                contentDescription = "",
                tint = DesignSystemTheme.colors.controlsSecondaryActive
            )
        }
    }
}

@Composable
fun DaysOfTheWeekTitles(resources: ResourceProvider) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, start = 20.dp),
        //  horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val days = resources.getStringArray("days")
        for (i in 0..6) {
            Text(
                modifier = Modifier.size(width = 40.dp, height = 18.dp),
                textAlign = TextAlign.Center,
                text = days[i].uppercase(Locale.getDefault()),
                style = DesignSystemTheme.typography.p3.regular,
                color = DesignSystemTheme.colors.textSecondary
            )
            if (i != 6) Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun DaysOfTheWeek(dayArray: List<List<String>>, viewModel: CalendarViewModel) {
    val colorActive = DesignSystemTheme.colors.controlsPrimaryActive
    val colorSecondary = DesignSystemTheme.colors.textSecondary
    val colorPrimary = DesignSystemTheme.colors.textPrimary
    val pressedI = remember {
        mutableStateOf(0)
    }
    val pressedJ = remember {
        mutableStateOf(0)
    }
    val dayColorArray = Array(6) { j ->
        Array(7) { i ->
            when {
                (i == pressedI.value && j == pressedJ.value) -> colorActive
                i in 0..4 -> colorPrimary
                i in 5..6 -> colorSecondary
                else -> colorPrimary
            }
        }
    }
    Column {
        for ((j, element) in dayArray.withIndex()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp, start = 20.dp)
            ) {
                for (i in 0..6) {
                    Text(
                        text = element[i],
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(width = 40.dp, height = 24.dp)
                            .clickable {
                                if (element[i] != "") {
                                    pressedI.value = i
                                    pressedJ.value = j
                                    viewModel.setCurrentPressed(element[i])
                                }
                            },
                        style = DesignSystemTheme.typography.h3.regular,
                        color = dayColorArray[j][i]
                    )
                    if (i != 6) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ButtonForJobs(isEnabled: Boolean, resources: ResourceProvider, viewModel: CalendarViewModel) {

    val interactionSource = remember { MutableInteractionSource() }
    val color =
        if (isEnabled) DesignSystemTheme.colors.controlsPrimaryActive else DesignSystemTheme.colors.controlsInactive
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp)
    ) {
        Button(
            onClick = {
                viewModel.getJobsData()
            },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = color,
                disabledBackgroundColor = color
            ),
            modifier = Modifier
                .size(width = 282.dp, height = 36.dp)
                .align(Alignment.Center),
            enabled = isEnabled,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = resources.getString("button_open_jobs"),
                style = DesignSystemTheme.typography.p2.medium,
                color = DesignSystemTheme.colors.textInverted
            )
        }
    }
}


@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}