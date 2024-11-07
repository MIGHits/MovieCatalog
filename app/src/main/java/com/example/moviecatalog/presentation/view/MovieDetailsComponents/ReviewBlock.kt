package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.presentation.entity.ReviewModelUI
import com.example.moviecatalog.presentation.entity.UserReviewUI
import com.example.moviecatalog.presentation.entity.UserShortModelUI
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond
import com.example.moviecatalog.presentation.view.BlockLabel
import com.example.moviecatalog.presentation.view_model.MovieDetailsViewModel

@Composable
fun ReviewBlock(
    reviews: List<ReviewModelUI>,
    movieId: String,
    userId: String,
    addFriend: (UserShortModelUI) -> Unit,
    viewModel: MovieDetailsViewModel
) {
    val reviewsCount = reviews.size
    var userReview by remember {
        mutableStateOf(
            UserReviewUI(
                INITIAL_FIELD_STATE,
                -1,
                false
            )
        )
    }
    var current by remember { mutableIntStateOf(0) }
    var reviewDialogState by remember { mutableStateOf(false) }
    var userSelfReview by remember { mutableStateOf(false) }

    val handleAccept: (UserReviewUI) -> Unit = { review ->
        userReview = review
        reviewDialogState = false
    }

    if (reviews[current].author.userId == userId) {
        userSelfReview = true
    } else {
        userSelfReview = false
    }

    if (reviewDialogState) {
        MyDialog({ reviewDialogState = false }, { userReview ->
            handleAccept(userReview)
            if (!userSelfReview) {
                viewModel.addReview(movieId, userReview)
            } else {
                viewModel.editReview(movieId, reviews[current].id, userReview)
            }
        },
            userSelfReview,
            reviews[current]
        )
    }

    Box(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 48.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(darkFaded)
    ) {
        Column {
            BlockLabel(R.drawable.review_ic, R.string.reviews)
            Box(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 12.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(R.color.screenBackgroundDark))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (!reviews[current].isAnonymous ||
                            reviews[current].author.userId == userId
                        ) {
                            FriendsAvatar(
                                reviews[current].author,
                                addFriend
                            )
                        } else {
                            FriendsAvatar(null) {}
                        }

                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp
                                )
                                .fillMaxWidth(0.75f)
                        ) {
                            Text(
                                text = if (reviews[current].isAnonymous
                                    && reviews[current].author.userId != userId
                                ) {
                                    stringResource(
                                        R.string.anonimus
                                    )
                                } else reviews[current].author.nickName.toString(),
                                fontSize = 12.sp,
                                lineHeight = 14.4.sp,
                                color = Color.White
                            )
                            Text(
                                text = reviews[current].createDateTime,
                                fontSize = 12.sp,
                                lineHeight = 14.4.sp,
                                color = colorResource(R.color.GrayFaded)
                            )
                        }
                        ReviewRating(reviews[current].rating)
                    }
                    Text(
                        text = reviews[current].reviewText.toString(),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 12.dp
                            )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = { reviewDialogState = true },
                    modifier = Modifier
                        .padding(end = if (!userSelfReview) 24.dp else 18.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Gradient(
                                listOf(
                                    gradientFirst,
                                    gradientSecond
                                ),
                            )
                        )
                        .weight(3f)
                ) {
                    Text(
                        text = if (!userSelfReview) stringResource(R.string.addReview)
                        else stringResource(
                            R.string.editReview
                        ),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

                if (userSelfReview) {
                    IconButton(
                        onClick = { viewModel.deleteReview(movieId, reviews[current].id) },
                        modifier = Modifier
                            .padding(end = 24.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (current != 0) colorResource(R.color.screenBackgroundDark)
                                else darkFaded
                            ),
                        enabled = current != 0

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bin),
                            contentDescription = null,
                            tint = if (current != 0) Color.White
                            else colorResource(R.color.TextGray),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                IconButton(
                    onClick = { current-- },
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (current != 0) colorResource(R.color.screenBackgroundDark)
                            else darkFaded
                        ),
                    enabled = current != 0

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = null,
                        tint = if (current != 0) Color.White
                        else colorResource(R.color.TextGray),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                IconButton(
                    onClick = { current++ },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (current != reviewsCount - 1) colorResource(R.color.screenBackgroundDark)
                            else darkFaded
                        ),
                    enabled = current != reviewsCount - 1

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.forward_ic),
                        contentDescription = null,
                        tint = if (current != reviewsCount - 1) Color.White
                        else colorResource(R.color.TextGray),
                        modifier = Modifier.padding(8.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun FriendsAvatar(user: UserShortModelUI?, addFriend: (UserShortModelUI) -> Unit) {
    val guestAvatar = R.drawable.profile_image
    AsyncImage(
        model = user?.avatar?.ifEmpty { guestAvatar },
        contentDescription = null,
        modifier = Modifier
            .width(32.dp)
            .height(32.dp)
            .clip(CircleShape)
            .clickable { user?.let { addFriend(it) } },
        contentScale = ContentScale.Crop
    )
}