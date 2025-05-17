package com.ckenken.composeexperiment.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun BloodPressureDashboard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("收縮壓", fontWeight = FontWeight.Bold)
        BloodPressureRow("最低", "最高", "平均")

        Spacer(Modifier.height(16.dp))
        Text("舒張壓", fontWeight = FontWeight.Bold)
        BloodPressureRow("最低", "最高", "平均")

        Spacer(Modifier.height(16.dp))
        Text("分布", fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(100.dp)
            ) {
                DistributionRow("良好", Color(0xFF55D0CF))
                DistributionRow("過高", Color(0xFFFFB74D))
                DistributionRow("過低", Color(0xFF9575CD))
                DistributionRow("總數", Color.Gray)
            }

            PieChart(
                values = listOf(75f, 20f, 5f),
                colors = listOf(
                    Color(0xFF55D0CF),
                    Color(0xFFFFB74D),
                    Color(0xFF9575CD)
                ),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun BloodPressureRow(label1: String, label2: String, label3: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(3) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color.LightGray)
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    when (it) {
                        0 -> label1
                        1 -> label2
                        else -> label3
                    },
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(4.dp)
                        .background(Color.Gray)
                )
            }
        }
    }
}

@Composable
fun DistributionRow(label: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.width(40.dp), fontSize = 14.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .width(16.dp)
                .height(4.dp)
                .background(color)
        )
    }
}