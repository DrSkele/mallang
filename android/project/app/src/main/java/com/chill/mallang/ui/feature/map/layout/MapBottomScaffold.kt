package com.chill.mallang.ui.feature.map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.data.model.Area
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.MallangTheme
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

/**
 * 지도 아래 레이아웃
 */
@Composable
fun MapScaffold(
    modifier: Modifier = Modifier,
    areaSelected: Area?,
    currentLocation: LatLng?,
    onLocate: () -> Unit = {},
    onShowDetail: (Area) -> Unit = {}
){
    // 현 위치와 선택된 위치 사이의 거리
    val distance by remember {
        derivedStateOf{
            if(currentLocation != null && areaSelected != null){
                SphericalUtil.computeDistanceBetween(currentLocation, areaSelected.latLng).toInt()
            } else {
                null
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(330.dp)
            .background(color = BackGround),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OccupationStatusBar(
            modifier = Modifier.padding(16.dp),
            leftCount = 7,
            rightCount = 3
        )
        CharacterMessageBox(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp)
        )

        if(areaSelected == null)
            LocateNearbyButton(
                modifier = Modifier.padding(bottom = 16.dp),
                onClick = onLocate
            )
        else
            AreaInfoBar(
                modifier = Modifier.padding(16.dp),
                area = areaSelected,
                distance = distance,
                onShowDetail = { area -> onShowDetail(area) }
            )
    }
}

/**
 * 기본 화면
 * 선택된 점령지가 없을 때
 */
@Preview(apiLevel = 34)
@Composable
private fun MapScaffoldPreview(){
    MallangTheme {
        MapScaffold(areaSelected = null, currentLocation = LatLng(0.0, 0.0))
    }
}

/**
 * 점령지를 선택했을 때
 */
@Preview(apiLevel = 34)
@Composable
private fun MapScaffoldPreviewWithAreaInfo(){
    MallangTheme {
        MapScaffold(
            areaSelected = Area(1, "찰밭공원", 0.0, 0.0),
            currentLocation = LatLng(0.0, 0.0)
        )
    }
}