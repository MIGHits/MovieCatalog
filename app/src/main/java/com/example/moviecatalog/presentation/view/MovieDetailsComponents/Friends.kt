package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.presentation.entity.UserShortModelUI
import com.example.moviecatalog.presentation.theme.darkFaded


@Composable
fun FriendsBlock(
    friendList: List<UserShortModelUI>,
    addFriend: (UserShortModelUI) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(darkFaded)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box() {
                LazyRow() {
                    items(friendList) { item ->
                        FriendsAvatar(
                            item,
                            addFriend
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(start = 8.dp, top = 6.dp, bottom = 6.dp),
                text = "нравится n вашим друзьям",
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 20.sp
            )
        }
    }
}