package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.presentation.entity.ReviewModelUI
import com.example.moviecatalog.presentation.entity.UserReviewUI
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond

import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDialog(
    onDismiss: () -> Unit,
    onAccept: (UserReviewUI) -> Unit,
    usersReview: Boolean,
    review: ReviewModelUI
) {
    var sliderPos by remember {
        mutableFloatStateOf(
            if (usersReview) review.rating.toFloat()
            else 5f
        )
    }
    var textField by remember {
        mutableStateOf(
            if (!usersReview) INITIAL_FIELD_STATE else
                review.reviewText
        )
    }
    var anonimusCheck by remember {
        mutableStateOf(if (usersReview) review.isAnonymous else false)
    }

    AlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .clip(RoundedCornerShape(28.dp)),
        backgroundColor = colorResource(R.color.screenBackgroundDark),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (usersReview) stringResource(R.string.addNewReview)
                else stringResource(R.string.EditReview),
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        },
        text = {
            Column {

                Text(
                    text = "Оценка",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = colorResource(R.color.TextGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )

                BoxWithConstraints {


                    val offset = getSliderOffset(
                        value = sliderPos,
                        valueRange = 0f..10f,
                        boxWidth = maxWidth,
                        labelWidth = 24.dp + 8.dp
                    )

                    if (sliderPos >= 0) {
                        SliderLabel(
                            label = sliderPos.roundToInt().toString(),
                            minWidth = 24.dp,
                            modifier = Modifier
                                .padding(start = offset)
                        )
                    }
                }

                androidx.compose.material3.Slider(
                    value = sliderPos,
                    onValueChange = { sliderPos = it },
                    valueRange = 0f..10f,
                    steps = 9,
                    colors = androidx.compose.material3.SliderDefaults.colors(
                        activeTickColor = Color.White,
                        inactiveTickColor = colorResource(R.color.Accent),
                        activeTrackColor = colorResource(R.color.Accent),
                        inactiveTrackColor = darkFaded
                    ),
                    thumb = {
                        Icon(
                            painter = painterResource(R.drawable.thumb),
                            contentDescription = null,
                            tint = colorResource(R.color.Accent)
                        )
                    }
                )
                TextField(
                    value = textField.toString(),
                    onValueChange = { textField = it },
                    placeholder = {
                        Column {
                            Text(
                                stringResource(R.string.reviewText),
                                color = colorResource(R.color.GrayFaded),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Spacer(Modifier.height(84.dp))
                        }
                    },
                    colors = androidx.compose.material3.TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.DarkFaded),
                        unfocusedContainerColor = colorResource(R.color.DarkFaded),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = colorResource(R.color.Accent)
                    ),
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        )
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(darkFaded)
                )
                Row(
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.anonReview),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = colorResource(R.color.GrayFaded),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.width(160.dp))
                    Switch(
                        checked = anonimusCheck,
                        onCheckedChange = { anonimusCheck = it },
                        colors = androidx.compose.material3.SwitchDefaults.colors(
                            checkedTrackColor = colorResource(R.color.Accent),
                            uncheckedTrackColor = colorResource(R.color.DarkFaded),
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAccept(
                        UserReviewUI(
                            textField.toString(),
                            sliderPos.roundToInt(),
                            anonimusCheck
                        )
                    )
                },
                modifier = Modifier
                    .offset((-12).dp, (-12).dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Gradient(
                            listOf(
                                gradientFirst,
                                gradientSecond
                            ),
                        )
                    )
                    .padding(start = 12.dp, end = 12.dp)
            ) {
                Text(
                    stringResource(R.string.sendReview),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding()
                )
            }
        }
    )
}

private fun getSliderOffset(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    boxWidth: Dp,
    labelWidth: Dp
): Dp {

    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    val positionFraction = calcFraction(valueRange.start, valueRange.endInclusive, coerced)

    return (boxWidth - labelWidth) * positionFraction
}

private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)
