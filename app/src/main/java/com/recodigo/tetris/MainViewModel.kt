package com.recodigo.tetris

import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.recodigo.tetris.ui.theme.Blue
import com.recodigo.tetris.ui.theme.Green
import com.recodigo.tetris.ui.theme.Yellow
import kotlinx.coroutines.delay

//class MainViewModel : ViewModel() {
//
//    var figures by mutableStateOf<List<Figure>>(emptyList())
//        private set
//
//    var figus by mutableStateOf<List<Figu>>(emptyList(), referentialEqualityPolicy())
//        private set
//
//    var figu by mutableStateOf<Figu>(Figu(FigureType.Line, 1f, 1f, 1f), referentialEqualityPolicy())
//        private set
//    var gridPoints by mutableStateOf<List<GridPoint>>(emptyList())
//        private set
//
//    private var shouldMove = true
//    private var scale: Float = 1f
//    private var maxHeight = 0f
//    private var maxWidth = 0f
//    private var cellSize = 1f
//    lateinit var figureManager: FigureManager
//        private set
//
//    fun init(height1: Float, height2: Float, cellSize: Float, maxWidth: Float) {
//        scale = height1 / height2
//        maxHeight = height1
//        this.maxWidth = maxWidth
//        this.cellSize = cellSize
//        if (!this::figureManager.isInitialized)
//            figureManager = FigureManager(cellSize * scale, maxHeight, maxWidth)
////        figureManager.updateManager(cellSize * scale, maxHeight, maxWidth)
////        createGrid()
////        start()
//    }
//
//    fun createGrid() {
//        val gridPoints = mutableListOf<GridPoint>()
//        for (i in 1..20) {
//            val posY = maxHeight - (cellSize * i * scale)
//            for (j in 1..9) {
//                val posX = cellSize * j * scale
//                gridPoints.add(GridPoint(posX - 1, posY))
//            }
//        }
//        figureManager.yOffset = gridPoints.last().posY
//        this.gridPoints = gridPoints
//    }
//
//    suspend fun start() {
//        // send new blocks
//        for (i in 1..3) {
////            figu = Figu(FigureType.Line, cellSize * scale, maxHeight, maxWidth)
////            updatePos()
//            figureManager.createRandomFigure(i)
//            figureManager.updatePosY()
//        }
//    }
//
//    suspend fun updatePos() {
//        var shouldMove = true
//        while (shouldMove) {
//            delay(100)
//            val currentPosY = figu.currentPosY
//            val currentPosX = figu.currentPosX
//            val lastFigu = figu.copy()
//            lastFigu.onNextPosY(currentPosY, 1)
//            lastFigu.onMoveInXAxis(currentPosX, 1)
//            figu = lastFigu
//            shouldMove = lastFigu.shouldKeepMoving
//        }
//        figus = figus.toMutableList().also {
//            it.add(figu)
//        }
//    }
//}
//
//data class Figure(val type: FigureType, var posY: Float)
//
//class LineInMatrix : FigureInMatrixOperations {
//    private val line: IntArray = IntArray(4) { 1 }
//    private var isInHorizontalModel = true
//    private var rotation = 0f
//    private var posXInMatrix = 0
//    private var posXInGrid = 0f
//    private var posYInMatrix = 0
//    private var posYinGrid = 0f
//    private var maxPosYInGrid = 20f
//    private var maxPosYInMatrix = 20
//
//    override fun addFigureInMatrix(
//        matrix: Array<IntArray>,
//        posYStart: Int,
//        posXStart: Int
//    ): Array<IntArray> {
//        line.forEachIndexed { index, value ->
//            val i = if (isInHorizontalModel) posYInMatrix else (posYInMatrix + index)
//            val j = if (isInHorizontalModel) (posXInMatrix + index) else posXInMatrix
//            matrix[i][j] = value
//        }
//        return matrix
//    }
//
//    override fun rotate(): Float {
//        isInHorizontalModel = !isInHorizontalModel
//        rotation += 90f
//        if (isInHorizontalModel) {
//            posXInGrid += 0.5f
//            posYinGrid += 0.5f
//            posXInMatrix -= 1
//            posYInMatrix += 1
//            maxPosYInGrid = 20f
//        }
//        else {
//            posXInGrid -= 0.5f
//            posYinGrid -= 0.5f
//            posXInMatrix += 1
//            posYInMatrix -= 1
//            maxPosYInGrid = 17.5f
//        }
//        return rotation
//    }
//
//    override fun heightInBlocks(): Int {
//        return if (isInHorizontalModel) 1 else 4
//    }
//
//    override fun widthInBlocks(): Int {
//        return if (isInHorizontalModel) 4 else 1
//    }
//
//    override fun posXInGrid(): Float {
//        return posXInGrid
//    }
//
//    override fun posXInMatrix(): Int {
//        return posXInMatrix
//    }
//
//    override fun increasePosY(): Boolean {
//        posYinGrid++
//        posYInMatrix++
//        return if (posYInMatrix > maxPosYInMatrix) {
//            posYinGrid = maxPosYInGrid
//            posYInMatrix--
//            false
//        } else
//            true
//    }
//
//    override fun posYInGrid(): Float {
//        return posYinGrid
//    }
//
//    override fun calculateMaxPositions(stepY: Int) {
//        maxPosYInMatrix = 20 - stepY
//    }
//
//}
//
//class SquareInMatrix : FigureInMatrixOperations {
//    private val square: Array<IntArray> =
//        Array(2) { IntArray(2) { 1 } }
//
//    override fun addFigureInMatrix(
//        matrix: Array<IntArray>,
//        posYStart: Int,
//        posXStart: Int
//    ): Array<IntArray> {
//        square.forEachIndexed { i, row ->
//            row.forEachIndexed { j, value ->
//                matrix[posYStart + i][j + posXStart] = value
//            }
//        }
//        return matrix
//    }
//
//    override fun rotate(): Float {
//        return 0f
//    }
//
//    override fun heightInBlocks(): Int = 2
//
//    override fun widthInBlocks(): Int = 2
//    override fun posXInGrid(): Float {
//        return 0f
//    }
//
//    override fun posXInMatrix(): Int {
//        return 0
//    }
//
//    override fun increasePosY(): Boolean {
//        return true
//    }
//
//    override fun posYInGrid(): Float {
//        return 0f
//    }
//
//    override fun calculateMaxPositions(stepY: Int) {
//
//    }
//}
//
//interface FigureInMatrixOperations {
//    fun addFigureInMatrix(matrix: Array<IntArray>, posYStart: Int, posXStart: Int): Array<IntArray>
//    fun rotate(): Float
//    fun heightInBlocks(): Int
//    fun widthInBlocks(): Int
//    fun posXInGrid(): Float
//    fun posXInMatrix(): Int
//    fun increasePosY(): Boolean
//    fun posYInGrid(): Float
//    fun calculateMaxPositions(stepY: Int)
//}
//
//class FigureManager(
//    private var cellSize: Float,
//    private var gridMaxHeight: Float,
//    private var gridMaxWidth: Float
//) {
//    // matrix of positions
//    private var figuresMatrix = createMatrix()
//
//    // list of figures
//    var figures = mutableStateListOf<Figu2>()
//        private set
//
//    // current figure
//    var currentFigure: Figu2 by mutableStateOf(
//        Figu2(FigureType.None),
//        referentialEqualityPolicy()
//    )
//        private set
//
//    // private val square = createSquare()
//    private var currentFigureInMatrix: FigureInMatrixOperations = LineInMatrix()
//
//    // maxHeightAvailable will change depending on figure type, x pos, rotation (perhaps) and gridMaxHeight
//    private var maxHeightAvailable: Float = gridMaxHeight
//
//    // maxWidthAvailable will change depending on figure type, x pos, rotation (perhaps) and gridMaxWidth
//    private var maxWidthAvailable: Float = gridMaxWidth
//
//    private var maxPosY: Float = 0f
//    private var maxPosX: Float = 0f
//    private var minPosX: Float = 0f
//    private var heightInBlocks: Int = 1
//    private var widthInBlocks: Int = 2
//    private var shouldKeepMoving = true
//    private var currentPosXInMatrix = 0
//    private var posYInMatrix = 0
//    var yOffset = 0f
//
//    private var maxPosYInMatrix = 20
//
//    private fun createMatrix(): Array<IntArray> =
//        Array(21) { IntArray(10) { 0 } }
//
//    init {
//        calculateSizeInBlocks()
//        calculateMaxPositions()
//    }
//
//    fun createRandomFigure(i: Int) {
////        currentFigure = Figu2(FigureType.Line, yOffset)
//        val color = if (i == 1) Green else if (i == 2) Blue else Yellow
//        currentFigureInMatrix = LineInMatrix()
//        val rotation = currentFigureInMatrix.rotate()
////        val rotation = 0f
//        currentFigure = Figu2(FigureType.Line, yOffset, color = color, rotation = rotation, pos = i, posX = currentFigureInMatrix.posXInGrid() * cellSize)
//        calculateSizeInBlocks()
//        calculateMaxPositions()
//    }
//
//    suspend fun updatePosY() {
//        posYInMatrix = 0
//        while (shouldKeepMoving) {
//            delay(100)
////            posYInMatrix++
//            updateY()
//
//            val newFigu = currentFigure.copy()
//            currentFigure = newFigu
//        }
//        addFigureToMatrix()
//        figures.add(currentFigure)
//        shouldKeepMoving = true
//    }
//
//    private fun addFigureToMatrix() {
//        figuresMatrix = currentFigureInMatrix.addFigureInMatrix(
//            figuresMatrix,
//            posYInMatrix,
//            currentPosXInMatrix
//        )
//    }
//
//    private fun updateY(step: Int = 1) {
////        val newPosY = currentFigure.posY + (step * cellSize)
////        val newPosY = posYInMatrix * (step * cellSize) + yOffset
////        currentFigure.posY = if (newPosY >= maxPosY) {
////            shouldKeepMoving = false
////            maxPosY
////        } else
////            newPosY
//        shouldKeepMoving = currentFigureInMatrix.increasePosY()
//        currentFigure.posY = currentFigureInMatrix.posYInGrid() * (step * cellSize) + yOffset
////        currentFigure.posY = if (posYInMatrix > maxPosYInMatrix) {
////            shouldKeepMoving = false
////            maxPosYInMatrix * (step * cellSize) + yOffset
////        } else
////            posYInMatrix * (step * cellSize) + yOffset
//    }
//
//    private fun calculateMaxPositions() {
//        // create stepY
//        val stepY = howManyBlocksInYBeforeCrash() + heightInBlocks
//        currentFigureInMatrix.calculateMaxPositions(stepY = stepY)
////        maxPosY = maxHeightAvailable - (cellSize * heightInBlocks * stepY)
////        maxPosX = maxWidthAvailable - (cellSize * widthInBlocks)
//        maxPosYInMatrix = 20 - stepY
//    }
//
//    private fun howManyBlocksInYBeforeCrash(): Int {
////        val figureMatrix = line // this will vary
//        val posXInMatrix = currentFigureInMatrix.posXInMatrix()
//        for (i in figuresMatrix.indices) {
//            for (j in posXInMatrix until posXInMatrix + 1) {
//                val value = figuresMatrix[i][j]
//                if (value == 1) return figuresMatrix.size - i
//            }
//        }
//        return 0
//    }
//
//    private fun calculateSizeInBlocks() {
//        val height = when (currentFigure.type) {
//            FigureType.Line -> 1
//            FigureType.Square -> 2
//            else -> 1
//        }
//        val width = when (currentFigure.type) {
//            FigureType.Line -> 4
//            FigureType.Square -> 2
//            else -> 1
//        }
////        heightInBlocks = if (isInHorizontalMode) height else width
////        widthInBlocks = if (isInHorizontalMode) width else height
//        heightInBlocks = currentFigureInMatrix.heightInBlocks()
//        widthInBlocks = currentFigureInMatrix.widthInBlocks()
//    }
//}
//
////  IMPORTANT: Create children from this, with default positions on X depending on the figure type. This will remove the enum
//data class Figu2(
//    val type: FigureType,
//    var posY: Float = 0f,
//    var posX: Float = 0f,
//    var rotation: Float = 0f,
//    val color: Color = Color.Black,
//    val pos: Int = 0
//)
//
//data class Figu(
//    val type: FigureType,
//    private val cellSize: Float,
//    private var maxHeightAvailable: Float,
//    private var maxWidthAvailable: Float
//) {
//    private var isInHorizontalMode = true
//    var currentPosY: Float = 0f
//
//    var currentPosX: Float = 0f
//    var maxPosY: Float = 0f
//    var maxPosX: Float = 0f
//    var minPosX: Float = 0f
//    private var heightInBlocks: Int = 1
//    private var widthInBlocks: Int = 2
//    var shouldKeepMoving = true
//        private set
//
//    init {
//        calculateSizeInBlocks()
//        calculateMaxPositions()
//    }
//
//    fun onNextPosY(currentPos: Float, step: Int) {
//        val newPosY = currentPos + (step * cellSize)
//        currentPosY = if (newPosY >= maxPosY) {
//            shouldKeepMoving = false
//            maxPosY
//        } else
//            newPosY
//    }
//
////    override fun equals(other: Any?): Boolean {
//////        if (this === other) return true
////        if (other is Figu) {
////            if (this.type == other.type &&
////                this.currentPosY == other.currentPosY &&
////                this.currentPosX == other.currentPosX
////            ) return true
////        }
////        return false
////    }
//
//    fun onMoveInXAxis(currentPos: Float, step: Int) {
//        // calculate new maxPosY based on new maxHeightAvailable provided by the canvas
//        val newPosX = currentPos + (step * cellSize)
//        currentPosX = when {
//            newPosX >= maxPosX -> maxPosX
//            newPosX <= minPosX -> minPosX
//            else -> newPosX
//        }
//    }
//
//    fun onRotate() {
//        // it will change isInHorizontalMode
//        isInHorizontalMode = !isInHorizontalMode
//        calculateSizeInBlocks()
//        // and will trigger the calculation of maxPox
//        calculateMaxPositions()
//    }
//
//    private fun calculateMaxPositions() {
//        maxPosY = maxHeightAvailable - (cellSize * heightInBlocks)
//        maxPosX = maxWidthAvailable - (cellSize * widthInBlocks)
//    }
//
//    private fun calculateSizeInBlocks() {
//        val height = when (type) {
//            FigureType.Line -> 1
//            FigureType.Square -> 2
//            else -> 1
//        }
//        val width = when (type) {
//            FigureType.Line -> 4
//            FigureType.Square -> 2
//            else -> 1
//        }
//        heightInBlocks = if (isInHorizontalMode) height else width
//        widthInBlocks = if (isInHorizontalMode) width else height
//    }
//
//}
//
//enum class FigureType {
//    Line,
//    Square,
//    None
//}
//
//data class GridPoint(var posX: Float, var posY: Float)