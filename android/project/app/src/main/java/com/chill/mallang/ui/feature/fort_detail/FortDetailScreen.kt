package com.chill.mallang.ui.feature.fort_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.AreaDetail
import com.chill.mallang.data.model.entity.TeamInfo
import com.chill.mallang.data.model.entity.TeamRecords
import com.chill.mallang.data.model.entity.UserInfo
import com.chill.mallang.data.model.entity.UserRecord
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun FortDetailScreen(
    modifier: Modifier = Modifier,
    areaId: Int?,
    userId: Int?,
    teamId: Int?,
) {
    val viewModel: FortDetailViewModel = hiltViewModel()
    val occupationState by viewModel.areaDetailStateFlow.collectAsStateWithLifecycle()
    val teamLeadersState by viewModel.teamRecordStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (areaId == null || userId == null || teamId == null) {
            viewModel.invalidateData()
        } else {
            viewModel.loadOccupationState(areaId, teamId)
            viewModel.loadTeamLeadersState(areaId, userId)
        }
    }

    AreaDetailContent(
        modifier = modifier,
        areaDetailState = occupationState,
        teamRecordState = teamLeadersState,
    )
}

@Composable
fun AreaDetailContent(
    modifier: Modifier = Modifier,
    areaDetailState: AreaDetailState,
    teamRecordState: TeamRecordState,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        MainBody(
            occupationState = areaDetailState,
            modifier = Modifier.weight(7F),
        )
        GameStartBody(
            modifier = Modifier.weight(2F),
        )
        RecordBody(
            teamRecordState = teamRecordState,
            modifier = Modifier.weight(7F),
        )
    }
}

@Composable
fun MainBody(
    occupationState: AreaDetailState,
    modifier: Modifier = Modifier,
) {
    when (occupationState) {
        is AreaDetailState.Loading -> {
            Surface(
                modifier = modifier.fillMaxSize(),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }
        }

        is AreaDetailState.Error -> {
            Surface(
                modifier = modifier.fillMaxSize(),
            ) {
                Text(occupationState.errorMessage.getErrorMessage(LocalContext.current))
            }
        }

        is AreaDetailState.Success -> {
            Surface(
                modifier =
                modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp, horizontal = 10.dp),
            ) {
                Column {
                    FortDetailHeader(
                        fortName = occupationState.areaDetail.areaName,
                        modifier = Modifier.weight(1F),
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.weight(6F),
                    ) {
                        TeamScoreAndTopUser(
                            teamInfo = occupationState.areaDetail.myTeamInfo,
                            modifier =
                            Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                        )
                        Text("VS", fontSize = 40.sp)
                        TeamScoreAndTopUser(
                            teamInfo = occupationState.areaDetail.oppoTeamInfo,
                            modifier =
                            Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FortDetailHeader(
    fortName: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.LightGray.copy(alpha = 0.4f))
            .padding(vertical = 7.dp, horizontal = 10.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "",
                modifier = Modifier.height(height = 20.dp),
            )
            Text(text = fortName, fontSize = 18.sp)
        }
    }
}

@Composable
fun TeamScoreAndTopUser(
    teamInfo: TeamInfo,
    modifier: Modifier = Modifier,
) {
    val teamColor by remember(teamInfo.teamId) { mutableStateOf(if (teamInfo.teamId == 1) Color.Red else Color.Blue) }
    val teamTopUserTitle =
        if (teamInfo.teamId == 1) stringResource(R.string.team_mal_title) else stringResource(R.string.team_rang_title)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = stringResource(R.string.top_user_title, teamTopUserTitle), color = teamColor)
        Image(
            painter = painterResource(id = R.mipmap.malang),
            contentDescription = "",
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_master_tier),
                contentDescription = "",
                modifier = Modifier.height(30.dp),
            )
            if (teamInfo.topUser != null) {
                Text(
                    text = teamInfo.topUser.userName,
                    style = Typography.displayLarge,
                    fontSize = 28.sp,
                )
            }
        }
        Text(
            text = stringResource(R.string.team_score, teamInfo.teamPoint),
            fontSize = 30.sp,
            style = Typography.displayLarge,
            color = teamColor,
        )
    }
}

