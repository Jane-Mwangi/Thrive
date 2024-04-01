package design.fiti.thrive.presentation.screens.home

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate

@Composable
fun InsightScreen(homeViewModel: HomeViewModel) {
    val screenState by homeViewModel.uiState.collectAsState()
    val predictionsList: MutableList<Float> = mutableListOf()

    // Fetch predictions when the screen launches
    LaunchedEffect(key1 = true) {
        homeViewModel.getPredictions()
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if (screenState.predictions == null || screenState.predictions!!.predictedSpendings.isNullOrEmpty()) {
            when (screenState.predicationloadState) {
                is WhenToNavigate.Processing -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is WhenToNavigate.Stopped -> {
                    Text(text = "Something went wrong...")
                }

                is WhenToNavigate.Go -> {
                    Text(text = "You'll need to add new expenses to see your spending predictions for the next 30 days.")
                }
            }
        } else {
            val predictions = screenState.predictions
            Log.d("Predictions..........................", "Predictions data: $predictions")
            Text(text = "Predicted Spendings:")

            predictions?.predictedSpendings?.forEach { spending ->
                predictionsList.add(spending.toFloat())
            }
            var counter = 0f
            val predictionPoints = predictionsList.map {
                Point(counter++, it)
            }
            counter = 0f

            val pointsData = listOf(
                Point(0f, 40f),
                Point(1f, 90f),
                Point(2f, 0f),
                Point(3f, 60f),
                Point(4f, 10f),
                Point(5f, 10f),
                Point(6f, 10f),
                Point(7f, 10f),
            )
            CombinedLinechartWithBackground(pointsData = predictionPoints)

        }
    }
}


//@Composable
//fun LineChartGraph() {
//    val steps = 4
//    val pointsData = listOf(
//        Point(0f, 40f),
//        Point(1f, 90f),
//        Point(2f, 0f),
//        Point(3f, 60f),
//        Point(4f, 10f)
//    )
//    val xAxisData = AxisData.Builder()
//        .axisStepSize(100.dp)
//        .backgroundColor(Color.Blue)
//        .steps(pointsData.size - 1)
//        .labelData { i -> i.toString() }
//        .labelAndAxisLinePadding(15.dp)
//        .build()
//
//    val yAxisData = AxisData.Builder()
//        .steps(steps)
//        .backgroundColor(Color.Red)
//        .labelAndAxisLinePadding(20.dp)
//        .labelData { i ->
//            val yScale = 100 / steps
//            (i * yScale).formatToSinglePrecision().toString()
//        }.build()
//
//    val lineChartData = LineChartData(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointsData,
//                    LineStyle(),
//                    IntersectionPoint(),
//                    SelectionHighlightPoint(),
//                    ShadowUnderLine(),
//                    SelectionHighlightPopUp()
//                )
//            ),
//        ),
//        xAxisData = xAxisData,
//        yAxisData = yAxisData,
//        gridLines = GridLines(),
//        backgroundColor = Color.White
//    )
//    LineChart(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp),
//        lineChartData = lineChartData
//    )
//}

/**
 * Combined linechart with background
 *
 * @param pointsData
 */
@Composable
fun CombinedLinechartWithBackground(pointsData: List<Point>) {
    val steps = pointsData.size
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            // Add yMin to get the negative axis values to the scale
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).formatToSinglePrecision()
        }.build()
    val colorPaletteList = listOf<Color>(Color.Blue, Color.Yellow, Color.Magenta, Color.DarkGray)
    val legendsConfig = LegendsConfig(
        legendLabelList = DataUtils.getLegendsLabelData(colorPaletteList),
        gridColumnCount = 4
    )
    val data = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(
                        lineType = LineType.SmoothCurve(isDotted = true),
                        style = Stroke(width = 0.2f),
                        color = colorPaletteList.first(),
                        width = 0.2f
                    ),
                    shadowUnderLine = ShadowUnderLine(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Green,
                                Color.Transparent
                            )
                        ), alpha = 0.3f
                    ),
                    selectionHighlightPoint = SelectionHighlightPoint(
                        color = Color.Green
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        backgroundStyle = Stroke(0.5f),
                        labelColor = Color.Red,
                        labelTypeface = Typeface.DEFAULT_BOLD
                    )
                ),
                Line(
                    dataPoints = pointsData.subList(
                        0,
                        pointsData.size
                    ), // Adjust sublist range as per your requirement
                    lineStyle = LineStyle(
                        lineType = LineType.SmoothCurve(),
                        color = colorPaletteList[1],
                        width = 0.2f,
                        style = Stroke(
                            width = 0.2f
                        )
                    ),
                    intersectionPoint = IntersectionPoint(color = Color.Red, radius = 2.dp),
                    selectionHighlightPopUp = SelectionHighlightPopUp(popUpLabel = { x, y ->
                        val xLabel = "x : ${(1900 + x).toInt()} "
                        val yLabel = "y : ${String.format("%.2f", y)}"
                        "$xLabel $yLabel"
                    })
                ),
                Line(
                    dataPoints = pointsData.subList(0, pointsData.size.coerceAtMost(20)),
                    LineStyle(color = colorPaletteList[2], width = 0.2f, style = Stroke(0.2f)),
                    IntersectionPoint(radius = 2.dp),
                    SelectionHighlightPoint(),
                    shadowUnderLine = ShadowUnderLine(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Cyan,
                                Color.Blue
                            )
                        ), alpha = 0.5f
                    ),
                    SelectionHighlightPopUp()
                ),
                Line(
                    dataPoints = pointsData.subList(0, pointsData.size),
                    LineStyle(
                        color = colorPaletteList[3],
                        style = Stroke(width = 0.2f),
                        width = 0.2f
                    ),
                    IntersectionPoint(radius = 2.dp),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
//        backgroundColor = Color.Yellow
    )

    Column(
        modifier = Modifier
//            .height(400.dp)
    ) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth(),
            lineChartData = data
        )
        Legends(
            legendsConfig = legendsConfig
        )
    }
}