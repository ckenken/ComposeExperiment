package com.ckenken.composeexperiment.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun FacilitiesSection() {
    Column(
        modifier = Modifier
            .background(StyleDictionary.kkColorWhite)
            .padding(StyleDictionary.kkSpacing7)
            .width(327.dp)
    ) {
        // 標題
        Text(
            text = "設施與服務",
            fontSize = StyleDictionary.kkTextH5FontSize,
            fontWeight = FontWeight.W600,
            color = StyleDictionary.kkColorTextDarker,
            modifier = Modifier.padding(bottom = StyleDictionary.kkSpacing7)
        )

        // 熱門設施
        FacilitiesGroup(
            title = "熱門设施",
            items = listOf(
                FacilityItem("ic_service_line", "24小时前台服务"),
                FacilityItem("ic_train_line", "机场接送"),
                FacilityItem("ic_tourFlag_line", "停车场"),
                FacilityItem("ic_car_line", "接驳服务"),
                FacilityItem("ic_walk_line", "健身房", tag = "免费"),
                FacilityItem("ic_service_line", "房内免费 Wi-Fi")
            )
        )

        Spacer(modifier = Modifier.height(StyleDictionary.kkSpacing5))
        DashedDivider()
        Spacer(modifier = Modifier.height(StyleDictionary.kkSpacing5))

        // 所有客房均提供
        FacilitiesGroup(
            title = "所有客房均提供",
            items = listOf(
                FacilityItem("ic_service_line", "24小时前台服务"),
                FacilityItem("ic_train_line", "机场接送"),
                FacilityItem("ic_tourFlag_line", "停车场"),
                FacilityItem("ic_car_line", "接驳服务"),
                FacilityItem("ic_walk_line", "健身房", tag = "免费"),
                FacilityItem("ic_service_line", "房内免费 Wi-Fi")
            )
        )

        Spacer(modifier = Modifier.height(StyleDictionary.kkSpacing5))
        DashedDivider()
        Spacer(modifier = Modifier.height(StyleDictionary.kkSpacing5))

        // 設施分類（修正版，設施文字與標題文字對齊）
        Column(
            modifier = Modifier.width(327.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = StyleDictionary.kkSpacing3)
            ) {
                // Icon placeholder
                Box(
                    modifier = Modifier
                        .size(StyleDictionary.kkDimensionIconMd)
                        .background(StyleDictionary.kkColorBackgroundPrimaryLighter, RoundedCornerShape(StyleDictionary.kkRadiusLg))
                )
                Text(
                    text = "接待设施",
                    fontSize = StyleDictionary.kkTextH6FontSize,
                    fontWeight = FontWeight.W600,
                    color = StyleDictionary.kkColorTextDarker,
                    modifier = Modifier.padding(start = StyleDictionary.kkSpacing3)
                )
            }
            // 讓所有設施文字與標題文字對齊（加上與標題相同的 start padding 8.dp）
            Column(
                modifier = Modifier.padding(start = StyleDictionary.kkDimensionIconMd + StyleDictionary.kkSpacing3)
            ) {
                FacilityText("24小時保全")
                FacilityText("24小時櫃檯服務")
                FacilityText("快速入住／退房服務")
                FacilityText("24小時皆可入住")
                FacilityText("安全／保全設施")
                FacilityText("住宿外部監視器")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FacilityText("公共區域監視器")
                    Tag(text = "收费", bgColor = StyleDictionary.kkColorBackgroundSurfaceLighter, textColor = StyleDictionary.kkColorTextDark)
                }
                FacilityText("煙霧警報器")
                FacilityText("室外走廊")
            }
        }

        Spacer(modifier = Modifier.height(StyleDictionary.kkSpacing7))

        // 更多設施與服務按鈕
        Button(
            onClick = { /* TODO */ },
            shape = RoundedCornerShape(StyleDictionary.kkRadiusMd),
            colors = ButtonDefaults.buttonColors(
                containerColor = StyleDictionary.kkColorWhite,
                contentColor = StyleDictionary.kkColorTextDarker
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(StyleDictionary.kkSpacing10)
        ) {
            Text(
                text = "更多设施与服务",
                fontSize = StyleDictionary.kkTextH6FontSize,
                fontWeight = FontWeight.W600
            )
        }
    }
}

data class FacilityItem(
    val icon: String,
    val text: String,
    val tag: String? = null
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilitiesGroup(title: String, items: List<FacilityItem>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF212121),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        FlowRow(
            modifier = Modifier
                .wrapContentHeight()
                .padding(0.dp),
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        )  {
            items.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    // Icon placeholder
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFFF0FAFB), RoundedCornerShape(12.dp))
                    )
                    Text(
                        text = item.text,
                        fontSize = 14.sp,
                        color = Color(0xFF212121),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    item.tag?.let {
                        Tag(text = it, bgColor = Color(0xFFF0FAFB), textColor = Color(0xFF13A3B6))
                    }
                }
            }
        }
    }
}

@Composable
fun FacilityText(text: String) {
    Text(
        text = text,
        fontSize = StyleDictionary.kkTextBodyMdFontSize,
        color = StyleDictionary.kkColorTextDarker,
        modifier = Modifier.padding(bottom = StyleDictionary.kkSpacing2)
    )
}

@Composable
fun Tag(text: String, bgColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(StyleDictionary.kkRadiusSm))
            .padding(horizontal = StyleDictionary.kkSpacing2, vertical = StyleDictionary.kkSpacing1)
    ) {
        Text(
            text = text,
            fontSize = StyleDictionary.kkTextBodySmFontSize,
            color = textColor
        )
    }
}

@Composable
fun DashedDivider() {
    // Jetpack Compose 沒有內建虛線，這裡用普通 Divider 代替
    HorizontalDivider(
        color = StyleDictionary.kkColorBorderLighter,
        thickness = 1.dp
    )
}
