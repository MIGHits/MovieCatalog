package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.view.BlockLabel
import com.example.moviecatalog.presentation.view.InfoBlock

@Composable
fun MovieMainInfo(
    country: String,
    age: Int,
    time: String,
    year: Int
) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(darkFaded)
    ) {
        Column {
            BlockLabel(R.drawable.info_ic, R.string.info)
            Box(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 12.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Top) {
                        InfoBlock(R.string.countries, country, 0.7f)
                        Spacer(Modifier.width(8.dp))
                        InfoBlock(R.string.age, "$age+", 1f)
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        InfoBlock(R.string.time, time.toString(), 0.5f)
                        Spacer(Modifier.width(8.dp))
                        InfoBlock(R.string.release_year, year.toString(), 1f)
                    }
                }
            }
        }
    }
}