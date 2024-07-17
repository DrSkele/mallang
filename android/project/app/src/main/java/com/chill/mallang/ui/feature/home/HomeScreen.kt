package com.chill.mallang.ui.feature.home

import android.util.Log
import android.widget.Space
import android.widget.TextView
import androidx.collection.mutableObjectIntMapOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray2
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography


object TestData { // 화면 임시 구성할 데이터
    const val USER_NAME = "짜이한"
    const val USER_RANK = "다이아"
    const val USER_ITEM = "15코인"
    const val RANK_1 = "다이아"
    const val RANK_2 = "골드"
    const val RANK_3 = "실버"
    const val RANK_4 = "브론즈"
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column(modifier) {
            Row(modifier) { // 유저 아이템 수의 따라 LazyColumn
                UserItem(
                    modifier = modifier,
                    icon = R.drawable.ic_stars,
                    label = TestData.USER_ITEM
                )
                Spacer(modifier.width(5.dp))
                UserItem(
                    modifier = modifier,
                    icon = R.drawable.ic_stars,
                    label = TestData.USER_ITEM
                )
            }
            Spacer(modifier = modifier.height(16.dp))
            SideUserButton(modifier = modifier) // 사이드 버튼
            UserCharacter( // 유저 캐릭터 or 프로필
                modifier = modifier,
                userName = TestData.USER_NAME,
                userRank = TestData.USER_RANK
            )
            Spacer(modifier.weight(0.1f))
            ModeButton( // 퀴즈 모드 버튼
                icon = R.drawable.ic_question,
                label = stringResource(R.string.mode_quiz),
                modifier = modifier.align(Alignment.End),
                onClick = { }
            )
            Spacer(modifier.height(16.dp))
            ModeButton( // 점령전 모드 버튼
                icon = R.drawable.ic_location,
                label = stringResource(R.string.mode_home),
                modifier = modifier.align(Alignment.End),
                onClick = { }
            )
            Spacer(modifier.weight(0.1f))
        }
    }
}

@Composable
private fun IconButton(icon: Int, label: String, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }, horizontalAlignment = Alignment.End
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label
            )
            Text(
                text = label,
                style = Typography.displayLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun SideUserButton(modifier: Modifier) {
    Column(modifier) {
        IconButton(
            icon = R.drawable.ic_setting,
            label = stringResource(R.string.side_button_setting),
            modifier = modifier,
            onClick = { }
        )
        Spacer(modifier = modifier.size(15.dp))
        IconButton(
            icon = R.drawable.ic_quest,
            label = stringResource(R.string.side_button_quest),
            modifier = modifier,
            onClick = { })
        Spacer(modifier = modifier.size(15.dp))
        IconButton(
            icon = R.drawable.ic_ranking,
            label = stringResource(R.string.side_button_ranking),
            modifier = modifier,
            onClick = { })
    }
}

@Composable
private fun UserItem(modifier: Modifier, icon: Int, label: String) {
    Row(
        modifier = modifier
            .border(1.dp, Color.Black, shape = RoundedCornerShape(15.dp))
            .padding(5.dp)
            .height(IntrinsicSize.Min)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = Color.Yellow,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp)
        )
        Text(
            text = label,
            style = Typography.displaySmall,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp)
        )
    }
}

@Composable
private fun UserCharacter(modifier: Modifier, userName: String, userRank: String) {
    when (userRank) { // 티어별 이미지 할당
        TestData.RANK_1 -> {}
        TestData.RANK_2 -> {}
        TestData.RANK_3 -> {}
        TestData.RANK_4 -> {}
    }
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_stars),
                contentDescription = userRank,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = userName,
                style = Typography.headlineLarge,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
        Image(painter = painterResource(id = R.mipmap.malang), contentDescription = "말랑")
        Box(modifier = Modifier.height(IntrinsicSize.Max)) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.mipmap.rectangle_message),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.Center),
                text = stringResource(id = R.string.character_message),
                style = Typography.bodyLarge,
                color = Sub1,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun ModeButton(icon: Int, label: String, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .width(75.dp)
            .height(75.dp)
            .clickable { onClick() }
            .background(color = Gray2, shape = CircleShape)
            .border(width = 2.dp, color = Color.Black, shape = CircleShape),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
        )
        Text(
            text = label,
            style = Typography.displayMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePreview() {
    MallangTheme {
        HomeScreen()
    }
}
