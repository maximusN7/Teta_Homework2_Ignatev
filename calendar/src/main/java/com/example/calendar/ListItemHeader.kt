package com.example.calendar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.calendar.ui.DesignSystemTheme
import com.example.data.calendar.ListItem
import com.example.data.utils.ResourceProvider

@Composable
fun ListItemHeader(resources: ResourceProvider, header: ListItem.JobHeader) {
    Text(
        text = header.title,
        style = DesignSystemTheme.typography.h3.regular
    )
}