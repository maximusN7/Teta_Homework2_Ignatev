package com.example.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendar.ui.DesignSystemTheme
import com.example.data.calendar.ListItem
import com.example.data.utils.ResourceProvider

@Composable
fun ListItemJob(resources: ResourceProvider, job: ListItem.TodayJob) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bullet_l),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 12.dp, top = 8.dp)
                .size(width = 8.dp, height = 8.dp)
        )
        Text(
            text = resources.getString("job_list_placeholder")
                .format(job.hours, job.minutes, job.job),
            style = DesignSystemTheme.typography.h3.regular
        )
    }
}