@Composable
fun GameStartBody(modifier: Modifier = Modifier) {
    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Button(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp),
            onClick = { },
            shape = RoundedCornerShape(10.dp),
            colors =
                ButtonDefaults.buttonColors(
                    Color.Black,
                    Color.White,
                ),
        ) {
            Text(
                stringResource(R.string.game_start),
                style = Typography.displayLarge,
                fontSize = 30.sp,
            )
        }
        Text(text = stringResource(R.string.remaining_chance, 3))
    }
}

@Composable
fun RecordBody(
    teamRecordState: TeamRecordState,
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs =
        listOf(
            stringResource(R.string.my_team_records_label),
            stringResource(R.string.oppo_team_records_label),
        )

    when (teamRecordState) {
        is TeamRecordState.Loading -> {
            Surface(
                modifier = modifier.fillMaxSize(),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is TeamRecordState.Error -> {
            Surface(
                modifier = modifier.fillMaxSize(),
            ) {
                Text(teamRecordState.errorMessage.getErrorMessage(LocalContext.current))
            }
        }

        is TeamRecordState.Success -> {
            Column(
                modifier = modifier,
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = {},
                    divider = {},
                ) {
                    tabs.forEachIndexed { index, title ->
                        Box {
                            CustomBorderBox(
                                bottomBorderColor =
                                    if (selectedTabIndex == index) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    },
                                bottomBorderWidth =
                                    if (selectedTabIndex == index) {
                                        4.dp
                                    } else {
                                        1.dp
                                    },
                            )
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(title) },
                            )
                        }
                    }
                }
                when (selectedTabIndex) {
                    0 -> TeamTab(recordList = teamRecordState.teamRecords.myTeamRecords)
                    1 -> TeamTab(recordList = teamRecordState.teamRecords.oppoTeamRecords)
                }
            }
        }
    }
}

@Composable
fun TeamTab(
    recordList: List<UserRecord>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier =
            modifier
                .padding(horizontal = 10.dp)
                .padding(vertical = 10.dp),
    ) {
        items(recordList) { record ->
            RecordListItem(record)
        }
    }
}

@Composable
fun RecordListItem(
    userRecord: UserRecord,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(height = 65.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = stringResource(R.string.user_record_rank, userRecord.userPlace),
                fontSize = 24.sp,
                style = Typography.displayLarge,
                color = Color.Red,
            )
            Text(
                text = userRecord.userName,
                fontSize = 22.sp,
                style = Typography.displayLarge,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(R.string.user_record_score, userRecord.userScore),
                fontSize = 22.sp,
                style = Typography.displayLarge,
            )
        }
    }
}

@Composable
fun CustomBorderBox(
    modifier: Modifier = Modifier,
    bottomBorderColor: Color = Color.White,
    bottomBorderWidth: Dp = 2.dp,
) {
    Box(modifier = modifier) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape =
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp,
                            ),
                    ),
        )

        Box(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 2.dp)
                    .height(bottomBorderWidth)
                    .fillMaxWidth()
                    .background(bottomBorderColor),
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FortDetailScreenPreview() {
    MallangTheme {
        AreaDetailContent(
            modifier = Modifier,
            areaDetailState = AreaDetailState.Loading,
            teamRecordState = TeamRecordState.Loading,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FortDetailScreenPreviewWithData() {
    MallangTheme {
        AreaDetailContent(
            modifier = Modifier,
            areaDetailState =
                AreaDetailState.Success(
                    AreaDetail(
                        areaName = "Name",
                        TeamInfo(1, 1, UserInfo(1, "Name")),
                        TeamInfo(1, 1, UserInfo(1, "Name")),
                    ),
                ),
            teamRecordState = TeamRecordState.Success(
                TeamRecords(
                    UserRecord(1, 1, 1),
                    listOf(UserRecord(1, 1, 1)),
                    listOf(UserRecord(1, 1, 1)),
                ),
            ),
        )
    }
}
