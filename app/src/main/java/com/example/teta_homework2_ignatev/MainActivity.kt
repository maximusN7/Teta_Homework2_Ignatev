package com.example.teta_homework2_ignatev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.calendar.CalendarScreen
import com.example.calendar.CalendarViewModel
import com.example.calendar.ui.DesignSystemTheme
import com.example.data.calendar.repository.JobsRepositoryMock
import com.example.data.utils.ResourceProvider
import kotlinx.coroutines.flow.MutableStateFlow


class MainActivity : ComponentActivity() {

    private val isDarkEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkEnabled = isDarkEnabled.collectAsState()
            DesignSystemTheme(darkEnabled.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DesignSystemTheme.colors.backgroundPrimary
                ) {
                    CalendarScreen(
                        CalendarViewModel(JobsRepositoryMock()),
                        ResourceProvider(this, resources)
                    )
                }
            }
        }
    }
}