package teka.android.chamaaapp.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jaikeerthick.composable_graphs.composables.bar.BarGraph
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphColors
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphFillType
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphVisibility
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility
import com.jaikeerthick.composable_graphs.style.LabelPosition
import teka.android.chamaaapp.presentation.members.EntityCountResult
import teka.android.chamaaapp.ui.theme.BlueEnd
import teka.android.chamaaapp.ui.theme.BlueStart
import teka.android.chamaaapp.ui.theme.LightGreen
import teka.android.chamaaapp.ui.theme.SecondaryColor
import teka.android.chamaaapp.ui.theme.quicksand
import teka.android.chamaaapp.util.components.FeaturedBox
import teka.android.chamaaapp.util.components.QuickAccessSection
import teka.android.chamaaapp.util.components.TopScreenSection


@Composable
fun HomeScreen(navController: NavController) {
    val homeScreenViewModel : HomeScreenViewModel = hiltViewModel()

    val memberCountState = homeScreenViewModel.memberCount.collectAsState()
    val chamaaAccountsCountState = homeScreenViewModel.chamaaAccountsCount.collectAsState()
    val context = LocalContext.current


    val memberCountText = when (val result = memberCountState.value) {
        is EntityCountResult.Success -> {
            "${result.data}"
        }
        is EntityCountResult.Error -> {
            "0"
        }
    }

    val chamaaAccountsCountText = when (val result = chamaaAccountsCountState.value) {
        is EntityCountResult.Success -> {
            "${result.data}"
        }
        is EntityCountResult.Error -> {
            "0"
        }
    }

    val pieChartData = listOf(
        PieData(value = 130F, label = "HTC", color = SecondaryColor, labelColor = Color.White),
        PieData(value = 260F, label = "Apple", color = LightGreen, labelColor = Color.White),
        PieData(value = 500F, label = "Google", color = BlueEnd, labelColor = Color.White),
    )
    val barData = listOf(
        BarData(x = "January", y = 20),
        BarData(x = "February", y = 30),
        BarData(x = "March", y = 25),
        BarData(x = "April", y = 40),
        BarData(x = "May", y = 35)
    )

    LaunchedEffect(homeScreenViewModel) {
        homeScreenViewModel.getMemberCount()
    }
    Scaffold(

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            TopScreenSection()
            LazyColumn(content = {
                item {
                    FeaturedBox(
                        totalMembers = memberCountText,
                        totalChamaaAccounts = chamaaAccountsCountText
                    )
                }
                item {
                    QuickAccessSection(navController = navController)
                }
                item {
                    ChartSection(
                        pieChartData1 = pieChartData,
                        pieChartData2 = pieChartData,
                        context = context
                    )
                }
                item {
                    BarGraphSection(
                        barData = barData,
                        context = context
                    )
                }
                item { 
                    Spacer(modifier = Modifier.height(12.dp))
                }
            })
        }
    }
}


@Composable
fun BarGraphSection(
    barData: List<BarData>,
    context: Context
){
    Text(
        text = "Egg Production",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(16.dp),
        fontFamily = quicksand
    )
    BarGraph(
        data = barData,
        style = BarGraphStyle(
            colors = BarGraphColors(
                fillType = BarGraphFillType.Gradient(brush = Brush.verticalGradient(listOf(
                    BlueStart, BlueEnd
                )))
            ),
            visibility = BarGraphVisibility(isYAxisLabelVisible = true),
            yAxisLabelPosition = LabelPosition.LEFT
        ),
        onBarClick = {
            Toast.makeText(context, "${it.x}: ${it.y}", Toast.LENGTH_SHORT).show()

        }

    )

}

@Composable
fun ChartSection(
    pieChartData1: List<PieData>,
    pieChartData2: List<PieData>,
    context: Context
) {
    Text(
        text = "PieCharts",
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(16.dp),
        fontFamily = quicksand
    )

//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 5.dp)
//    ) {
        Column(modifier = Modifier.padding(horizontal = 5.dp)) {

            // Place the PieCharts in a Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // First PieChart
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    PieChart(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(150.dp),
                        data = pieChartData1,
                        style = PieChartStyle(
                            visibility = PieChartVisibility(isLabelVisible = true, isPercentageVisible = true),
                            ),
                        onSliceClick = { pieData ->
                            Toast.makeText(context, "${pieData.label}: ${pieData.value}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // Second PieChart
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    PieChart(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(150.dp),
//                            .size(220.dp),
                        data = pieChartData2,
                        style = PieChartStyle(
                            visibility = PieChartVisibility(isLabelVisible = true, isPercentageVisible = true),

                            ),
                        onSliceClick = { pieData ->
                            Toast.makeText(context, "${pieData.label}: ${pieData.value}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // First PieChart
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    PieChart(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(150.dp),

                        data = pieChartData1,
                        style = PieChartStyle(
                            visibility = PieChartVisibility(isLabelVisible = true, isPercentageVisible = true),

                            ),
                        onSliceClick = { pieData ->
                            Toast.makeText(context, "${pieData.label}: ${pieData.value}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // Second PieChart
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    PieChart(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .height(150.dp),
//                            .size(220.dp),
                        data = pieChartData2,
                        style = PieChartStyle(
                            visibility = PieChartVisibility(isLabelVisible = true, isPercentageVisible = true),

                            ),
                        onSliceClick = { pieData ->
                            Toast.makeText(context, "${pieData.label}: ${pieData.value}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
//    }
}