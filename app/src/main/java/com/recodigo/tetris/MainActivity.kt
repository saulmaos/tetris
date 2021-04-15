package com.recodigo.tetris

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.recodigo.tetris.ui.theme.Red
import com.recodigo.tetris.ui.theme.TetrisTheme
import com.recodigo.tetris.ui.theme.Yellow

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel2 by lazy {
        MainViewModel2()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TetrisTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
                    Container(Modifier.padding(32.dp), viewModel)
                }
            }
        }
        Log.d("Main", "${window.decorView.height}")
    }
}

@Composable
fun Container(modifier: Modifier = Modifier, viewModel: MainViewModel2) {
    val gridPoints = viewModel.gridPoints
    BoxWithConstraints(modifier = modifier.background(Red)) {
        val chm: Int = constraints.maxHeight
        val cwm: Int = constraints.maxWidth

        val hm = maxHeight
        val wm = maxWidth
        val cellSize = wm / 10

        viewModel.init(chm.toFloat(), hm.value, cellSize.value, cwm.toFloat())
        val currentFigure = viewModel.figureManager.currentFigure
        val figures = viewModel.figureManager.figures

        var rotation by remember { mutableStateOf(0f) }

        gridPoints.forEach {
            GridPointCompose(gridPoint = it)
        }

        Button(onClick = { viewModel.moveInX(false) }) {
            Text(text = "Left")
        }
        Button(onClick = { /*viewModel.rotate()*/ rotation += 90f}, Modifier.align(Alignment.TopCenter)) {
            Text(text = "Rotate")
        }
        Button(onClick = { viewModel.moveInX(true) }, Modifier.align(Alignment.TopEnd)) {
            Text(text = "Right")
        }

        val offset = 38f
        TBlock(
            modifier = Modifier.graphicsLayer(translationY = 90f + offset, translationX = 90f, rotationZ = rotation),
            size = cellSize,
            color = Yellow
        )


        FigureCompose(figure = currentFigure, cellSize = cellSize)
        figures.forEach {
            FigureCompose(figure = it, cellSize = cellSize)
        }
        LaunchedEffect(key1 = "sdf") {
            viewModel.createGrid()
            viewModel.start()
        }
    }
}

@Composable
fun FigureCompose(figure: Figure, cellSize: Dp) {
    val itemModifier = Modifier
        .graphicsLayer(translationY = figure.posY, translationX = figure.posX, rotationZ = figure.rotation)
    when (figure.type) {
        FigureType.Line -> LineBlock(
            itemModifier,
            cellSize,
            figure.color,
            figure.pos
        )
        FigureType.Square -> SquareBlock(
            itemModifier,
            cellSize,
            figure.color
        )
        else -> {}
    }
}

@Composable
fun LineBlock(modifier: Modifier = Modifier, size: Dp, color: Color, pos: Int) {
    Row(modifier = modifier) {
        Block(size, color, pos)
        Block(size, color, pos)
        Block(size, color, pos)
        Block(size, color, pos)
    }
}

@Composable
fun SquareBlock(modifier: Modifier = Modifier, size: Dp, color: Color) {
    Column(modifier = modifier) {
        Row {
            Block(size, color)
            Block(size, color)
        }
        Row {
            Block(size, color)
            Block(size, color)
        }
    }
}

@Composable
fun TBlock(modifier: Modifier, size: Dp, color: Color) {
    Column(modifier = modifier) {
        Row(Modifier.align(Alignment.CenterHorizontally)) {
            Block(size, color)
        }
        Row {
            Block(size, color)
            Block(size, color)
            Block(size, color)
        }
    }
}

@Composable
fun Block(size: Dp, color: Color = Color.Black, i : Int = 0) {
    Surface(
        Modifier
            .size(size),
        color = color,
        border = BorderStroke(1.dp, Color.Black)
    ){
        Text(text = i.toString())
    }
}

@Composable
fun GridPointCompose(gridPoint: GridPoint) {
    Surface(
        Modifier
            .graphicsLayer(translationX = gridPoint.posX, translationY = gridPoint.posY)
            .offset(x = (-1).dp, y = (-1).dp)
            .size(2.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {}
}

